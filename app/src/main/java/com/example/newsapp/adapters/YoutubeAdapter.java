package com.example.newsapp.adapters;

import android.content.Context;
import android.content.pm.LabeledIntent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.newsapp.R;
import com.example.newsapp.model.YtModel;

import java.util.ArrayList;
import java.util.List;

public class YoutubeAdapter  extends RecyclerView.Adapter<YoutubeAdapter.ViewHolder> {

    private Context context;
    private List<YtModel.Item> items=new ArrayList<>();

    public YoutubeAdapter(Context context, List<YtModel.Item> items) {
        this.context = context;
        this.items = items;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(
                R.layout.item_video,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        YtModel.Item singleVideoItem=items.get(holder.getAdapterPosition());
        if (singleVideoItem.getSnippet().getThumbnails().getStandard()!=null){
            Glide.with(context)
                    .load(singleVideoItem.getSnippet().getThumbnails().getStandard().getUrl())
                    .into(holder.videoThumbnail);
        }else {
            Glide.with(context)
                    .load(singleVideoItem.getSnippet().getThumbnails().getDefault().getUrl())
                    .into(holder.videoThumbnail);
        }
        holder.videoTitle.setText(singleVideoItem.getSnippet().getChannelTitle());
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView videoThumbnail;
        TextView videoTitle;
        View view;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            view=itemView;
            videoThumbnail=view.findViewById(R.id.thumbnail);
            videoTitle=view.findViewById(R.id.video_title);
        }
    }
}
