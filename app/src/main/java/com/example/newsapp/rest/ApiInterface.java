package com.example.newsapp.rest;

import com.example.newsapp.model.HomePageModel;
import com.example.newsapp.model.OurYtModel;
import com.example.newsapp.model.YtModel;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

public interface ApiInterface {
    @GET("homepage_api")
    Call<HomePageModel> getHomePageApi(@QueryMap Map<String,String> params);

    ///Getting news details by post id(pid)
    @GET("news_by_pid")
    Call<HomePageModel> getNewsDetailsById(@QueryMap Map<String,String> params);


    ///Getting youtube  details
    @GET("youtube")
    Call<OurYtModel> getYoutubeDetailsFromServer();


    /// ///Getting youtube  details
    @GET("https://www.googleapis.com/youtube/v3/activities")
    Call<YtModel> getYoutubeServerData(@QueryMap Map<String,String> params);

}
