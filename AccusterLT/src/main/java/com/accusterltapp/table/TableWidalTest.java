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
import com.accusterltapp.model.WidalTest;

import java.util.ArrayList;
import java.util.Random;
public class TableWidalTest extends BaseTable {
    private Context mContext;
    public static final String TABLE_WIDAL_TEST  = "tbl_widaltest";
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
    public static  final String WIDAL_TEST_RESULT15="widal_result15";
    public static  final String WIDAL_TEST_RESULT21="widal_result21";
    public static  final String WIDAL_TEST_RESULT22="widal_result22";
    public static  final String WIDAL_TEST_RESULT23="widal_result23";
    public static  final String WIDAL_TEST_RESULT24="widal_result24";
    public static  final String WIDAL_TEST_RESULT25="widal_result25";
    public static  final String WIDAL_TEST_RESULT31="widal_result31";
    public static  final String WIDAL_TEST_RESULT32="widal_result32";
    public static  final String WIDAL_TEST_RESULT33="widal_result33";
    public static  final String WIDAL_TEST_RESULT34="widal_result34";
    public static  final String WIDAL_TEST_RESULT35="widal_result35";
    public static  final String WIDAL_TEST_RESULT41="widal_result41";
    public static  final String WIDAL_TEST_RESULT42="widal_result42";
    public static  final String WIDAL_TEST_RESULT43="widal_result43";
    public static  final String WIDAL_TEST_RESULT44="widal_result44";
    public static  final String WIDAL_TEST_RESULT45="widal_result45";
    public static final String SYN_STATUS = "SYN";
    public static  final String CREATE_TIME="create_time";
    public static  final String UPDATE_TIME="update_time";
    public static  final String WIDAL_INTERPRETATION="widal_interpretation";
    public static  final String WIDAL_PRECAUTION="widal_precaution";
    public  static  final String REPORT_ID="report_id";
  public static  final  String CREATE_WIDAL_TEST_TABLE=" create table if not exists tbl_widaltest (test_id INTEGER PRIMARY KEY AUTOINCREMENT,SYN  TEXT DEFAULT 0 ,widal_test_name1 TEXT,report_id TEXT,widal_result11 TEXT,widal_result12 TEXT, widal_result13 TEXT, widal_result14 TEXT, widal_result15 TEXT, widal_result21 TEXT, widal_result22 TEXT, widal_result23 TEXT, widal_result24 TEXT, widal_result25 TEXT ,widal_result31 TEXT, widal_result32 TEXT, widal_result33 TEXT, widal_result34 TEXT, widal_result35 TEXT, widal_result41 TEXT, widal_result42 TEXT, widal_result43 TEXT,pid TEXT, widal_result44 TEXT, widal_result45 TEXT,widal_test_name2 TEXT,widal_test_name3 TEXT,widal_test_name4 TEXT,widal_test_head1 TEXT,widal_test_head2 TEXT,widal_test_head3 TEXT,widal_test_head4 TEXT,widal_test_head5 TEXT,shop_id TEXT,create_time TEXT,update_time TEXT,widal_interpretation TEXT,widal_precaution TEXT, unique ( pid))";
    public TableWidalTest(Context context) {
        mDbObject = LtSqliteOpenHelper.getInstance(context).getWritableDatabase();
    }
    public boolean isTableEmpty() {
        Cursor mcursor = makeRawQuery("SELECT count(*)AS count FROM " + TABLE_WIDAL_TEST);
        mcursor.moveToFirst();
        return mcursor.getInt(mcursor.getColumnIndex("count")) <= 0;
    }
    public boolean packageSynStatus() {
        Cursor mcursor = makeRawQuery("SELECT count(*)AS count  from " + TABLE_WIDAL_TEST + " where SYN = 0");
        mcursor.moveToFirst();
        return mcursor.getInt(mcursor.getColumnIndex("count")) <= 0;
    }
    public String getReport_id(String pid)
    {
        WidalTest widalTest=null;
        Cursor mcursor = makeRawQuery("SELECT *  from " + TABLE_WIDAL_TEST+ " where pid = '" + pid + "'");
        if (mcursor != null && mcursor.moveToFirst()) {
            do {
                widalTest=addPackageDetails(mcursor);
            } while (mcursor.moveToNext());
        }
        if (widalTest!=null)
        return widalTest.getReport_id();
        else
        return null;
    }

