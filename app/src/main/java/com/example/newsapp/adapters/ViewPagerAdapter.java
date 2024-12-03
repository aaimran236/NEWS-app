package com.example.newsapp.adapters;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.newsapp.fragments.FragmentYoutube;
import com.example.newsapp.model.OurYtModel;

public class ViewPagerAdapter extends FragmentPagerAdapter {
    private Context context;
    private OurYtModel ourYtModel;
    public ViewPagerAdapter(@NonNull FragmentManager fm, OurYtModel ourYtModel,Context context) {
        super(fm, FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        this.ourYtModel=ourYtModel;
        this.context=context;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        Bundle bundle=new Bundle();
        bundle.putString("cid",ourYtModel.getYoutubeData().get(position).getChannelId());
        FragmentYoutube fragmentYoutube=new FragmentYoutube();
        fragmentYoutube.setArguments(bundle);
        return fragmentYoutube;
    }

    @Override
    public int getCount() {
        return ourYtModel.getYoutubeData().size();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return ourYtModel.getYoutubeData().get(position).getTitle();
    }
}
