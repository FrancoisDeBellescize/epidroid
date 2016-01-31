package com.example.francois.myapplication;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import ConnectionObject.PlanningObject;
import cz.msebera.android.httpclient.Header;

public class PlanningFragment extends Fragment {
    private RecyclerView mRecyclerView;
    private List<PlanningObject> plannings = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(
                R.layout.module_fragment_content, container, false);


        String url = "https://epitech-api.herokuapp.com/planning";
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        params.put("token", ((DashBoard) getActivity()).getToken());

        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        dateFormat.format(date);
        params.put("start", date);
        params.put("end", date);

        client.post(url, params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray errorResponse) {
                Gson gson = new GsonBuilder().create();
                PlanningObject[] plannings_tmp = gson.fromJson(errorResponse.toString(), PlanningObject[].class);
                for (PlanningObject tmp : plannings_tmp) {
                    if (tmp.getModule_registered())
                        plannings.add(tmp);
                }
                Collections.reverse(plannings);
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
        private TextView subscribe;
        private RelativeLayout layoutCard;

        //itemView est la vue correspondante à 1 cellule
        public MyViewHolder(View itemView) {
            super(itemView);

            //c'est ici que l'on fait nos findView

            textTitle = (TextView) itemView.findViewById(R.id.planning_title);
            textContent = (TextView) itemView.findViewById(R.id.planning_content);
            textDateStart = (TextView) itemView.findViewById(R.id.planning_date_start);
            textDateEnd = (TextView) itemView.findViewById(R.id.planning_date_end);
            layoutCard = (RelativeLayout) itemView.findViewById(R.id.layout_card_planning);
            subscribe = (TextView) itemView.findViewById(R.id.subscribe_planning);
        }

        //puis ajouter une fonction pour remplir la cellule en fonction d'un MyObject
        public void bind(final PlanningObject planning) {
            textTitle.setText(planning.getActi_title());
            textContent.setText(planning.getTitle_module());
            textDateStart.setText(planning.getStart());
            textDateEnd.setText(planning.getEnd());

            if (planning.getEvent_registered()) {
                subscribe.setText("Inscrit");
                subscribe.setTextColor(Color.GREEN);
            } else {
                subscribe.setText("Non inscrit");
                subscribe.setTextColor(Color.RED);
            }
        }
    }

    public class MyAdapter extends RecyclerView.Adapter<MyViewHolder> {

        List<PlanningObject> list;

        //ajouter un constructeur prenant en entrée une liste
        public MyAdapter(List<PlanningObject> list) {
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
            PlanningObject planning = list.get(position);
            myViewHolder.bind(planning);
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