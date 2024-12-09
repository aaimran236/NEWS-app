package com.example.newsapp.fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.newsapp.R;
import com.example.newsapp.Utils;
import com.example.newsapp.adapters.YoutubeAdapter;
import com.example.newsapp.model.HomePageModel;
import com.example.newsapp.model.YtModel;
import com.example.newsapp.rest.ApiClient;
import com.example.newsapp.rest.ApiInterface;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class FragmentYoutube extends Fragment {


    RecyclerView recyclerView;
    SwipeRefreshLayout swipeRefreshLayout;
    ProgressBar progressBar;
    Context context;

    String cid, pageToken= "";

    YoutubeAdapter youtubeAdapter;
    List<YtModel.Item> items = new ArrayList<>();
    @Override
    public void onAttach(@NonNull Context context)

    {
        super.onAttach(context);
        this.context = context;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_youtube, container,false);
        recyclerView  = view.findViewById(R.id.video_recycler);
        swipeRefreshLayout = view.findViewById(R.id.swipe);
        progressBar = view.findViewById(R.id.progress_bar);



        youtubeAdapter=  new YoutubeAdapter(context, items);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(linearLayoutManager);



        cid = getArguments().getString("cid");
        LoadDataFromYoutubeServer();

        return view;
    }

    private void LoadDataFromYoutubeServer() {
        // This will load data from youtube server (Not from our local host)

        ApiInterface apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Map<String, String> params = new HashMap<>();
        params.put("part", "snippet");
        params.put("channelId", cid);
        params.put("maxResults", "20");
       params.put("pageToken", pageToken);
        params.put("key", Utils.api_key);


        Call<YtModel> call = apiInterface.getYoutubeServerData(params);
        call.enqueue(new Callback<YtModel>() {
            @Override
            public void onResponse(Call<YtModel> call, Response<YtModel> response) {
                if(response.body()==null){
                    Toast.makeText(getContext(),"null",Toast.LENGTH_SHORT).show();
                }else{
                    items.clear();
                    items.addAll(response.body().getItems());
                    recyclerView.setAdapter(youtubeAdapter);
                }

            }

            @Override
            public void onFailure(Call<YtModel> call, Throwable t) {
                Toast.makeText(context, t.getMessage(), Toast.LENGTH_SHORT).show();
                swipeRefreshLayout.setRefreshing(false);
                progressBar.setVisibility(View.GONE);
            }
        });


    }
}