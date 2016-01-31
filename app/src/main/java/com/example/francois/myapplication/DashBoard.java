package com.example.francois.myapplication;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;

import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;

import ConnectionObject.AlertsObject;
import ConnectionObject.InfoObject;
import ConnectionObject.PhotoObject;
import cz.msebera.android.httpclient.Header;

public class DashBoard extends FragmentActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private InfoObject infos;
    private String token;
    private Fragment currentFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dash_board);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        String token = getToken();
        String url = "https://epitech-api.herokuapp.com/infos";
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        params.put("token", token);
        client.get(url, params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject errorResponse) {

                Gson gson = new GsonBuilder().create();
                infos = gson.fromJson(errorResponse.toString(), InfoObject.class);
                LoadInfos();
                ChangeFragment(new ModulesFragment());
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);

                Log.w("Error : ", "Can't get Infos");
                Intent intent = new Intent(DashBoard.this, MainActivity.class);
                startActivity(intent);
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.dash_board, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        Fragment fragment = null;
        String title = getString(R.string.app_name);

        if (id == R.id.nav_modules) {
            fragment = new ModulesFragment();
        } else if (id == R.id.nav_projets) {
            fragment = new ProjetsFragment();
        } else if (id == R.id.nav_history) {
            fragment = new HistoryFragment();
        } else if (id == R.id.nav_activity) {
            fragment = new ActivityFragment();
        } else if (id == R.id.nav_marks) {
            fragment = new MarksFragment();
        } else if (id == R.id.nav_planning) {
            fragment = new PlanningFragment();
        }
    ChangeFragment(fragment);
        return true;
    }

    public void LoadInfos() {

        TextView TvLogin = (TextView) findViewById(R.id.profil_login);
        TvLogin.setText(infos.getInfos().getLogin());
        TextView TvEmail = (TextView) findViewById(R.id.profil_email);
        TvEmail.setText(infos.getInfos().getInternal_email());

        LoadPhoto();
        LoadAlerts();
    }

    public void LoadPhoto() {
        String token = getToken();
        String url = "https://epitech-api.herokuapp.com/photo";
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        params.put("token", token);
        params.put("login", infos.getInfos().getLogin());
        client.get(url, params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject errorResponse) {

                Gson gson = new GsonBuilder().create();
                PhotoObject photo = gson.fromJson(errorResponse.toString(), PhotoObject.class);
                ImageView i = (ImageView) findViewById(R.id.profil_image);
                new DownloadImageTask(i).execute(photo.getUrl());
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);

                Log.w("Error : ", "Can't get Infos");
                Intent intent = new Intent(DashBoard.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }

    public void LoadAlerts() {
        String token = getToken();
        String url = "https://epitech-api.herokuapp.com/alerts";
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        params.put("token", token);
        client.get(url, params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray errorResponse) {

                Gson gson = new GsonBuilder().create();
                AlertsObject[] alerts = gson.fromJson(errorResponse.toString(), AlertsObject[].class);

                LinearLayout profilLayout = (LinearLayout) findViewById(R.id.ProfilLayout);

                for (AlertsObject alert : alerts) {
                    TextView tmp = new TextView(DashBoard.this);
                    tmp.setTextSize(10);
                    tmp.setTextColor(Color.parseColor("#FFFFFF"));
                    tmp.setText(alert.getTitle());
                    profilLayout.addView(tmp);
                    profilLayout.invalidate();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);

                Log.w("Error : ", "Can't get Alerts");
                Intent intent = new Intent(DashBoard.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }

    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        ImageView bmImage;

        public DownloadImageTask(ImageView bmImage) {
            this.bmImage = bmImage;
        }

        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];
            Bitmap mIcon11 = null;
            try {
                InputStream in = new java.net.URL(urldisplay).openStream();
                mIcon11 = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return mIcon11;
        }

        protected void onPostExecute(Bitmap result) {
            bmImage.setImageBitmap(result);
        }
    }

    public void ChangeFragment(Fragment fragment) {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);


        if (fragment != null) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();

            if (currentFragment == null) {
                ft.replace(R.id.content, fragment);
            } else {
                currentFragment = fragment;
                ft.detach(currentFragment);
                ft.attach(fragment);
            }
            ft.commit();
        }
    }

    public InfoObject getInfos() {
        return (infos);
    }

    public String getToken() {
        if (token == null) {
            SharedPreferences settings = getSharedPreferences("connection", Context.MODE_PRIVATE);
            token = settings.getString("token", null);
        }
        return (token);
    }
}
