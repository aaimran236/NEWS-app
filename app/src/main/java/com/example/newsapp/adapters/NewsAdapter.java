package com.example.newsapp.adapters;

import android.content.Context;
import android.view.ContextMenu;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.newsapp.model.HomePageModel;

import java.util.List;

public class NewsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    Context context;
    List<HomePageModel> news;
    int img_left=1;
    int img_top=2;

    public NewsAdapter(Context context, List<HomePageModel> news) {
        this.context = context;
        this.news = news;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }
}
