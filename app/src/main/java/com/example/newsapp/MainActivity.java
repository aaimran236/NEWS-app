package com.example.newsapp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.NestedScrollingChild;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.newsapp.activities.YoutubeActivity;
import com.example.newsapp.adapters.GridCategoryAdapter;
import com.example.newsapp.adapters.NewsAdapter;
import com.example.newsapp.adapters.SlideAdapter;
import com.example.newsapp.model.HomePageModel;
import com.example.newsapp.rest.ApiClient;
import com.example.newsapp.rest.ApiInterface;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

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
    private List<HomePageModel.CategoryBotton> categoryBottons;

    ///Variable for infinite news feeds
    private int posts=3;
    private static int page=1;
    private boolean isFromStart=true;

    ///Progress bar
    private ProgressBar progressBar;

    private NestedScrollView nestedScrollView;

    private SwipeRefreshLayout swipeRefreshLayout;

    private FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       /// EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

//        MaterialToolbar toolbar=findViewById(R.id.toolBar);
//        setSupportActionBar(toolbar);

        initiateViews();
        ///addToSliderRecycler();

        ///Initial Condition
        page=1;
        isFromStart=true;

        ///Getting data
        getHomeData();

        ///Getting new data on scrolling and swiping down
        nestedScrollView.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(@NonNull NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                if (scrollY== (v.getChildAt(0).getMeasuredHeight()-v.getMeasuredHeight())){
                    isFromStart=false;
                    progressBar.setVisibility(View.VISIBLE);
                    page++;
                    getHomeData();
                }
            }
        });
    }

    private void getHomeData() {
        ApiInterface apiInterface= ApiClient.getApiClient().create(ApiInterface.class);
        Map<String,String> params=new HashMap<>();
        params.put("page",page+"");
        params.put("posts",posts+"");

        Call<HomePageModel> call=apiInterface.getHomePageApi(params);

        call.enqueue(new Callback<HomePageModel>() {
            @Override
            public void onResponse(Call<HomePageModel> call, Response<HomePageModel> response) {
                updateDataOnHomePage(response.body());
            }

            @Override
            public void onFailure(Call<HomePageModel> call, Throwable throwable) {
                progressBar.setVisibility(View.GONE);
                swipeRefreshLayout.setRefreshing(false);
            }
        });
    }

    private void updateDataOnHomePage(HomePageModel body) {
        progressBar.setVisibility(View.GONE);
        swipeRefreshLayout.setRefreshing(false);

        if (isFromStart){
            images.clear();
            news.clear();
            categoryBottons.clear();
        }

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

        int beforeNewsSize=news.size();

        for (int i=0;i<body.getNews().size();i++){
            news.add(body.getNews().get(i));
        }

        categoryBottons.addAll(body.getCategoryBotton());

        if (isFromStart){
            recyclerView.setAdapter(newsAdapter);
            gridView.setAdapter(adapter);
        }else{
            newsAdapter.notifyItemRangeChanged(beforeNewsSize,body.getNews().size());
        }




    }

    @SuppressLint("ResourceAsColor")
    private void initiateViews() {
        fab=findViewById(R.id.floatings);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MainActivity.this, YoutubeActivity.class);
                startActivity(intent);
            }
        });

        categoryBottons=new ArrayList<>();

        sliderRecycler=findViewById(R.id.slider_recycler);

        gridView=findViewById(R.id.grid_view);
        adapter=new GridCategoryAdapter(this,categoryBottons);

        ///Progressbar
        progressBar=findViewById(R.id.progressBar);

        ///ScrollView
        nestedScrollView=findViewById(R.id.nested_scrollView);

        ///Initialize recyclerview
        recyclerView=findViewById(R.id.rec_news);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        ///recyclerView.setHasFixedSize(true);
        recyclerView.setNestedScrollingEnabled(false);

        news=new ArrayList<>();
        newsAdapter=new NewsAdapter(this,news);

        swipeRefreshLayout=findViewById(R.id.swipy);
        swipeRefreshLayout.setRefreshing(true);
        swipeRefreshLayout.setColorSchemeResources(R.color.orange,R.color.blue,R.color.green);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                isFromStart=true;
                page=1;
                getHomeData();
            }
        });
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