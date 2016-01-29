package com.example.francois.myapplication;

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
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import ConnectionObject.InfoObject;
import Entity.Module;
import cz.msebera.android.httpclient.Header;

public class ModulesFragment extends Fragment {
    private RecyclerView mRecyclerView;
    private List<Module> modules = new ArrayList<>();
    private InfoObject infos;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(
                R.layout.module_fragment_content, container, false);

        ajouterModules();
        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setAdapter(new ModulesFragment.MyAdapter(modules));

        return rootView;
    }

    private void ajouterModules() {
        infos = ((DashBoard) getActivity()).getInfos();
        if (infos != null)
            for (InfoObject.BoardObject.ModuleObject object : infos.getBoard().getModules()) {
                modules.add(new Module(object.getTitle(), object.getTimeline_start(), object.getTimeline_end(),
                        Float.parseFloat(object.getTimeline_barre()), object.getTitle_link()));
            }
        else
            Log.w("Fragment:", "null ...");
    }

    private void refreshModules() {
        mRecyclerView = (RecyclerView) getView().findViewById(R.id.recyclerView);
        mRecyclerView.removeAllViews();
        ajouterModules();


        ((DashBoard) getActivity()).LoadInfos();
        while(infos == null){
            infos = ((DashBoard) getActivity()).getInfos();
        }
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
        public void bind(final Module module) {
            textTitle.setText(module.getTitle());
            textEnd.setText(module.getEnd());
            textStart.setText(module.getStart());
            progressBar.setProgress(Math.round(module.getProgress()));

            button = new Button(getActivity());
            button.setText("Inscription");
            RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            layoutParams.addRule(RelativeLayout.BELOW, R.id.progress);
            layoutCard.addView(button, layoutParams);
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View arg0) {
                    Log.e("Inscription", "click");
                    String[] separated = module.getTitle_link().split("/");

                    String url = "https://epitech-api.herokuapp.com/module";
                    AsyncHttpClient client = new AsyncHttpClient();
                    RequestParams params = new RequestParams();
                    params.put("token", ((DashBoard) getActivity()).getToken());
                    params.put("scolaryear", separated[2]);
                    params.put("codemodule", separated[3]);
                    params.put("codeinstance", separated[4]);

                    client.post(url, params, new JsonHttpResponseHandler() {
                        @Override
                        public void onSuccess(int statusCode, Header[] headers, JSONObject errorResponse) {
                            Toast.makeText(getActivity(), "Vous avez été inscrit au module", Toast.LENGTH_SHORT).show();
                            layoutCard.removeView(button);
                        }

                        @Override
                        public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                            super.onFailure(statusCode, headers, throwable, errorResponse);

                            Toast.makeText(getActivity(), "Impossible de s'inscrire au module", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            });
        }
    }

    public class MyAdapter extends RecyclerView.Adapter<MyViewHolder> {

        List<Module> list;

        //ajouter un constructeur prenant en entrée une liste
        public MyAdapter(List<Module> list) {
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
            Module module = list.get(position);
            myViewHolder.bind(module);
        }

        @Override
        public int getItemCount() {
            return list.size();
        }

        public void reload() {
            this.notifyDataSetChanged();
        }
    }
}