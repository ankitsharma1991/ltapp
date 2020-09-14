package com.accusterltapp.service;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.signature.StringSignature;
import com.squareup.picasso.Picasso;

import ss.com.bannerslider.ImageLoadingService;

public class ImageLodingService implements ImageLoadingService {
    Context mcontext;
    public  ImageLodingService(Context mcontex)
    {
        this.mcontext=mcontex;
    }

    @Override
    public void loadImage(String url, ImageView imageView) {
      // RequestOptions options = new RequestOptions();

     //   options.fitCenter();
        Glide.with(mcontext).load(url).signature(new StringSignature(String.valueOf(System.currentTimeMillis()))).fitCenter().diskCacheStrategy(DiskCacheStrategy.NONE).into(imageView);
       //


    }

    @Override
    public void loadImage(int resource, ImageView imageView) {

    }

    @Override
    public void loadImage(String url, int placeHolder, int errorDrawable, ImageView imageView) {
        Glide.with(mcontext).load(url).signature(new StringSignature(String.valueOf(System.currentTimeMillis()))).fitCenter().diskCacheStrategy(DiskCacheStrategy.NONE)
                .into(imageView);

    }
}
