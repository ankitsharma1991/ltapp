package com.base.utility;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.v4.content.ContextCompat;

/**
 * Created by OnSpon on 15/10/16.
 */

public class Resource {

    public static Drawable getDrawableResource(Context context, int drawableId) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {

            return ContextCompat.getDrawable(context, drawableId);
        } else {
            return context.getResources().getDrawable(drawableId);
        }
    }

    public static int getColor(Context context, int colorId) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            return ContextCompat.getColor(context, colorId);
        } else {
            return context.getResources().getColor(colorId);
        }
    }


}
