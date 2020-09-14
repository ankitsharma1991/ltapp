package com.accusterltapp.service;

import android.app.ProgressDialog;
import android.content.Context;

/**
 * Created by pbadmin on 19/7/17.
 */

public class ProgressBar {
    private static ProgressDialog mProgressDialog;

    public static void showDialog(Context context, String message, boolean isCancelable) {
        try {
            if (mProgressDialog==null ||!mProgressDialog.isShowing()) {
            mProgressDialog = new ProgressDialog(context);
            mProgressDialog.setMessage(message);
            mProgressDialog.setIndeterminate(true);
            mProgressDialog.setCancelable(isCancelable);
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
