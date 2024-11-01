package com.example.newsapp.rest;

import com.example.newsapp.model.HomePageModel;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;

public interface ApiInterface {
    @GET("homepage_api")
    Call<HomePageModel> getHomePageApi(@QueryMap Map<String,String> params);
}
