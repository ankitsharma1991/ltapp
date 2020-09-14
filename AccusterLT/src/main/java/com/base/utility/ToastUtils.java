package com.base.utility;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by pbadmin on 22/1/17.
 */

public class ToastUtils {
    public static void showShortToastMessage(Context context, String msg) {

        if (context != null && msg != null) {
            Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
        }
    }

    public static void showLongToastMessage(Context context, String msg) {
        if (context != null && msg != null) {
            Toast.makeText(context, msg, Toast.LENGTH_LONG).show();
        }
    }
}
