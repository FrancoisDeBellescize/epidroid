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

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import ConnectionObject.InfoObject;
import ConnectionObject.MarksObject;
import ConnectionObject.PhotoObject;
import Entity.Module;
import cz.msebera.android.httpclient.Header;

public class MarksFragment extends Fragment {
    private RecyclerView mRecyclerView;
    private List<MarksObject.Note> marks;
    private InfoObject infos;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(
                R.layout.module_fragment_content, container, false);



        String url = "https://epitech-api.herokuapp.com/marks";
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        params.put("token", ((DashBoard) getActivity()).getToken());

        client.post(url, params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject errorResponse) {
                Gson gson = new GsonBuilder().create();
                MarksObject mark = gson.fromJson(errorResponse.toString(), MarksObject.class);
                marks = mark.getNotes();
                Collections.reverse(marks);
                mRecyclerView = (RecyclerView) getView().findViewById(R.id.recyclerView);
                mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                mRecyclerView.setAdapter(new MarksFragment.MyAdapter(marks));
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);

            }
        });
        return rootView;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView textTitle;
        private TextView textContent;
        private TextView textDate;

        //itemView est la vue correspondante à 1 cellule
        public MyViewHolder(View itemView) {
            super(itemView);

            //c'est ici que l'on fait nos findView

            textTitle = (TextView) itemView.findViewById(R.id.note_title);
            textContent = (TextView) itemView.findViewById(R.id.note_content);
            textDate = (TextView) itemView.findViewById(R.id.note_date);
        }

        //puis ajouter une fonction pour remplir la cellule en fonction d'un MyObject
        public void bind(final MarksObject.Note note) {
            textTitle.setText(note.getTitle());
            textContent.setText(note.getFinal_note());
            textDate.setText(note.getDate());
        }
    }

    public class MyAdapter extends RecyclerView.Adapter<MyViewHolder> {

        List<MarksObject.Note> list;

        //ajouter un constructeur prenant en entrée une liste
        public MyAdapter(List<MarksObject.Note> list) {
            this.list = list;
        }

        //cette fonction permet de créer les viewHolder
        //et par la même indiquer la vue à inflater (à partir des layout xml)
        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int itemType) {
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.cell_cards_note, viewGroup, false);
            return new MyViewHolder(view);
        }

        //c'est ici que nous allons remplir notre cellule avec le texte/image de chaque MyObjects
        @Override
        public void onBindViewHolder(MyViewHolder myViewHolder, int position) {
            MarksObject.Note note = list.get(position);
            myViewHolder.bind(note);
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