package com.accusterltapp.table;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.accusterltapp.database.BaseTable;
import com.accusterltapp.database.LtSqliteOpenHelper;
import com.accusterltapp.model.SubTestDetails;
import com.accusterltapp.model.TestDetails;

import java.util.ArrayList;

/**
 * Created by LoB Android on 14/12/17.
 */

public class TableGenericPacakge extends BaseTable {

    private Context mContext;
    public static final String TABLE_TEST_PACKAGE = "tbl_generic_pack";
    public static final String TEST_PROFILE_ID = "test_profile_id";
    public static final String TEST_PROFILE_NAME = "test_profile_name";

    public static final String create_table_generic_package = "create table if not exists " + TABLE_TEST_PACKAGE + " ("
            + TEST_PROFILE_ID + " integer primary key autoincrement, "
            + TEST_PROFILE_NAME + " text not null unique);";

    public TableGenericPacakge(Context context) {
        mContext = context;
        mDbObject = LtSqliteOpenHelper.getInstance(context).getReadableDatabase();

    }

    public boolean isTableEmpty() {
        Cursor mcursor = makeRawQuery("SELECT count(*)AS count FROM " + TABLE_TEST_PACKAGE);
        mcursor.moveToFirst();
        return mcursor.getInt(mcursor.getColumnIndex("count")) <= 0;
    }


    private TestDetails addPackageDetails(Cursor mcursor) {
        TestDetails details = new TestDetails();
        details.setTest_type_name(mcursor.getString(mcursor.getColumnIndex(TEST_PROFILE_NAME)));
        TableGenericTest subTestDetails = new TableGenericTest(mContext);
        details.setTest_list(subTestDetails.getAllSubTestList(new ArrayList<SubTestDetails>(),mcursor.getString(mcursor.getColumnIndex(TEST_PROFILE_NAME))));
        return details;
    }

    private ContentValues getContentValues(TestDetails packageDetails) {
        ContentValues values = new ContentValues();
        values.put(TEST_PROFILE_NAME, packageDetails.getTest_name());
        TableGenericTest subTestDetails = new TableGenericTest(mContext);
        subTestDetails.insertSubTestDetailsList(packageDetails.getTest_list());
        return values;
    }

    public void insertPackageList(ArrayList<TestDetails> accountData) {
        mDbObject.beginTransaction();
        try {
            for (TestDetails testData : accountData) {
                mDbObject.insertWithOnConflict(TABLE_TEST_PACKAGE, null, getContentValues(testData),
                        SQLiteDatabase.CONFLICT_REPLACE);
            }
            mDbObject.setTransactionSuccessful();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            mDbObject.endTransaction();
        }
    }

    public void insertPacakge(TestDetails campDetails) {
        mDbObject.beginTransaction();
        try {
            ContentValues contentValues = new ContentValues();
            mDbObject.insertWithOnConflict(TABLE_TEST_PACKAGE, null, getContentValues(campDetails)
                    , SQLiteDatabase.CONFLICT_REPLACE);
            contentValues.clear();
            mDbObject.setTransactionSuccessful();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            mDbObject.endTransaction();
        }
    }


    public ArrayList<TestDetails> getPackageList(ArrayList<TestDetails> patientList) {
        Cursor mcursor = makeRawQuery("SELECT *  from " + TABLE_TEST_PACKAGE);
        if (mcursor != null && mcursor.moveToFirst()) {
            do {
                patientList.add(addPackageDetails(mcursor));
            } while (mcursor.moveToNext());
        }
        return patientList;
    }

}
