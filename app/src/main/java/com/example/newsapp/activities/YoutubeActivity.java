package com.example.newsapp.activities;

import android.os.Bundle;

import android.view.View;
import android.widget.Toast;


import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import com.example.newsapp.R;
import com.example.newsapp.adapters.ViewPagerAdapter;
import com.example.newsapp.model.HomePageModel;
import com.example.newsapp.model.OurYtModel;
import com.example.newsapp.rest.ApiClient;
import com.example.newsapp.rest.ApiInterface;
import com.google.android.material.tabs.TabLayout;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class YoutubeActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private ViewPager viewPager;
    private TabLayout tabLayout;
    private ViewPagerAdapter viewPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_youtube);

//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
//            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
//            return insets;
//        });

        inItViews();

    }

    private void inItViews() {
        toolbar=findViewById(R.id.toolbary);
        viewPager=findViewById(R.id.view_pager);
        tabLayout=findViewById(R.id.tab_layout);

        tabLayout.setupWithViewPager(viewPager);

        ///toolbar
        // toolbar
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Youtube Videos");
        toolbar.setNavigationIcon(R.drawable.icon_arrow_back_white);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        ///Getting youtube data from our api
        getYoutubeData();
    }

    private void getYoutubeData() {

        ApiInterface apiInterface= ApiClient.getApiClient().create(ApiInterface.class);
        Call<OurYtModel> call=apiInterface.getYoutubeDetailsFromServer();
        
        call.enqueue(new Callback<OurYtModel>() {
            @Override
            public void onResponse(Call<OurYtModel> call, Response<OurYtModel> response) {
                viewPagerAdapter=new ViewPagerAdapter(getSupportFragmentManager(),
                        response.body(),
                        YoutubeActivity.this);
                viewPager.setAdapter(viewPagerAdapter);
            }

            @Override
            public void onFailure(Call<OurYtModel> call, Throwable throwable) {
                Toast.makeText(YoutubeActivity.this, "Sorry!!!", Toast.LENGTH_SHORT).show();
            }
        });

    }
}