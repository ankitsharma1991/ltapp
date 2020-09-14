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

import java.util.ArrayList;

public class PatientWidalTest extends BaseTable {
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
    public static  final  String CREATE_WIDAL_TEST_TABLE=" create table if not exists tbl_widaltest (test_id INTEGER PRIMARY KEY AUTOINCREMENT,SYN  TEXT DEFAULT 0 ,widal_test_name1 TEXT,widal_result11 TEXT,widal_result12 TEXT, widal_result13 TEXT, widal_result14 TEXT, widal_result21 TEXT, widal_result22 TEXT, widal_result23 TEXT, widal_result24 TEXT, widal_result31 TEXT, widal_result32 TEXT, widal_result33 TEXT, widal_result34 TEXT, widal_result41 TEXT, widal_result42 TEXT, widal_result43 TEXT,pid TEXT, widal_result44 TEXT,widal_test_name2 TEXT,widal_test_name3 TEXT,widal_test_name4 TEXT,widal_test_head1 TEXT,widal_test_head2 TEXT,widal_test_head3 TEXT,widal_test_head4 TEXT,shop_id TEXT, unique ( pid))";
    public PatientWidalTest(Context context) {
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
    private QcData addPackageDetails(Cursor mcursor) {
//        QcData details = new QcData();
//        details.setC1(mcursor.getString(mcursor.getColumnIndex(C1)));
//        details.setC2(mcursor.getString(mcursor.getColumnIndex(C2)));
//        details.setDate(mcursor.getString(mcursor.getColumnIndex(Date)));
//        details.setTime(mcursor.getString(mcursor.getColumnIndex(Time)));
//        details.setLab_id(mcursor.getString(mcursor.getColumnIndex(LtId)));
//        details.setStatus(mcursor.getString(mcursor.getColumnIndex(STATUS)));
//        details.setC3(mcursor.getString(mcursor.getColumnIndex(C3)));
//        details.setL1(mcursor.getString(mcursor.getColumnIndex(L1)));
//        details.setL2(mcursor.getString(mcursor.getColumnIndex(L2)));
//        details.setTest_id(mcursor.getString(mcursor.getColumnIndex(TEST_ID)));
        // details.setLtid(mcursor.getString(mcursor.getColumnIndex(LtId)));
        // details.setC2();
        //  details.setPackage_name(mcursor.getString(mcursor.getColumnIndex(TEST_PROFILE_NAME)));
        // details.setPackage_code(mcursor.getString(mcursor.getColumnIndex(TEST_PACKAGE_CODE)));
        // TablePackageTestDetail subTestDetails = new TablePackageTestDetail(mContext);
        // details.setTest_list(subTestDetails.getAllSubTestList(new ArrayList<SubTestDetails>(),
        // mcursor.getString(mcursor.getColumnIndex(TEST_PROFILE_NAME))));
        return null;
    }
    private ContentValues getContentValues(QcData qcdata) {
        ContentValues values = new ContentValues();
//        values.put(TEST_ID,qcdata.getTest_id());
//        //values.put(LtId,qcdata.getLtid());
//        values.put(LtId,qcdata.getLab_id());
//        values.put(Time,qcdata.getTime());
//        values.put(Date,qcdata.getDate());
//        values.put(C1, qcdata.getC1());
//        values.put(C2,qcdata.getC2());
//        //Log.e("value of c2",qcdata.getC2());
//        values.put(STATUS,qcdata.getStatus());
//        values.put(C3,qcdata.getC3());
//        values.put(L1,qcdata.getL1());
//        values.put(L2,qcdata.getL2());
        return values;
    }
    //    public void insertPackageList(QcData accountData) {
//        mDbObject.beginTransaction();
//        try {
//            for (TestDetails testData : accountData) {
//                mDbObject.insertWithOnConflict(TABLE_TEST_PACKAGE, null, getContentValues()
//                        , SQLiteDatabase.CONFLICT_REPLACE);
//            }
//            mDbObject.setTransactionSuccessful();
//        } catch (Exception e) {
//            e.printStackTrace();
//        } finally {
//            mDbObject.endTransaction();
//        }
//    }
    public void insertQcData(QcData data) {
        mDbObject.execSQL(TableQcData.create_table_qcData);
        mDbObject.beginTransaction();
        try {
            mDbObject.insertWithOnConflict(TABLE_WIDAL_TEST, null, getContentValues(data)
                    , SQLiteDatabase.CONFLICT_REPLACE);
            mDbObject.setTransactionSuccessful();
            Log.e("insertid ",data.toString());
            Heleprec.qc_time=data.getTime();
        } catch (Exception e) {
            Log.e("not insertid ",e.toString());
            e.printStackTrace();
        } finally {
            mDbObject.endTransaction();
        }
    }
    public void insertQcDataofL(QcData data) {
        mDbObject.execSQL(TableQcData.create_table_qcData);
        if (Heleprec.isc3)
        {
            Log.e("update calls","update");
            if (Heleprec.qc_time!=null) {
                ContentValues values = new ContentValues();
//                values.put(L1, data.getL1());
//                values.put(L2, data.getL2());
                mDbObject.updateWithOnConflict(TABLE_WIDAL_TEST, values, "time = ?",
                        new String[]{Heleprec.qc_time}, SQLiteDatabase.CONFLICT_REPLACE);
                Heleprec.isc3=false;
            }
        }
        else{
            Log.e("insert calls","insert");
            mDbObject.beginTransaction();
            try {
                mDbObject.insertWithOnConflict(TABLE_WIDAL_TEST, null, getContentValues(data)
                        , SQLiteDatabase.CONFLICT_REPLACE);
                mDbObject.setTransactionSuccessful();
                Log.e("insertid ", data.toString());
            } catch (Exception e) {
                Log.e("not insertid ", e.toString());
                e.printStackTrace();
            } finally {

                mDbObject.endTransaction();
            }
            Heleprec.isc3=false;
        }
    }
    public ArrayList<QcData> getQcdataList(ArrayList<QcData> patientList) {
        Cursor mcursor = makeRawQuery("SELECT *  from " + TABLE_WIDAL_TEST);
        if (mcursor != null && mcursor.moveToFirst()) {
            do {
                patientList.add(addPackageDetails(mcursor));
            } while (mcursor.moveToNext());
        }
        return patientList;
    }
    public ArrayList<QcData> getQcListToSyn(ArrayList<QcData> patientList) {
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
}
