package com.apperspace.forward.catapp;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


/**
 * A simple {@link Fragment} subclass.
 */
public class FavouritesFragment extends Fragment
{
    RecyclerView recyclerView;
    ProgressBar loadingProgress;
    ArrayList<Model> mList=new ArrayList<>();
    CatListAdapter adapter;

    public FavouritesFragment()
    {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        View view=inflater.inflate(R.layout.fragment_favourites, container, false);

        recyclerView=view.findViewById(R.id.recycler_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(),RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(),
                layoutManager.getOrientation());
        recyclerView.addItemDecoration(dividerItemDecoration);
        loadingProgress=view.findViewById(R.id.loading_progress);

        adapter=new CatListAdapter(getActivity(),mList);

        ArrayList<String>mStringlist= new ArrayList<>();
        mStringlist=getArrayList("fav_list");

        if(mStringlist!=null)
        {
            for(int i=0;i<mStringlist.size();i++)
            {
                Model model= new Model();
                model.setName(mStringlist.get(i));
                mList.add(model);
            }
        }

        adapter.notifyDataSetChanged();

        recyclerView.setAdapter(adapter);


        return view;
    }


    public ArrayList<String> getArrayList(String key)
    {
        SharedPreferences preferences = getActivity().getSharedPreferences("cat_app_prefrences", Context.MODE_PRIVATE);
        Gson gson = new Gson();
        String json = preferences.getString(key, null);
        Type type = new TypeToken<ArrayList<String>>() {}.getType();
        return gson.fromJson(json, type);
    }




}
