package com.accusterltapp.database;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * table for all base handling
 */

public class BaseTable {

   public SQLiteDatabase mDbObject;

   public Cursor makeRawQuery(String query) {
        Cursor cursor = null;
        try {
            cursor = mDbObject.rawQuery(query, null);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return cursor;
    }


   public   void closeCursor(Cursor cursor){
        if(cursor != null && !cursor.isClosed()){
            cursor.close();
        }
    }

}

