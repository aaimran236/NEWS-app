package com.example.newsapp;

import android.os.Bundle;
import android.widget.GridView;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.newsapp.adapters.GridCategoryAdapter;
import com.example.newsapp.adapters.NewsAdapter;
import com.example.newsapp.adapters.SlideAdapter;
import com.example.newsapp.model.HomePageModel;
import com.example.newsapp.rest.ApiClient;
import com.example.newsapp.rest.ApiInterface;
import com.google.android.material.appbar.MaterialToolbar;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    private RecyclerView sliderRecycler;
    private ArrayList<String> images=new ArrayList<>();
    private GridView gridView;
    private GridCategoryAdapter adapter;

    ///we will load real news from our custom website
    private RecyclerView recyclerView;
    private NewsAdapter newsAdapter;
    private List<HomePageModel.News> news;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       /// EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        MaterialToolbar toolbar=findViewById(R.id.toolBar);
        setSupportActionBar(toolbar);

        initiateViews();
        ///addToSliderRecycler();

        ///Getting data
        getHomeData();
    }

    private void getHomeData() {
        ApiInterface apiInterface= ApiClient.getApiClient().create(ApiInterface.class);
        Map<String,String> params=new HashMap<>();
        params.put("page",1+"");
        params.put("posts",10+"");

        Call<HomePageModel> call=apiInterface.getHomePageApi(params);

        call.enqueue(new Callback<HomePageModel>() {
            @Override
            public void onResponse(Call<HomePageModel> call, Response<HomePageModel> response) {
                updateDataOnHomePage(response.body());
            }

            @Override
            public void onFailure(Call<HomePageModel> call, Throwable throwable) {

            }
        });
    }

    private void updateDataOnHomePage(HomePageModel body) {

        for(int i=0;i<body.getBanners().size();i++){
            images.add(body.getBanners().get(i).getImage());
        }

        SlideAdapter slideAdapter=new SlideAdapter(MainActivity.this,images);
        slideAdapter.setOnItemClickListener(new SlideAdapter.OnItemClickListener() {
            @Override
            public void onClick(ImageView imageView, String url) {

            }
        });
        sliderRecycler.setAdapter(slideAdapter);


        for (int i=0;i<body.getNews().size();i++){
            news.add(body.getNews().get(i));
        }
        recyclerView.setAdapter(newsAdapter);
    }

    private void initiateViews() {
        sliderRecycler=findViewById(R.id.slider_recycler);
        gridView=findViewById(R.id.grid_view);
        adapter=new GridCategoryAdapter(this.gridView.getContext());
        gridView.setAdapter(adapter);

        recyclerView=findViewById(R.id.rec_news);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setNestedScrollingEnabled(false);

        news=new ArrayList<>();
        newsAdapter=new NewsAdapter(this,news);
    }

    private void addToSliderRecycler() {
        images=new ArrayList<>();
        SlideAdapter slideAdapter=new SlideAdapter(MainActivity.this,images);
        slideAdapter.setOnItemClickListener(new SlideAdapter.OnItemClickListener() {
            @Override
            public void onClick(ImageView imageView, String url) {

            }
        });
        sliderRecycler.setAdapter(slideAdapter);
    }

}