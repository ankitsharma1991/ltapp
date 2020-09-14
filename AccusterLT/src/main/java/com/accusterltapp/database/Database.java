package com.accusterltapp.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;


public class Database implements IDatabase {

    private final Context mContext;
    private SQLiteDatabase mWritableDatabase;
    private SQLiteDatabase mReadableDatabase;
    private IKeyValueTable mSqlKeyValueStore;
    private SharePreferenceKeyValueTable mSharedPreferenceKeyValueStore;

    public Database(Context context) {

        mContext = context;
        mWritableDatabase = LtSqliteOpenHelper.getInstance(context).getWritableDatabase();
        mReadableDatabase = LtSqliteOpenHelper.getInstance(context).getReadableDatabase();
    }

    @Override
    public IKeyValueTable getSqlKeyValueTable() {
        if (mSqlKeyValueStore == null) {
            mSqlKeyValueStore = new SqlKeyValueTable(mWritableDatabase);
        }
        return mSqlKeyValueStore;
    }

    @Override
    public SharePreferenceKeyValueTable getSharedPreferenceKeyValueTable() {
        if (mSharedPreferenceKeyValueStore == null) {
            mSharedPreferenceKeyValueStore = new SharePreferenceKeyValueTable(mContext);
        }
        return mSharedPreferenceKeyValueStore;
    }
}
