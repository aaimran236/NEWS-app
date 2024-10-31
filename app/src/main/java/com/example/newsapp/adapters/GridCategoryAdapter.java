package com.example.newsapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.newsapp.R;

import java.util.ArrayList;
import java.util.List;

public class GridCategoryAdapter extends BaseAdapter {

    LayoutInflater layoutInflater;
    Context context;
    List<DemoCategories> demoCategories;

    public GridCategoryAdapter(Context context){
        this.context=context;
        layoutInflater=(LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        demoCategories=new ArrayList<>();

        demoCategories.add(new DemoCategories(R.drawable.icn1, "Basics"));
        demoCategories.add(new DemoCategories(R.drawable.icn2, "Logic"));
        demoCategories.add(new DemoCategories(R.drawable.icn3, "Android"));
        demoCategories.add(new DemoCategories(R.drawable.icn4, "Binary"));
        demoCategories.add(new DemoCategories(R.drawable.icn5, "Components"));
        demoCategories.add(new DemoCategories(R.drawable.icn6, "Java"));
        demoCategories.add(new DemoCategories(R.drawable.icn7, "HTML"));
        demoCategories.add(new DemoCategories(R.drawable.icn8, "Database"));
    }
    @Override
    public int getCount() {
        return demoCategories.size();
    }

    @Override
    public Object getItem(int i) {
        return demoCategories.get(i);
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
        holder.textView.setText(demoCategories.get(i).imageName);
        Glide.with(context).load(demoCategories.get(i).imageId).into(holder.circularImage);

        return view;
    }

    public static class GridViewHolder{
        ImageView circularImage;
        TextView textView;
    }

    static class DemoCategories {
        int imageId;
        String imageName;

        public DemoCategories(int imageId, String imageName) {
            this.imageId = imageId;
            this.imageName = imageName;
        }
    }
}
