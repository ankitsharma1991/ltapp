package com.accusterltapp.database;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

/**
 * Created by pbadmin on 1/11/17.
 */

public abstract class DataBaseHelper<T> {
    protected SQLiteDatabase database;
    protected LtSqliteOpenHelper DataBaseHelper;


    public DataBaseHelper(Context context) {
        DataBaseHelper = LtSqliteOpenHelper.getInstance(context);
        database = DataBaseHelper.getDatabase();
        /*if (!isOpen()) {
            open();
        }*/
    }

    /***
     * open database
     *
     * @throws SQLException
     */
    public synchronized void open() throws SQLException {
        database = DataBaseHelper.getWritableDatabase();
    }


    /**
     * check database is open or not
     *
     * @return check is db open
     * @throws SQLException
     */
    public boolean isOpen() throws SQLException {
        return database != null && database.isOpen();
    }

    protected void closeCursor(Cursor cursor) {
        if (cursor != null && !cursor.isClosed()) {
            cursor.close();
        }
    }

    /***
     * close datadase
     */
    public void close() {
        DataBaseHelper.close();
    }

    public abstract boolean insertDetail(T t);

    public abstract boolean updateDetail(T t);

    public abstract ArrayList<T> getAllDetail();
}