    protected String getSaltString() {
        String SALTCHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
        StringBuilder salt = new StringBuilder();
        Random rnd = new Random();
        while (salt.length() < 8) { // length of the random string.
            int index = (int) (rnd.nextFloat() * SALTCHARS.length());
            salt.append(SALTCHARS.charAt(index));
        }
        return salt.toString();
    }
    private WidalTest addPackageDetails(Cursor mcursor) {
        WidalTest widalTest=new WidalTest();
        Log.e("pid ",mcursor.getString(mcursor.getColumnIndex(PATIENT_ID))+" id");
        widalTest.setPid(mcursor.getString(mcursor.getColumnIndex(PATIENT_ID)));
        widalTest.setWidal_result11(mcursor.getString(mcursor.getColumnIndex(WIDAL_TEST_RESULT11)));
        widalTest.setWidal_result12(mcursor.getString(mcursor.getColumnIndex(WIDAL_TEST_RESULT12)));
        widalTest.setWidal_result13(mcursor.getString(mcursor.getColumnIndex(WIDAL_TEST_RESULT13)));
        widalTest.setWidal_result14(mcursor.getString(mcursor.getColumnIndex(WIDAL_TEST_RESULT14)));
        widalTest.setWidal_result21(mcursor.getString(mcursor.getColumnIndex(WIDAL_TEST_RESULT21)));
        widalTest.setWidal_result22(mcursor.getString(mcursor.getColumnIndex(WIDAL_TEST_RESULT22)));
        widalTest.setWidal_result23(mcursor.getString(mcursor.getColumnIndex(WIDAL_TEST_RESULT23)));
        widalTest.setWidal_result24(mcursor.getString(mcursor.getColumnIndex(WIDAL_TEST_RESULT24)));
        widalTest.setWidal_result31(mcursor.getString(mcursor.getColumnIndex(WIDAL_TEST_RESULT31)));
        widalTest.setWidal_result32(mcursor.getString(mcursor.getColumnIndex(WIDAL_TEST_RESULT32)));
        widalTest.setWidal_result33(mcursor.getString(mcursor.getColumnIndex(WIDAL_TEST_RESULT33)));
        widalTest.setWidal_result34(mcursor.getString(mcursor.getColumnIndex(WIDAL_TEST_RESULT34)));
        widalTest.setWidal_result41(mcursor.getString(mcursor.getColumnIndex(WIDAL_TEST_RESULT41)));
        widalTest.setWidal_result42(mcursor.getString(mcursor.getColumnIndex(WIDAL_TEST_RESULT42)));
        widalTest.setWidal_result43(mcursor.getString(mcursor.getColumnIndex(WIDAL_TEST_RESULT43)));
        widalTest.setWidal_result44(mcursor.getString(mcursor.getColumnIndex(WIDAL_TEST_RESULT44)));
        widalTest.setWidal_test_name1(mcursor.getString(mcursor.getColumnIndex(WIDAL_TEST_NAME1)));
        widalTest.setWidal_test_name2(mcursor.getString(mcursor.getColumnIndex(WIDAL_TEST_NAME2)));
        widalTest.setWidal_test_name3(mcursor.getString(mcursor.getColumnIndex(WIDAL_TEST_NAME3)));
        widalTest.setWidal_test_name4(mcursor.getString(mcursor.getColumnIndex(WIDAL_TEST_NAME4)));
        widalTest.setWidal_test_head1(mcursor.getString(mcursor.getColumnIndex(WIDAL_TEST_HEAD1)));
        widalTest.setWidal_test_head2(mcursor.getString(mcursor.getColumnIndex(WIDAL_TEST_HEAD2)));
        widalTest.setWidal_test_head3(mcursor.getString(mcursor.getColumnIndex(WIDAL_TEST_HEAD3)));
        widalTest.setWidal_test_head4(mcursor.getString(mcursor.getColumnIndex(WIDAL_TEST_HEAD4)));
        widalTest.setWidal_test_head5(mcursor.getString(mcursor.getColumnIndex(WIDAL_TEST_HEAD5)));
        widalTest.setWidal_test_interpretation(mcursor.getString(mcursor.getColumnIndex(WIDAL_INTERPRETATION)));
        widalTest.setWidal_test_precaution(mcursor.getString(mcursor.getColumnIndex(WIDAL_PRECAUTION)));
        widalTest.setWidal_test_created_time(mcursor.getString(mcursor.getColumnIndex(CREATE_TIME)));
        widalTest.setWidal_test_updated_time(mcursor.getString(mcursor.getColumnIndex(UPDATE_TIME)));
        widalTest.setWidal_result15(mcursor.getString(mcursor.getColumnIndex(WIDAL_TEST_RESULT15)));
        widalTest.setWidal_result25(mcursor.getString(mcursor.getColumnIndex(WIDAL_TEST_RESULT25)));
        widalTest.setWidal_result35(mcursor.getString(mcursor.getColumnIndex(WIDAL_TEST_RESULT35)));
        widalTest.setWidal_result45(mcursor.getString(mcursor.getColumnIndex(WIDAL_TEST_RESULT45)));
        widalTest.setReport_id(mcursor.getString(mcursor.getColumnIndex(REPORT_ID)));
        return widalTest;
    }
    private ContentValues getContentValues(WidalTest data) {
        ContentValues values = new ContentValues();
        values.put(CREATE_TIME,data.getWidal_test_created_time());
        values.put(UPDATE_TIME,data.getWidal_test_updated_time());
        values.put(WIDAL_INTERPRETATION,data.getWidal_test_interpretation());
        values.put(WIDAL_PRECAUTION,data.getWidal_test_precaution());
        values.put(SYN_STATUS,"0");
        TablePatientTest tablePatientTest=new TablePatientTest(mContext);
        String report_id=tablePatientTest.getReport_id(data.getPid());
        if (report_id!=null)
            values.put(REPORT_ID, report_id);
        else
            values.put(REPORT_ID, getSaltString());
        values.put(WIDAL_TEST_NAME1,data.getWidal_test_name1());
        values.put(PATIENT_ID,data.getPid());
        values.put(WIDAL_TEST_NAME2,data.getWidal_test_name2());
        values.put(WIDAL_TEST_NAME3, data.getWidal_test_name3());
        values.put(WIDAL_TEST_NAME4,data.getWidal_test_name4());
        values.put(WIDAL_TEST_HEAD1,data.getWidal_test_head1());
        values.put(WIDAL_TEST_HEAD2,data.getWidal_test_head2());
        values.put(WIDAL_TEST_HEAD3,data.getWidal_test_head3());
        values.put(WIDAL_TEST_HEAD4,data.getWidal_test_head4());
        values.put(WIDAL_TEST_HEAD5,data.getWidal_test_head5());
        values.put(WIDAL_TEST_RESULT11,data.getWidal_result11());
        values.put(WIDAL_TEST_RESULT12,data.getWidal_result12());
        values.put(WIDAL_TEST_RESULT13,data.getWidal_result13());
        values.put(WIDAL_TEST_RESULT14,data.getWidal_result14());
        values.put(WIDAL_TEST_RESULT21,data.getWidal_result21());
        values.put(WIDAL_TEST_RESULT22,data.getWidal_result22());
        values.put(WIDAL_TEST_RESULT23,data.getWidal_result23());
        values.put(WIDAL_TEST_RESULT24,data.getWidal_result24());
        values.put(WIDAL_TEST_RESULT31,data.getWidal_result31());
        values.put(WIDAL_TEST_RESULT32,data.getWidal_result32());
        values.put(WIDAL_TEST_RESULT33,data.getWidal_result33());
        values.put(WIDAL_TEST_RESULT34,data.getWidal_result34());
        values.put(WIDAL_TEST_RESULT41,data.getWidal_result41());
        values.put(WIDAL_TEST_RESULT42,data.getWidal_result42());
        values.put(WIDAL_TEST_RESULT43,data.getWidal_result43());
        values.put(WIDAL_TEST_RESULT44,data.getWidal_result44());
        values.put(WIDAL_TEST_RESULT45,data.getWidal_result45());
        values.put(WIDAL_TEST_RESULT35,data.getWidal_result35());
        values.put(WIDAL_TEST_RESULT25,data.getWidal_result25());
        values.put(WIDAL_TEST_RESULT15,data.getWidal_result15());
        return values;
    }

