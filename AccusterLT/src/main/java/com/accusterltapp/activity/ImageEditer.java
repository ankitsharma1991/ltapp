package com.accusterltapp.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.accusterltapp.R;



public class ImageEditer extends AppCompatActivity {
    //CropIwaView crop;
    Intent intent;
    RelativeLayout main;
    ImageView imagedd;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.imageediter);
     /*   main=findViewById(R.id.main);
        imagedd=findViewById(R.id.imagedd);
        BrushDrawingView brushDrawingView = (BrushDrawingView) findViewById(R.id.drawing_view);
        PhotoEditorSDK photoEditorSDK = new PhotoEditorSDK.PhotoEditorSDKBuilder(ImageEditer.this)
                .parentView(main)
//add parent image view
                .childView(imagedd)
//add the desired image view
                .deleteView(main)
//add the deleted view that will appear during the movement of the views
                .brushDrawingView(brushDrawingView)
// add the brush drawing view that is responsible for drawing on the image view
                .buildPhotoEditorSDK();
// build photo editor sdk


        photoEditorSDK.setBrushSize(18);
        photoEditorSDK.setBrushColor(Color.GREEN);
        photoEditorSDK.brushEraser();
        photoEditorSDK.setBrushEraserSize(25);
        photoEditorSDK.setBrushEraserColor(Color.YELLOW);*/
    }
}
