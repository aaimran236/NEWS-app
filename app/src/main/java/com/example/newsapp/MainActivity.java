package com.example.newsapp;

import android.os.Bundle;
import android.widget.GridView;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.newsapp.adapters.GridCategoryAdapter;
import com.example.newsapp.adapters.SlideAdapter;
import com.example.newsapp.model.HomePageModel;
import com.example.newsapp.rest.ApiClient;
import com.example.newsapp.rest.ApiInterface;
import com.google.android.material.appbar.MaterialToolbar;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    private RecyclerView sliderRecycler;
    private ArrayList<String> images=new ArrayList<>();
    private GridView gridView;
    private GridCategoryAdapter adapter;


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
    }

    private void initiateViews() {
        sliderRecycler=findViewById(R.id.slider_recycler);
        gridView=findViewById(R.id.grid_view);
        adapter=new GridCategoryAdapter(this.gridView.getContext());
        gridView.setAdapter(adapter);
    }

    private void addToSliderRecycler() {
        images=new ArrayList<>();
        images.add("https://images.unsplash.com/photo-1506260408121-e353d10b87c7?q=80&w=1856&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D");
        images.add("https://images.unsplash.com/photo-1464822759023-fed622ff2c3b?q=80&w=2070&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D");
        images.add("https://images.unsplash.com/photo-1470071459604-3b5ec3a7fe05?q=80&w=1948&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D");
        images.add("https://images.unsplash.com/photo-1451337516015-6b6e9a44a8a3?q=80&w=1974&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D");
        images.add("https://images.unsplash.com/photo-1504217051514-96afa06398be?q=80&w=1888&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D");
        images.add("https://images.unsplash.com/photo-1433190152045-5a94184895da?q=80&w=2070&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D");

        
        SlideAdapter slideAdapter=new SlideAdapter(MainActivity.this,images);
        slideAdapter.setOnItemClickListener(new SlideAdapter.OnItemClickListener() {
            @Override
            public void onClick(ImageView imageView, String url) {

            }
        });
        sliderRecycler.setAdapter(slideAdapter);
    }

}