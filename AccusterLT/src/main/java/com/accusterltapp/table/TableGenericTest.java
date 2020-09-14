package com.accusterltapp.table;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.accusterltapp.database.BaseTable;
import com.accusterltapp.database.LtSqliteOpenHelper;
import com.accusterltapp.model.SubTestDetails;
import com.google.gson.Gson;

import java.util.ArrayList;

/**
 * Created by LoB Android on 14/12/17.
 */
public class TableGenericTest extends BaseTable {
    public static final String TABLE_TEST_DETAILS = "tbl_generic_test";
    private static final String COLUMN_ID = "_id";
    public static final String TEST_NAME = "test_name";
    public static final String TEST_CODE = "test_code";
    public static final String TEST_RESULT = "test_result";
    public static final String TEST_COST = "test_cost";
    public static final String TEST_UNITS = "test_unit";
    public static final String TEST_RANGE = "test_range";
    public static final String MANUAL_RESULT = "manual_result";
    public static final String MALE_UPPER_BOUND = "m_upper";
    public static final String FEMALE_UPPER_BOUND = "f_upper";
    public static final String MALE_LOWER_BOUND = "m_lower";
    public static final String FEMALE_LOWER_BOUND = "f_lower";
    public static final String TEST_ID = "t_id";
    public static final String TEST_PROFILE_NAME = "test_profile_name";
    public static final String create_table_generic_test_Details = "create table if not exists " + TABLE_TEST_DETAILS + " ("
            + COLUMN_ID + " integer primary key autoincrement, "
            + TEST_NAME + " text not null unique, "
            + TEST_CODE + " text , "
            + TEST_UNITS + " text  , "
            + TEST_RANGE + " text , "
            + TEST_ID + " text , "
            +"test_interpretation" + " text , "
            +"test_precautions"+" text , "
            +"image_permission"+" text , "
            + MALE_UPPER_BOUND + " text , "
            + FEMALE_UPPER_BOUND + " text , "
            + MALE_LOWER_BOUND + " text , "
            + FEMALE_LOWER_BOUND + " text , "
            + TEST_PROFILE_NAME + " text , "
            + TEST_RESULT + " text, "
            + MANUAL_RESULT + " INTEGER DEFAULT 1, "
            + TEST_COST + " text );";
    public TableGenericTest(Context context) {
        mDbObject = LtSqliteOpenHelper.getInstance(context).getReadableDatabase();
    }
    public boolean isTableEmpty() {
        Cursor mcursor = makeRawQuery("SELECT count(*)AS count FROM " + TABLE_TEST_DETAILS);
        mcursor.moveToFirst();
        return mcursor.getInt(mcursor.getColumnIndex("count")) <= 0;
    }
    private SubTestDetails addCampToList(Cursor mcursor) {
        SubTestDetails details = new SubTestDetails();
        details.setTest_interpretation(mcursor.getString(mcursor.getColumnIndex("test_interpretation")));
        details.setTest_precautions(mcursor.getString(mcursor.getColumnIndex("test_precautions")));
        details.setTest_name(mcursor.getString(mcursor.getColumnIndex(TEST_NAME)));
        details.setImage_permission(mcursor.getString(mcursor.getColumnIndex("image_permission")));
        details.setTest_code(mcursor.getString(mcursor.getColumnIndex(TEST_CODE)));
        details.setTest_result(mcursor.getString(mcursor.getColumnIndex(TEST_RESULT)));
        details.setTest_price(mcursor.getString(mcursor.getColumnIndex(TEST_COST)));
        details.setTest_manual_status(mcursor.getInt(mcursor.getColumnIndex(MANUAL_RESULT)));
        details.setTest_unit(mcursor.getString(mcursor.getColumnIndex(TEST_UNITS)));
        details.setTest_range(mcursor.getString(mcursor.getColumnIndex(TEST_RANGE)));
        details.setTest_low_bound_female(mcursor.getString(mcursor.getColumnIndex(FEMALE_LOWER_BOUND)));
        details.setTest_low_bound_male(mcursor.getString(mcursor.getColumnIndex(MALE_LOWER_BOUND)));
        details.setTest_upper_bound_female(mcursor.getString(mcursor.getColumnIndex(FEMALE_UPPER_BOUND)));
        details.setTest_upper_bound_male(mcursor.getString(mcursor.getColumnIndex(MALE_UPPER_BOUND)));
        details.setTest_id(mcursor.getString(mcursor.getColumnIndex(TEST_ID)));
        details.setTest_type_name(mcursor.getString(mcursor.getColumnIndex(TEST_PROFILE_NAME)));
        return details;
    }
    private ContentValues getContentValues(SubTestDetails testDetails) {
        ContentValues values = new ContentValues();
        values.put(TEST_NAME, testDetails.getTest_name());
        values.put(TEST_CODE, testDetails.getTest_code());
        values.put("image_permission",testDetails.getImage_permission());
        values.put(TEST_COST, testDetails.getTest_price());
        values.put(TEST_UNITS, testDetails.getTest_unit());
        values.put(TEST_RANGE, testDetails.getTest_range());
        values.put(MANUAL_RESULT, testDetails.isTest_manual_status());
        values.put(FEMALE_LOWER_BOUND, testDetails.getTest_low_bound_female());
        values.put(MALE_LOWER_BOUND, testDetails.getTest_low_bound_male());
        values.put(FEMALE_UPPER_BOUND, testDetails.getTest_upper_bound_female());
        values.put(MALE_UPPER_BOUND, testDetails.getTest_upper_bound_male());
        values.put(TEST_ID, testDetails.getTest_id());
        values.put(TEST_PROFILE_NAME,testDetails.getTest_type_name());
        Log.e("test pri and in "," mohit"+testDetails.getTest_precautions());
        values.put("test_precautions",testDetails.getTest_precautions());
        values.put("test_interpretation",testDetails.getTest_interpretation());
        return values;
    }
    public void insertSubTestDetailsList(ArrayList<SubTestDetails> accountData) {
        mDbObject.beginTransaction();
        try {
            for (SubTestDetails testData : accountData) {
                mDbObject.insertWithOnConflict(TABLE_TEST_DETAILS, null, getContentValues(testData),
                        SQLiteDatabase.CONFLICT_REPLACE);
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
            ContentValues contentValues = new ContentValues();
            mDbObject.insertWithOnConflict(TABLE_TEST_DETAILS, null, getContentValues(campDetails)
                    , SQLiteDatabase.CONFLICT_REPLACE);
            contentValues.clear();
            mDbObject.setTransactionSuccessful();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            mDbObject.endTransaction();
        }
    }
    public ArrayList<SubTestDetails> getAllSubTestList(ArrayList<SubTestDetails> patientList, String testTypeName) {
        Cursor mcursor = makeRawQuery("SELECT *  from " + TABLE_TEST_DETAILS+" where "+TEST_PROFILE_NAME+"='"+testTypeName+"'");
        if (mcursor != null && mcursor.moveToFirst()) {
            do {
                patientList.add(addCampToList(mcursor));
            } while (mcursor.moveToNext());
        }
        return patientList;
    }
}
