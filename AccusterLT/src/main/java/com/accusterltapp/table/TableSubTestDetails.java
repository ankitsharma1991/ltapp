package com.accusterltapp.table;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.accusterltapp.database.BaseTable;
import com.accusterltapp.database.LtSqliteOpenHelper;
import com.accusterltapp.model.SubTestDetails;

import java.util.ArrayList;

/**
 * Created by pbadmin on 11/12/17.
 */

public class TableSubTestDetails extends BaseTable {
    public static final String TABLE_TEST_DETAILS = "tbl_test_details";
    private static final String COLUMN_ID = "_id";
    public static final String TEST_NAME = "test_name";
    public static final String TEST_CODE = "test_code";
    public static final String TEST_RESULT = "test_result";
    public static final String TEST_COST = "test_cost";
    public static final String TEST_UNITS = "test_unit";
    public static final String TEST_RANGE = "test_range";
    public static final String MANUAL_RESULT = "manual_result";
    public static final String LT_ID = "lt_id";
    public static final String MALE_UPPER_BOUND = "m_upper";
    public static final String FEMALE_UPPER_BOUND = "f_upper";
    public static final String MALE_LOWER_BOUND = "m_lower";
    public static final String FEMALE_LOWER_BOUND = "f_lower";

    public static final String create_table_test_Details = "create table if not exists " + TABLE_TEST_DETAILS + " ("
            + COLUMN_ID + " integer primary key autoincrement, "
            + TEST_NAME + " text, "
            + TEST_CODE + " text not null , "
            + TEST_UNITS + " text not null , "
            + TEST_RANGE + " text not null , "
            + TEST_RESULT + " text, "
            + LT_ID + " text, "
            + MANUAL_RESULT + " INTEGER DEFAULT 1, "
            + TEST_COST + " text );";

    public TableSubTestDetails(Context context) {
        mDbObject = LtSqliteOpenHelper.getInstance(context).getReadableDatabase();

    }

    public boolean isTableEmpty() {
        Cursor mcursor = makeRawQuery("SELECT count(*)AS count FROM " + TABLE_TEST_DETAILS);
        mcursor.moveToFirst();
        return mcursor.getInt(mcursor.getColumnIndex("count")) <= 0;
    }


    private SubTestDetails addCampToList(Cursor mcursor) {
        SubTestDetails details = new SubTestDetails();
        details.setTest_name(mcursor.getString(mcursor.getColumnIndex(TEST_NAME)));
        details.setTest_code(mcursor.getString(mcursor.getColumnIndex(TEST_CODE)));
        details.setTest_result(mcursor.getString(mcursor.getColumnIndex(TEST_RESULT)));
        details.setTest_price(mcursor.getString(mcursor.getColumnIndex(TEST_COST)));
        details.setTest_manual_status(mcursor.getInt(mcursor.getColumnIndex(MANUAL_RESULT)));
        details.setTest_unit(mcursor.getString(mcursor.getColumnIndex(TEST_UNITS)));
        details.setTest_range(mcursor.getString(mcursor.getColumnIndex(TEST_RANGE)));
        return details;
    }

    private ContentValues getContentValues(SubTestDetails testDetails) {
        ContentValues values = new ContentValues();
        values.put(TEST_NAME, testDetails.getTest_name());
        values.put(TEST_CODE, testDetails.getTest_code());
        values.put(TEST_COST, testDetails.getTest_price());
        values.put(TEST_UNITS, testDetails.getTest_unit());
        values.put(TEST_RANGE, testDetails.getTest_range());
        values.put(MANUAL_RESULT, testDetails.isTest_manual_status());
        return values;
    }

    public void insertSubTestDetailsList(ArrayList<SubTestDetails> accountData) {
        mDbObject.beginTransaction();
        try {
            for (SubTestDetails testData : accountData) {
                mDbObject.insertWithOnConflict(TABLE_TEST_DETAILS, null, getContentValues(testData)
                        , SQLiteDatabase.CONFLICT_REPLACE);
            }
            mDbObject.setTransactionSuccessful();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            mDbObject.endTransaction();
        }
    }

    public void insertSubTestDetails(SubTestDetails campDetails) {
        mDbObject.beginTransaction();
        try {
            mDbObject.insertWithOnConflict(TABLE_TEST_DETAILS, null, getContentValues(campDetails)
                    , SQLiteDatabase.CONFLICT_REPLACE);
            mDbObject.setTransactionSuccessful();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            mDbObject.endTransaction();
        }
    }


    public ArrayList<SubTestDetails> getAllSubTestList(ArrayList<SubTestDetails> patientList) {
        Cursor mcursor = makeRawQuery("SELECT *  from " + TABLE_TEST_DETAILS);
        if (mcursor != null && mcursor.moveToFirst()) {
            do {
                patientList.add(addCampToList(mcursor));
            } while (mcursor.moveToNext());
        }
        return patientList;
    }

}
