package com.accusterltapp.service;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.app.ProgressDialog;
import android.content.Context;
import android.util.Log;
import android.view.animation.DecelerateInterpolator;

/**
 * Created by pbadmin on 19/7/17.
 */

public class ProgressBar {
    public static ProgressDialog mProgressDialog;
    public static ObjectAnimator animation;

    public static void showDialog(Context context, String message, boolean isCancelable, boolean forSyncData) {
        try {
            if (mProgressDialog==null ||!mProgressDialog.isShowing()) {
            mProgressDialog = new ProgressDialog(context);
            mProgressDialog.setMessage(message);
            if (forSyncData){

                mProgressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);

                animation = ObjectAnimator.ofInt(mProgressDialog, "progress", 0, 100);
                animation.setDuration(10000);
                animation.setInterpolator(new DecelerateInterpolator());
                animation.addListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animator) { }

                    @Override
                    public void onAnimationEnd(Animator animator) {
                        Log.d("animation END","TESt");
                        //do something when the countdown is complete

                    }

                    @Override
                    public void onAnimationCancel(Animator animator) { }

                    @Override
                    public void onAnimationRepeat(Animator animator) { }
                });
                animation.start();

            }
            else{
            mProgressDialog.setIndeterminate(true);
            mProgressDialog.setCancelable(isCancelable);
            }
                mProgressDialog.show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void dismissDialog() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
    }
}
