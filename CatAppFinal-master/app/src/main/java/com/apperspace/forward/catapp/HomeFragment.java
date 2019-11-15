package com.apperspace.forward.catapp;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import org.json.JSONArray;
import org.json.JSONException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class HomeFragment extends Fragment
{
    RecyclerView recyclerView;
    ProgressBar loadingProgress;
    ArrayList<Model>mList=new ArrayList<>();
    CatListAdapter adapter;
    public HomeFragment()
    {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        final View view=inflater.inflate(R.layout.fragment_home, container, false);
        recyclerView=view.findViewById(R.id.recycler_view);
        final EditText etSearchText=view.findViewById(R.id.editText_search);
        ImageView searchView=view.findViewById(R.id.search_item);

        searchView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                if(!etSearchText.getText().toString().isEmpty())
                {
                    InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Activity.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                    sendRequest(etSearchText.getText().toString().trim());
                }

                else
                {
                    Toast.makeText(getActivity(), "Please enter search query", Toast.LENGTH_SHORT).show();
                }
            }
        });

        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(),RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(),
                layoutManager.getOrientation());
        recyclerView.addItemDecoration(dividerItemDecoration);
        loadingProgress=view.findViewById(R.id.loading_progress);
        adapter=new CatListAdapter(getActivity(),mList);


        adapter.setOnItemClickListener(new CatListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position)
            {
                startActivity(new Intent(getActivity(),CatDetailsActivity.class).putExtra("search_query",mList.get(position).getId()));
            }
        });

        recyclerView.setAdapter(adapter);

        etSearchText.addTextChangedListener(new TextWatcher()
        {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count)
            {

            }
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }
            @Override
            public void afterTextChanged(Editable s)
            {
               if(s.toString().isEmpty())
               {
                   mList.clear();
                   adapter.notifyDataSetChanged();
               }
            }
        });

        return view;
    }



    private void sendRequest(String query)
    {
        mList.clear();
        loadingProgress.setVisibility(View.VISIBLE);
        String link ="https://api.thecatapi.com/v1/breeds/search?q="+query;
        StringRequest stringRequest = new StringRequest(Request.Method.GET, link,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response)
                    {
                        loadingProgress.setVisibility(View.GONE);

                        try
                        {
                            JSONArray jsonArray = new JSONArray(response);

                            for (int i = 0; i < jsonArray.length(); i++)
                            {
                                Model model = new Model();
                                model.setId(jsonArray.getJSONObject(i).getString("id"));
                                model.setName(jsonArray.getJSONObject(i).getString("name"));
                                mList.add(model);
                            }

                            if(mList.isEmpty())
                            {
                                Toast.makeText(getActivity(), "No Values Found", Toast.LENGTH_SHORT).show();
                            }

                            adapter.notifyDataSetChanged();

                        }

                        catch (JSONException e)
                        {
                            e.printStackTrace();
                            Toast.makeText(getActivity(), "No Values Found", Toast.LENGTH_SHORT).show();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error)
                    {
                        loadingProgress.setVisibility(View.GONE);
                        Toast.makeText(getActivity(), "No Values Found", Toast.LENGTH_SHORT).show();
                    }
                }) {

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                //params.put("x-api-key","b73336c1-22f9-4312-820c-5232fa483f89");
                params.put("Content-Type", "application/x-www-form-urlencoded");
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(stringRequest);

    }


}
