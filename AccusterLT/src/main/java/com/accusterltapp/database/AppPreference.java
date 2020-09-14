package com.accusterltapp.database;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by pbadmin on 27/8/17.
 */

public class AppPreference {
    private static final String PREFS_NAME = "lt app";
    public static final String PREF_BLUETOOTH_ADDRESS = "bluetooth_address";
    public static final String LT_NAME = "firstTime";
    public static final String E_MAIL = "email";
    public static final String PASSWORD = "password";
    public static final String IS_LOGIN = "login";
    public static final String USER_ID = "userID";
    public static final String TEST_TABLE_UPDATE = "testUpdate";
    public static final String ORGANISATION_ID = "org_id";
    public static final String ORGANISATION_NAME = "org_name";
    public static final String PATHOLOGIST_LIST = "path_list";
    public static final String IS_PACKAGE_ADDED = "pack_added";
    public static final String CAMP_POSITION = "pos_camp";


    private static SharedPreferences getSharedPreference(Context context) {
        return context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
    }

    //set boolean type data
    public static boolean getBoolean(Context context, String key) {

        return getSharedPreference(context).getBoolean(key, false);
    }

    public static void setBoolean(Context context, String key, boolean value) {

        SharedPreferences.Editor editor = getSharedPreference(context).edit();
        editor.putBoolean(key, value);
        editor.apply();
    }

    //set string type data
    public static void setString(Context context, String key, String value) {

        SharedPreferences.Editor editor = getSharedPreference(context).edit();
        editor.putString(key, value);
        editor.apply();
    }

    //set string type data
    public static void setInt(Context context, String key, int value) {

        SharedPreferences.Editor editor = getSharedPreference(context).edit();
        editor.putInt(key, value);
        editor.apply();
    }

    public static String getString(Context context, String key) {

        return getSharedPreference(context).getString(key, "");
    }


    public static int getInt(Context context, String key) {

        return getSharedPreference(context).getInt(key, 0);
    }


    public static void clearData(Context context) {
        getSharedPreference(context).edit().clear().apply();
    }
}
