package com.example.francois.myapplication;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import ConnectionObject.InfoObject;
import Entity.Projet;
import cz.msebera.android.httpclient.Header;

public class ProjetsFragment extends Fragment {
    private RecyclerView mRecyclerView;
    private List<Projet> projets = new ArrayList<>();
    private InfoObject infos;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(
                R.layout.projet_fragment_content, container, false);

        ajouterProjets();
        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setAdapter(new ProjetsFragment.MyAdapter(projets));

        return rootView;
    }

    private void ajouterProjets() {
        projets.clear();
        infos = ((DashBoard) getActivity()).getInfos();
        if (infos != null)
            for (InfoObject.BoardObject.ProjetObject object : infos.getBoard().getProjets()) {
                projets.add(new Projet(object.getTitle(), object.getTimeline_start(), object.getTimeline_end(),
                        Float.parseFloat(object.getTimeline_barre()), object.getDate_inscription(), object.getTitle_link()));
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
        public void bind(final Projet projet) {
            textTitle.setText(projet.getTitle());
            textEnd.setText(projet.getEnd());
            textStart.setText(projet.getStart());
            progressBar.setProgress(Math.round(projet.getProgress()));

            if (projet.getDate_inscription().compareTo("false") != 0) {
                Log.e("PROJET", projet.getTitle() + " creation button");
                button = new Button(getActivity());
                button.setText("Inscription");
                RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                layoutParams.addRule(RelativeLayout.BELOW, R.id.progress);
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View arg0) {
                        String[] separated = projet.getTitle_link().split("/");

                        String url = "https://epitech-api.herokuapp.com/project";
                        AsyncHttpClient client = new AsyncHttpClient();
                        RequestParams params = new RequestParams();
                        params.put("token", ((DashBoard) getActivity()).getToken());
                        params.put("scolaryear", separated[2]);
                        params.put("codemodule", separated[3]);
                        params.put("codeinstance", separated[4]);
                        params.put("codeacti", separated[5]);

                        client.post(url, params, new JsonHttpResponseHandler() {
                            @Override
                            public void onSuccess(int statusCode, Header[] headers, JSONObject errorResponse) {
                                layoutCard.removeView(button);
                                Toast.makeText(getActivity(), "Vous avez été inscrit au projet", Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                                super.onFailure(statusCode, headers, throwable, errorResponse);

                                Toast.makeText(getActivity(), "Impossible de s'inscrire au projet", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                });
                layoutCard.addView(button, layoutParams);
            }
        }
    }

    public class MyAdapter extends RecyclerView.Adapter<MyViewHolder> {

        List<Projet> list;

        //ajouter un constructeur prenant en entrée une liste
        public MyAdapter(List<Projet> list) {
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
            Projet projet = list.get(position);
            myViewHolder.bind(projet);
        }

        @Override
        public int getItemCount() {
            return list.size();
        }

    }
}