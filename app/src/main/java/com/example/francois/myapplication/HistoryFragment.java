package com.example.francois.myapplication;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import ConnectionObject.InfoObject;
import Entity.History;
import Entity.Projet;

public class HistoryFragment extends Fragment {
    private RecyclerView mRecyclerView;
    private List<History> histories = new ArrayList<>();
    private InfoObject infos;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(
                R.layout.projet_fragment_content, container, false);

        ajouterHistory();

        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setAdapter(new HistoryFragment.MyAdapter(histories));

        return rootView;
    }

    private void ajouterHistory() {
        infos = ((DashBoard) getActivity()).getInfos();
        if (infos != null)
            for (InfoObject.HistoryObject history : infos.getHistory()) {
                histories.add(new History(history.getTitle(), history.getContent(), history.getUser().getTitle()));
        }
        else
            Log.w("Fragment:", "null ...");
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView textTitle;
        private TextView textContent;
        private TextView textEnd;
        private ImageView image;

        //itemView est la vue correspondante à 1 cellule
        public MyViewHolder(View itemView) {
            super(itemView);

            //c'est ici que l'on fait nos findView

            textTitle = (TextView) itemView.findViewById(R.id.history_title);
            textContent = (TextView) itemView.findViewById(R.id.history_content);
            textEnd = (TextView) itemView.findViewById(R.id.history_end);
            image = (ImageView) itemView.findViewById(R.id.history_image);

        }

        //puis ajouter une fonction pour remplir la cellule en fonction d'un MyObject
        public void bind(History history) {
            textTitle.setText(history.getTitle());
            textContent.setText(history.getContent());
            textEnd.setText(history.getEnd());
        }
    }

    public class MyAdapter extends RecyclerView.Adapter<MyViewHolder> {

        List<History> list;

        //ajouter un constructeur prenant en entrée une liste
        public MyAdapter(List<History> list) {
            this.list = list;
        }

        //cette fonction permet de créer les viewHolder
        //et par la même indiquer la vue à inflater (à partir des layout xml)
        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int itemType) {
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.cell_cards_history, viewGroup, false);
            return new MyViewHolder(view);
        }

        //c'est ici que nous allons remplir notre cellule avec le texte/image de chaque MyObjects
        @Override
        public void onBindViewHolder(MyViewHolder myViewHolder, int position) {
            History history = list.get(position);
            myViewHolder.bind(history);
        }

        @Override
        public int getItemCount() {
            return list.size();
        }

    }
}