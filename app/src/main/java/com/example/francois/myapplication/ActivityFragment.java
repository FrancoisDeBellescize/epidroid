package com.example.francois.myapplication;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import ConnectionObject.InfoObject;
import Entity.Activity;
import cz.msebera.android.httpclient.Header;

public class ActivityFragment extends Fragment {
    private RecyclerView mRecyclerView;
    private List<Activity> activities = new ArrayList<>();
    private InfoObject infos;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(
                R.layout.module_fragment_content, container, false);

        ajouterAcitivies();
        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setAdapter(new ActivityFragment.MyAdapter(activities));

        return rootView;
    }

    private void ajouterAcitivies() {
        activities.clear();
        infos = ((DashBoard) getActivity()).getInfos();
        if (infos != null)
            for (InfoObject.BoardObject.ActivityObject object : infos.getBoard().getActivites()) {
                activities.add(new Activity(object.getTitle(), object.getTimeline_start(), object.getTimeline_end(),
                        Float.parseFloat(object.getTimeline_barre()), object.getToken(), object.getToken_link()));
            }
        else
            Log.w("Fragment:", "null ...");
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView textTitle;
        private TextView textEnd;
        private TextView textStart;
        private ProgressBar progressBar;
        private Button button;
        private RelativeLayout layoutCard;

        //itemView est la vue correspondante à 1 cellule
        public MyViewHolder(View itemView) {
            super(itemView);

            //c'est ici que l'on fait nos findView

            textTitle = (TextView) itemView.findViewById(R.id.title);
            textEnd = (TextView) itemView.findViewById(R.id.end);
            textStart = (TextView) itemView.findViewById(R.id.start);
            progressBar = (ProgressBar) itemView.findViewById(R.id.progress);
            layoutCard = (RelativeLayout) itemView.findViewById(R.id.layout_card);
        }

        //puis ajouter une fonction pour remplir la cellule en fonction d'un MyObject
        public void bind(final Activity activity) {
            final Context context = getActivity();
            textTitle.setText(activity.getTitle());
            textEnd.setText(activity.getEnd());
            textStart.setText(activity.getStart());
            progressBar.setProgress(Math.round(activity.getProgress()));

            if (activity.getToken() != null) {
                button = new Button(getActivity());
                RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                layoutParams.addRule(RelativeLayout.BELOW, R.id.progress);
                button.setText("Valider le token");
                button.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View arg0) {

                        // get prompts.xml view
                        LayoutInflater li = LayoutInflater.from(context);
                        View promptsView = li.inflate(R.layout.prompt, null);

                        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);

                        // set prompts.xml to alertdialog builder
                        alertDialogBuilder.setView(promptsView);

                        final EditText userInput = (EditText) promptsView.findViewById(R.id.editTextDialogUserInput);

                        // set dialog message
                        alertDialogBuilder
                                .setCancelable(false)
                                .setPositiveButton("OK",
                                        new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int id) {
                                                String[] separated = activity.getToken_link().split("\\\\/");

                                                String url = "https://epitech-api.herokuapp.com/token";
                                                AsyncHttpClient client = new AsyncHttpClient();
                                                RequestParams params = new RequestParams();
                                                params.put("token", ((DashBoard) getActivity()).getToken());
                                                params.put("scolaryear", separated[2]);
                                                params.put("codemodule", separated[3]);
                                                params.put("codeinstance", separated[4]);
                                                params.put("codeacti", separated[5]);
                                                params.put("codeinstance", separated[6]);
                                                params.put("codeevent", separated[3]);
                                                params.put("tokenvalidationcode", userInput.getText());

                                                client.get(url, params, new JsonHttpResponseHandler() {
                                                    @Override
                                                    public void onSuccess(int statusCode, Header[] headers, JSONObject errorResponse) {

                                                    }

                                                    @Override
                                                    public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                                                        super.onFailure(statusCode, headers, throwable, errorResponse);

                                                        Log.w("Error : ", "Can't valid token");
                                                        Intent intent = new Intent(getActivity(), MainActivity.class);
                                                        startActivity(intent);
                                                    }
                                                });
                                            }
                                        })
                                .setNegativeButton("Cancel",
                                        new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int id) {
                                                dialog.cancel();
                                            }
                                        });

                        // create alert dialog
                        AlertDialog alertDialog = alertDialogBuilder.create();

                        // show it
                        alertDialog.show();

                    }
                });
                layoutCard.addView(button, layoutParams);
            }
        }
    }

    public class MyAdapter extends RecyclerView.Adapter<MyViewHolder> {

        List<Activity> list;

        //ajouter un constructeur prenant en entrée une liste
        public MyAdapter(List<Activity> list) {
            this.list = list;
        }

        //cette fonction permet de créer les viewHolder
        //et par la même indiquer la vue à inflater (à partir des layout xml)
        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int itemType) {
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.cell_cards, viewGroup, false);
            return new MyViewHolder(view);
        }

        //c'est ici que nous allons remplir notre cellule avec le texte/image de chaque MyObjects
        @Override
        public void onBindViewHolder(MyViewHolder myViewHolder, int position) {
            Activity activity = list.get(position);
            myViewHolder.bind(activity);
        }

        @Override
        public int getItemCount() {
            return list.size();
        }

    }
}