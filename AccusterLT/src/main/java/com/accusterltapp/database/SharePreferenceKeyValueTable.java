package com.accusterltapp.database;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class SharePreferenceKeyValueTable {
    private SharedPreferences mSharedPreferences;

    public SharedPreferences getSharedPreferences() {
        return mSharedPreferences;
    }

    public SharePreferenceKeyValueTable(Context context) {
        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
    }

    public String get(String key) {
        return mSharedPreferences.getString(key, "");
    }

    public void put(String key, String value) {
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.putString(key, value);
        editor.apply();
    }

    public void delete(String key) {
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.remove(key);

//        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.GINGERBREAD) {
//            editor.commit();
//        } else {
        editor.apply();
//        }
    }

}
