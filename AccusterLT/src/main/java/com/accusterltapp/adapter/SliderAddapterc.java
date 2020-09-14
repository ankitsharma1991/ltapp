package com.accusterltapp.adapter;

import android.content.Context;

import java.util.ArrayList;

import ss.com.bannerslider.adapters.SliderAdapter;
import ss.com.bannerslider.viewholder.ImageSlideViewHolder;

public class SliderAddapterc extends SliderAdapter {
    Context mcontext;
    ArrayList<String> list;
    public  SliderAddapterc(Context mcontext, ArrayList<String> list)
    {
        this.mcontext=mcontext;
        this.list=list;
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public void onBindImageSlide(int position, ImageSlideViewHolder imageSlideViewHolder) {
        imageSlideViewHolder.bindImageSlide("/storage/emulated/0/rapid/"+list.get(position));

    }
}
