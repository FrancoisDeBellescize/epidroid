package com.example.francois.myapplication;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.loopj.android.http.*;

import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import ConnectionObject.LoginObject;
import cz.msebera.android.httpclient.Header;


public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SharedPreferences settings = getSharedPreferences("connection", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();

        String login = settings.getString("login", null);

        if (login != null) {
            EditText edit_login = (EditText) findViewById(R.id.login);
            edit_login.setText(login);
            findViewById(R.id.password).requestFocus();
        }
    }

    public String getStringFromEditText(EditText et) {

        String ALLOWED_URI_CHARS = "<>@#&=*+-_.,:!?()/~'%";
        String ret = null;
        ret = Uri.encode(et.getText().toString(), ALLOWED_URI_CHARS);
        return (ret);
    }

    public void Connection(View view) {

        String login = getStringFromEditText((EditText) findViewById(R.id.login));
        String password = getStringFromEditText((EditText) findViewById(R.id.password));

        Log.e("login : ", login);
        Log.e("pwd : ", password);

        TextView error = (TextView) findViewById(R.id.error);

        SharedPreferences settings = getSharedPreferences("connection", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString("login", login);
        editor.commit();

        error.setText("Connection ...");
        String url = "https://epitech-api.herokuapp.com/login";
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        params.put("login", login);
        params.put("password", password);
        client.get(url, params, new JsonHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject errorResponse) {

                Gson gson = new GsonBuilder().create();
                LoginObject tmp = gson.fromJson(errorResponse.toString(), LoginObject.class);

                SharedPreferences settings = getSharedPreferences("connection", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = settings.edit();
                editor.putString("token", tmp.getToken());
                editor.commit();

                String token = settings.getString("token", "No token");
                Log.w("Token : ", token);


                TextView error = (TextView) findViewById(R.id.error);
                error.setText("Connected");

                Intent intent = new Intent(MainActivity.this, DashBoard.class);
                startActivity(intent);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);

                Gson gson = new GsonBuilder().create();
                LoginObject tmp = gson.fromJson(errorResponse.toString(), LoginObject.class);
                if (tmp.getError() != null) {
                    TextView error = (TextView) findViewById(R.id.error);
                    error.setText(tmp.getError().getMessage());
                    Log.w("Error " + tmp.getError().getCode() + " : ", tmp.getError().getMessage());
                }
            }
        });
    }
}
