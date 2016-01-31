package com.example.francois.myapplication;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONObject;

import java.util.Collections;
import java.util.List;

import ConnectionObject.InfoObject;
import ConnectionObject.MarksObject;
import ConnectionObject.PlanningObject;
import cz.msebera.android.httpclient.Header;

public class PlanningFragment extends Fragment {
    private RecyclerView mRecyclerView;
    private List<PlanningObject.Planning> plannings;
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
        params.put("start", "2016-01-29");
        params.put("end", "2016-01-29");

        client.post(url, params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject errorResponse) {
                Gson gson = new GsonBuilder().create();
                PlanningObject planning = gson.fromJson(errorResponse.toString(), PlanningObject.class);
                plannings = planning.getPlanning();
                //Collections.reverse(plannings);
                mRecyclerView = (RecyclerView) getView().findViewById(R.id.recyclerView);
                mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                mRecyclerView.setAdapter(new PlanningFragment.MyAdapter(plannings));
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
        private TextView textDateStart;
        private TextView textDateEnd;

        //itemView est la vue correspondante à 1 cellule
        public MyViewHolder(View itemView) {
            super(itemView);

            //c'est ici que l'on fait nos findView

            textTitle = (TextView) itemView.findViewById(R.id.planning_title);
            textContent = (TextView) itemView.findViewById(R.id.planning_content);
            textDateStart = (TextView) itemView.findViewById(R.id.planning_date_start);
            textDateEnd = (TextView) itemView.findViewById(R.id.planning_date_end);
        }

        //puis ajouter une fonction pour remplir la cellule en fonction d'un MyObject
        public void bind(final PlanningObject.Planning planning) {
            textTitle.setText(planning.getActi_title());
            textContent.setText(planning.getTitle_module());
            textDateStart.setText(planning.getStart());
            textDateEnd.setText(planning.getEnd());
        }
    }

    public class MyAdapter extends RecyclerView.Adapter<MyViewHolder> {

        List<PlanningObject.Planning> list;

        //ajouter un constructeur prenant en entrée une liste
        public MyAdapter(List<PlanningObject.Planning> list) {
            this.list = list;
        }

        //cette fonction permet de créer les viewHolder
        //et par la même indiquer la vue à inflater (à partir des layout xml)
        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int itemType) {
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.cell_cards_planning, viewGroup, false);
            return new MyViewHolder(view);
        }

        //c'est ici que nous allons remplir notre cellule avec le texte/image de chaque MyObjects
        @Override
        public void onBindViewHolder(MyViewHolder myViewHolder, int position) {
            PlanningObject.Planning planning = list.get(position);
            myViewHolder.bind(planning);
        }

        @Override
        public int getItemCount() {
        //            return list.size();
            return 0;
        }

        public void reload() {
            this.notifyDataSetChanged();
        }
    }
}