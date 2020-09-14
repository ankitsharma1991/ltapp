package com.accusterltapp.database;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class SqlKeyValueTable implements IKeyValueTable {

    private final SQLiteDatabase mDatabase;
    private static Map<String, String> mCachedKeyValueStore = null;
    private static final String TABLE_NAME = "PB_KVStore";

    private enum fields {
        KEY, VALUE
    }

    public SqlKeyValueTable(SQLiteDatabase database) {
        mDatabase = database;
    }

    @Override
    public synchronized void set(String key, String value) {

        if (mCachedKeyValueStore != null) {
            mCachedKeyValueStore.put(key, value);
        }

        ContentValues values = new ContentValues();
        values.put(fields.KEY.toString(), key);
        values.put(fields.VALUE.toString(), value);

        mDatabase.replace(TABLE_NAME, null, values);
    }

    @Override
    public synchronized String get(String key) {
        if (mCachedKeyValueStore == null) {
            mCachedKeyValueStore = Collections.synchronizedMap(new HashMap<String, String>());
            fetchAllKeyValuePairs();
        }

        String value = mCachedKeyValueStore.get(key);
        return value == null ? "" : value;
    }

    @Override
    public String get(String key, String defaultValue) {
        String value = get(key);
        if (value.equals("")) {
            value = defaultValue;
        }
        return value;
    }

    @Override
    public synchronized void delete(String key) {

        if (mCachedKeyValueStore != null) {
            mCachedKeyValueStore.remove(key);
        }

        String where = fields.KEY + " = ?";
        String whereArgs[] = { key };
        mDatabase.delete(TABLE_NAME, where, whereArgs);
    }


    private void fetchAllKeyValuePairs() {

        String[] columns = { fields.KEY.toString(), fields.VALUE.toString() };
        Cursor cursor = mDatabase.query(TABLE_NAME, columns, null, null, null, null, null);

        if (cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                mCachedKeyValueStore.put(cursor.getString(0), cursor.getString(1));
            }
        }
        cursor.close();
    }
}
