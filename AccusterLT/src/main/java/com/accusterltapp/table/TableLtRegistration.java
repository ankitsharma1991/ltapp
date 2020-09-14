package com.accusterltapp.table;

import android.content.Context;
import android.database.Cursor;

import com.accusterltapp.database.BaseTable;
import com.accusterltapp.database.LtSqliteOpenHelper;

/**
 * Created by pbadmin on 12/12/17.
 */

public class TableLtRegistration extends BaseTable {

    private Context mContext;
    public static final String TABLE_LT_PACKAGE = "tbl_lt_profile";
    public static final String TEST_PROFILE_ID = "test_profile_id";
    public static final String TEST_PROFILE_NAME = "test_profile_name";

    public static final String create_table_test_package = "create table if not exists " + TABLE_LT_PACKAGE + " ("
            + TEST_PROFILE_ID + " integer primary key autoincrement, "
            + TEST_PROFILE_NAME + " text);";

    public TableLtRegistration(Context context) {
        mContext = context;
        mDbObject = LtSqliteOpenHelper.getInstance(context).getReadableDatabase();

    }

    public boolean isTableEmpty() {
        Cursor mcursor = makeRawQuery("SELECT count(*)AS count FROM " + TABLE_LT_PACKAGE);
        mcursor.moveToFirst();
        return mcursor.getInt(mcursor.getColumnIndex("count")) <= 0;
    }
}