    public void insertPatientWidaltest(WidalTest data) {
        mDbObject.execSQL(TableQcData.create_table_qcData);
        mDbObject.beginTransaction();
        try {
            mDbObject.insertWithOnConflict(TABLE_WIDAL_TEST, null, getContentValues(data)
                    , SQLiteDatabase.CONFLICT_REPLACE);
            mDbObject.setTransactionSuccessful();
            Log.e("insertid widal",data.toString());

        } catch (Exception e) {
            Log.e("not insertid ",e.toString());
            e.printStackTrace();
        } finally {
            mDbObject.endTransaction();
        }
    }

    public WidalTest getWidaltest(String pid) {
        WidalTest widalTest=null;
        Cursor mcursor = makeRawQuery("SELECT *  from " + TABLE_WIDAL_TEST+ " where pid = '" + pid + "'");
        if (mcursor != null && mcursor.moveToFirst()) {
            do {
              widalTest=addPackageDetails(mcursor);
            } while (mcursor.moveToNext());
        }
        return widalTest;
    }
    public ArrayList<WidalTest>
    getwidaltestList(ArrayList<WidalTest> patientList) {
        Cursor mcursor = makeRawQuery("SELECT *  from " + TABLE_WIDAL_TEST+ " where SYN = 0");
        if (mcursor != null && mcursor.moveToFirst()) {
            do {
                patientList.add(addPackageDetails(mcursor));
            } while (mcursor.moveToNext());
        }
        return patientList;
    }
    public void deleteTableContent() {
        mDbObject.delete(TABLE_WIDAL_TEST, null, null);
        //new TablePackageTestDetail(mContext).deleteTableContent();
    }
    public void updateSynStatus(ArrayList<String> list) {
        try {
            for (String s : list) {
                ContentValues values = new ContentValues();
                values.put(SYN_STATUS, "1");
                mDbObject.updateWithOnConflict(TABLE_WIDAL_TEST, values, "Pid = ?",
                        new String[]{s}, SQLiteDatabase.CONFLICT_REPLACE);
                Log.e("the id of test",s);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
