package com.example.newsapp.rest;

import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Protocol;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {

    ///Default ip address for emulator 10.0.2.2
    public static final String BASE_URL="http://10.0.2.2/newsapp/wp-json/api/";

    ///insert laptop ip address after connecting the phone and laptop to the same Wi-Fi
    /// 192.168.x.x(insert your own api , run "ipconfig" in cmd) ip ,it keep changing everytime i connected with Wi-Fi
    public static final String BASE_URL_REAL="http://192.168.0.102/newsapp/wp-json/api/";

    private  static Retrofit retrofit=null;

    public static synchronized Retrofit getApiClient(){
       HttpLoggingInterceptor loggingInterceptor =new HttpLoggingInterceptor();
       loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client=new OkHttpClient.Builder()
                .protocols(List.of(Protocol.HTTP_1_1))
                .addInterceptor(loggingInterceptor)
                .build();

        if(retrofit==null){
            retrofit= new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(client)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        
        return  retrofit;
    }
}
