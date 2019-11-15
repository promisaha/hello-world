package com.apperspace.forward.catapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.reflect.TypeToken;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CatDetailsActivity extends AppCompatActivity
{
    ProgressBar loadingProgress;
    TextView mTitleText,mDescText,mTvOriginText,mTvTemperamentText,mWeightText,mDogLevelTxt,mWikiLinkTxt,mLifeSpanTxt;
    ImageView mImage,mFavouriteImage;
    LinearLayout detailsLayout;
    boolean isFavourite;
    Model model;
    ArrayList<String> list = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cat_details);
        model = new Model();
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Breed Details");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        loadingProgress=findViewById(R.id.loading_progress);
        mFavouriteImage=findViewById(R.id.img_favourite);
        mTitleText=findViewById(R.id.tv_title);
        mDescText=findViewById(R.id.tv_desc);
        mTvOriginText=findViewById(R.id.tv_origin);
        mTvTemperamentText=findViewById(R.id.tv_temperament);
        mWeightText=findViewById(R.id.tv_weight);
        mDogLevelTxt=findViewById(R.id.tv_dogfriendlevel);
        mWikiLinkTxt=findViewById(R.id.tv_wikilink);
        mLifeSpanTxt=findViewById(R.id.tv_lifespan);
        mImage=findViewById(R.id.user_img);
        detailsLayout=findViewById(R.id.details_layout);
        detailsLayout.setVisibility(View.GONE);
        getDetails();
        list=getArrayList("fav_list");

        if(list==null)
        {
            list=new ArrayList<>();
        }

        mFavouriteImage.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if(isFavourite)
                {
                    mFavouriteImage.setBackground(getResources().getDrawable(R.drawable.ic_star_empty));
                    isFavourite=false;
                    list.remove(model.getName());
                    saveArrayList(list,"fav_list");
                    Toast.makeText(CatDetailsActivity.this, "Removed from favourite", Toast.LENGTH_SHORT).show();
                }

                else
                {
                    mFavouriteImage.setBackground(getResources().getDrawable(R.drawable.ic_star));
                    isFavourite=true;
                    list.add(model.getName());
                    saveArrayList(list,"fav_list");
                    Toast.makeText(CatDetailsActivity.this, "Added to favourite", Toast.LENGTH_SHORT).show();
                }



            }
        });

    }

    public void saveArrayList(ArrayList<String> list, String key){
        SharedPreferences preferences = getSharedPreferences("cat_app_prefrences", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(list);
        editor.putString(key, json);
        editor.apply();

    }

    public ArrayList<String> getArrayList(String key)
    {
        SharedPreferences preferences = getSharedPreferences("cat_app_prefrences", Context.MODE_PRIVATE);
        Gson gson = new Gson();
        String json = preferences.getString(key, null);
        Type type = new TypeToken<ArrayList<String>>() {}.getType();
        return gson.fromJson(json, type);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        if(item.getItemId()==android.R.id.home)
        {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    public void getDetails()
    {
        String query=getIntent().getStringExtra("search_query");
        loadingProgress.setVisibility(View.VISIBLE);
        String link ="https://api.thecatapi.com/v1/images/search?breed_ids="+query;
        StringRequest stringRequest = new StringRequest(Request.Method.GET, link,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response)
                    {
                        Log.i("asdasdasd",response);
                        loadingProgress.setVisibility(View.GONE);


                        try
                        {
                            JSONArray jsonArray = new JSONArray(response);
                            model.setImageUrl(jsonArray.getJSONObject(0).getString("url"));
                            model.setId(jsonArray.getJSONObject(0).getJSONArray("breeds").getJSONObject(0).getString("id"));
                            model.setName(jsonArray.getJSONObject(0).getJSONArray("breeds").getJSONObject(0).getString("name"));
                            model.setTemperament(jsonArray.getJSONObject(0).getJSONArray("breeds").getJSONObject(0).getString("temperament"));
                            model.setDescription(jsonArray.getJSONObject(0).getJSONArray("breeds").getJSONObject(0).getString("description"));
                            model.setLifeSpan(jsonArray.getJSONObject(0).getJSONArray("breeds").getJSONObject(0).getString("life_span"));
                            model.setOrigin(jsonArray.getJSONObject(0).getJSONArray("breeds").getJSONObject(0).getString("origin"));
                            model.setWikiLink(jsonArray.getJSONObject(0).getJSONArray("breeds").getJSONObject(0).getString("wikipedia_url"));
                            model.setDogFrindlinesslevel(jsonArray.getJSONObject(0).getJSONArray("breeds").getJSONObject(0).getInt("dog_friendly"));
                            model.setWeightImperial(jsonArray.getJSONObject(0).getJSONArray("breeds").getJSONObject(0).getJSONObject("weight").getString("imperial"));
                            model.setWeightMetric(jsonArray.getJSONObject(0).getJSONArray("breeds").getJSONObject(0).getJSONObject("weight").getString("metric"));
                            displayResults();

                        }

                        catch (JSONException e)
                        {
                            e.printStackTrace();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error)
                    {
                        loadingProgress.setVisibility(View.GONE);
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

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    }


    public void displayResults()
    {

        if(model.getImageUrl().isEmpty())
        {
            mImage.setVisibility(View.GONE);
        }


        if(list!=null)
        {
            if(list.contains(model.getName()))
            {
                mFavouriteImage.setBackground(getResources().getDrawable(R.drawable.ic_star));
                isFavourite=true;
            }

            else
            {
                mFavouriteImage.setBackground(getResources().getDrawable(R.drawable.ic_star_empty));
                isFavourite=false;
            }
        }



        detailsLayout.setVisibility(View.VISIBLE);
        mTitleText.setText(model.getName());
        mDescText.setText(model.getDescription());
        mTvOriginText.setText(model.getOrigin());
        mTvTemperamentText.setText(model.getTemperament());
        mWeightText.setText("Imperial " +model.getWeightImperial()+ " \t\t\tMetric:  "+model.getWeightMetric());
        mDogLevelTxt.setText(String.valueOf(model.getDogFrindlinesslevel()));
        mLifeSpanTxt.setText(model.getLifeSpan());
        mWikiLinkTxt.setText(model.getWikiLink());
        Glide.with(this).load(model.getImageUrl()).thumbnail(Glide.with(this).load(R.drawable.place_holder)).into(mImage);

    }

}
