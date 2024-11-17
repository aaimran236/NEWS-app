package com.example.newsapp.adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.newsapp.R;
import com.example.newsapp.model.HomePageModel;

import java.util.ArrayList;
import java.util.List;

public class GridCategoryAdapter extends BaseAdapter {

    LayoutInflater layoutInflater;
    Context context;
    List<HomePageModel.CategoryBotton> categoryBottons;

    public GridCategoryAdapter(Context context,List<HomePageModel.CategoryBotton> categoryBottons){
        this.context=context;
        layoutInflater=(LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        this.categoryBottons=categoryBottons;

    }
    @Override
    public int getCount() {
        return categoryBottons.size();
    }

    @Override
    public Object getItem(int i) {
        return categoryBottons.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        GridViewHolder holder=null;
        if (view==null){
            view=layoutInflater.inflate(R.layout.item_category_layout,null);
            holder=new GridViewHolder();
            holder.circularImage=view.findViewById(R.id.category_image);
            holder.textView=view.findViewById(R.id.category_name);
            view.setTag(holder);
        }else {
            holder=(GridViewHolder) view.getTag();
        }
        holder.textView.setText(categoryBottons.get(i).getName());
        Glide.with(context).load(categoryBottons.get(i).getImage()).into(holder.circularImage);

        if (categoryBottons.get(i).getColor()!=null){
            holder.circularImage.setBackgroundColor(Color.parseColor(categoryBottons.get(i).getColor()));
        }

        return view;
    }

    public static class GridViewHolder{
        ImageView circularImage;
        TextView textView;
    }

}
