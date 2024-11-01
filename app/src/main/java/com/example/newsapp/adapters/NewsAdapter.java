package com.example.newsapp.adapters;

import android.content.Context;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.newsapp.R;
import com.example.newsapp.model.HomePageModel;

import java.util.List;

public class NewsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    Context context;
    List<HomePageModel.News> news;
    int img_left=1;
    int img_top=2;

    public NewsAdapter(Context context, List<HomePageModel.News> news) {
        this.context = context;
        this.news = news;
    }

    @Override
    public int getItemViewType(int position) {
        ///2 different layout in the same recyclerview
        if ((position+1)%8==0 || position==0){
            ///Loading the First item and every 8th item in the big layout
        return img_top;
        }else {
            return img_left;
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType==img_left){
            View v= LayoutInflater.from(context).inflate(R.layout.item_news,parent,false);
            return new ViewHolder(v);
        }else{
            View v= LayoutInflater.from(context).inflate(R.layout.item_news_image,parent,false);
            return new ViewHolder(v);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        HomePageModel.News singleNews=news.get(holder.getAdapterPosition());

        ViewHolder viewHolder=(ViewHolder) holder;
        viewHolder.newsTitle.setText(removeHtml(singleNews.getTitle()));
        viewHolder.newsDesc.setText(removeHtml(singleNews.getPostContent()));

        if (singleNews.getSource()!=null){
            viewHolder.newsSource.setText(singleNews.getSource());
        }

        Glide.with(context).load(singleNews.getImage())
                .into(viewHolder.newsImage);
    }

    @Override
    public int getItemCount() {
        return news.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        View holder;
        ImageView newsImage;
        TextView newsTitle,newsDesc,newsDate,newsSource,newsViews;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            holder=itemView;
            newsImage=holder.findViewById(R.id.news_image);
            newsTitle=holder.findViewById(R.id.news_title);
            newsDesc=holder.findViewById(R.id.news_description);
            newsDate=holder.findViewById(R.id.news_date);
            newsSource=holder.findViewById(R.id.news_source);
            newsViews=holder.findViewById(R.id.news_views);
        }
    }

    /// Removing HTML Codes
    public static String removeHtml(String html){
        html = html.replaceAll("<(.*?)\\>"," ");//Removes all items in brackets
        html = html.replaceAll("<(.*?)\\\n"," ");//Must be undeneath
        html = html.replaceFirst("(.*?)\\>", " ");//Removes any connected item to the last bracket
        html = html.replaceAll("&nbsp;"," ");
        html = html.replaceAll("&amp;"," ");
        return html;
        // Check the description and the source code if you want it
    }

}
