package com.accusterltapp.table;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.accusterltapp.database.BaseTable;
import com.accusterltapp.database.LtSqliteOpenHelper;
import com.accusterltapp.model.Heleprec;
import com.accusterltapp.model.QcData;
import com.accusterltapp.model.WidalTestData;
import com.accusterltapp.model.WidalTestModel;

import java.util.ArrayList;

public class TableWidalData extends BaseTable {
    private Context mContext;
    public static final String TABLE_WIDAL_TEST  = "widal";
    public static final String TEST_ID = "test_id";
    public static final String WIDAL_TEST_NAME1 = "widal_test_name1";
    public static final String WIDAL_TEST_NAME2 = "widal_test_name2";
    public static final String WIDAL_TEST_NAME3 = "widal_test_name3";
    public static final String WIDAL_TEST_NAME4 = "widal_test_name4";
    public static final String WIDAL_TEST_HEAD1 = "widal_test_head1";
    public static final String WIDAL_TEST_HEAD2 = "widal_test_head2";
    public static final String WIDAL_TEST_HEAD3 = "widal_test_head3";
    public static final String WIDAL_TEST_HEAD4 = "widal_test_head4";
    public static final String WIDAL_TEST_HEAD5 = "widal_test_head5";
    public static  final String  PATIENT_ID="pid";
    public static  final String WIDAL_TEST_RESULT11="widal_result11";
    public static  final String WIDAL_TEST_RESULT12="widal_result12";
    public static  final String WIDAL_TEST_RESULT13="widal_result13";
    public static  final String WIDAL_TEST_RESULT14="widal_result14";
    public static  final String WIDAL_TEST_RESULT21="widal_result21";
    public static  final String WIDAL_TEST_RESULT22="widal_result22";
    public static  final String WIDAL_TEST_RESULT23="widal_result23";
    public static  final String WIDAL_TEST_RESULT24="widal_result24";
    public static  final String WIDAL_TEST_RESULT31="widal_result31";
    public static  final String WIDAL_TEST_RESULT32="widal_result32";
    public static  final String WIDAL_TEST_RESULT33="widal_result33";
    public static  final String WIDAL_TEST_RESULT34="widal_result34";
    public static  final String WIDAL_TEST_RESULT41="widal_result41";
    public static  final String WIDAL_TEST_RESULT42="widal_result42";
    public static  final String WIDAL_TEST_RESULT43="widal_result43";
    public static  final String WIDAL_TEST_RESULT44="widal_result44";
    public static final String SYN_STATUS = "SYN";
    public static  final String WIDAL_INTERPRETATION="widal_interpretation";
    public static  final String WIDAL_PRECAUTION="widal_precaution";
    public static  final  String CREATE_WIDAL_DATA_TABLE=" create table if not exists widal (test_id INTEGER PRIMARY KEY AUTOINCREMENT,SYN  TEXT DEFAULT 0 ,widal_test_name1 TEXT,pid TEXT,widal_test_name2 TEXT,widal_test_name3 TEXT,widal_test_name4 TEXT,widal_test_head1 TEXT,widal_test_head2 TEXT,widal_test_head3 TEXT,widal_test_head4 TEXT,widal_test_head5 TEXT,widal_precaution TEXT,widal_interpretation TEXT, unique ( pid))";
    public TableWidalData(Context context) {
        mDbObject = LtSqliteOpenHelper.getInstance(context).getWritableDatabase();
        Log.e("the data base ob",mDbObject+" data");
    }
    public boolean isTableEmpty() {
        Cursor mcursor = makeRawQuery("SELECT count(*)AS count FROM " + TABLE_WIDAL_TEST);
        mcursor.moveToFirst();
        return mcursor.getInt(mcursor.getColumnIndex("count")) <= 0;
    }
    private WidalTestData adddata(Cursor mcursor) {
        WidalTestData widalTestData=new  WidalTestData();
        widalTestData.setWidal_test_precaution(mcursor.getString(mcursor.getColumnIndex(WIDAL_PRECAUTION)));
        widalTestData.setWidal_test_interpretation(mcursor.getString(mcursor.getColumnIndex(WIDAL_INTERPRETATION)));
        widalTestData.setWidal_test_header_1(mcursor.getString(mcursor.getColumnIndex(WIDAL_TEST_HEAD1)));
        widalTestData.setWidal_test_header_2(mcursor.getString(mcursor.getColumnIndex(WIDAL_TEST_HEAD2)));
        widalTestData.setWidal_test_header_3(mcursor.getString(mcursor.getColumnIndex(WIDAL_TEST_HEAD3)));
        widalTestData.setWidal_test_header_4(mcursor.getString(mcursor.getColumnIndex(WIDAL_TEST_HEAD4)));
        widalTestData.setWidal_test_header_5(mcursor.getString(mcursor.getColumnIndex(WIDAL_TEST_HEAD5)));
        widalTestData.setWidal_test_left_1(mcursor.getString(mcursor.getColumnIndex(WIDAL_TEST_NAME1)));
        widalTestData.setWidal_test_left_2(mcursor.getString(mcursor.getColumnIndex(WIDAL_TEST_NAME2)));
        widalTestData.setWidal_test_left_3(mcursor.getString(mcursor.getColumnIndex(WIDAL_TEST_NAME3)));
        widalTestData.setWidal_test_left_4(mcursor.getString(mcursor.getColumnIndex(WIDAL_TEST_NAME4)));
        return widalTestData;
    }
    private ContentValues getContentValues(WidalTestData widalTestData) {
        ContentValues values = new ContentValues();
        values.put(WIDAL_TEST_NAME1,widalTestData.getWidal_test_left_1());
        values.put(WIDAL_TEST_NAME2,widalTestData.getWidal_test_left_2());
        values.put(WIDAL_TEST_NAME3,widalTestData.getWidal_test_left_3());
        values.put(WIDAL_TEST_NAME4,widalTestData.getWidal_test_left_4());
        values.put(WIDAL_TEST_HEAD1,widalTestData.getWidal_test_header_1());
        values.put(WIDAL_TEST_HEAD2,widalTestData.getWidal_test_header_2());
        values.put(WIDAL_TEST_HEAD3,widalTestData.getWidal_test_header_3());
        values.put(WIDAL_TEST_HEAD4,widalTestData.getWidal_test_header_4());
        values.put(WIDAL_TEST_HEAD5,widalTestData.getWidal_test_header_5());
        values.put(WIDAL_INTERPRETATION,widalTestData.getWidal_test_interpretation());
        values.put(WIDAL_PRECAUTION,widalTestData.getWidal_test_precaution());
        return values;
    }
    public void insertWidalData(WidalTestData data) {
        mDbObject.beginTransaction();
        try {
            mDbObject.insertWithOnConflict(TABLE_WIDAL_TEST, null,getContentValues(data), SQLiteDatabase.CONFLICT_REPLACE);
            mDbObject.setTransactionSuccessful();
            Log.e("Data insertid ","data");
        }
        catch (Exception e)
        {
            Log.e("data not insertid","data");
        }
        finally {
           mDbObject.endTransaction();
        }
    }
    public WidalTestData getWidalData() {
        WidalTestData widalTestData=null;
        Cursor mcursor = makeRawQuery("SELECT *  from " + TABLE_WIDAL_TEST);
        if (mcursor != null && mcursor.moveToFirst()) {
            do {
               widalTestData=adddata(mcursor);
            } while (mcursor.moveToNext());
        }
        return widalTestData;
    }
    public void deleteTableContent() {
        mDbObject.delete(TABLE_WIDAL_TEST, null, null);
    }
}
