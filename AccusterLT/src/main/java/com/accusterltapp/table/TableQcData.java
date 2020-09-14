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

public class TableQcData extends BaseTable {
    private Context mContext;
    public static final String TABLE_TEST_PACKAGE = "tbl_qcdata";
    public static final String TEST_ID = "test_id";
    public static final String C1 = "c1";
    public static final String C2 = "c2";
    public static  final  String C3="c3";
            public static  final String L1="l1";
    public  static  final String L2="l2";
    public  static  final String LtId="labid";
    public  static  final String Date="date";
    public  static  final String Time="time";
    public static  final String STATUS="status";

    public static final String SYN_STATUS = "SYN";

    public static final String create_table_qcData = "create table if not exists " + TABLE_TEST_PACKAGE +"("
            + TEST_ID + " text, "
            +LtId +" text, "
            +Time +" text, "
            +Date +" text, "
            + C1 + " text, "
            + C2 + " text, "
            + C3 + " text, "
            + L1 + " text, "
            + STATUS + " text, "
            + L2 + " text);";

    public TableQcData(Context context) {
       //mContext = context;
        mDbObject = LtSqliteOpenHelper.getInstance(context).getWritableDatabase();

    }

    public boolean isTableEmpty() {
        Cursor mcursor = makeRawQuery("SELECT count(*)AS count FROM " + TABLE_TEST_PACKAGE);
        mcursor.moveToFirst();
        return mcursor.getInt(mcursor.getColumnIndex("count")) <= 0;
    }

    public boolean packageSynStatus() {
        Cursor mcursor = makeRawQuery("SELECT count(*)AS count  from " + TABLE_TEST_PACKAGE + " where SYN = 0");
        mcursor.moveToFirst();
        return mcursor.getInt(mcursor.getColumnIndex("count")) <= 0;
    }


    private QcData addPackageDetails(Cursor mcursor) {
        QcData details = new QcData();
        details.setC1(mcursor.getString(mcursor.getColumnIndex(C1)));
        details.setC2(mcursor.getString(mcursor.getColumnIndex(C2)));
        details.setDate(mcursor.getString(mcursor.getColumnIndex(Date)));
        details.setTime(mcursor.getString(mcursor.getColumnIndex(Time)));
        details.setLab_id(mcursor.getString(mcursor.getColumnIndex(LtId)));
        details.setStatus(mcursor.getString(mcursor.getColumnIndex(STATUS)));

        details.setC3(mcursor.getString(mcursor.getColumnIndex(C3)));

        details.setL1(mcursor.getString(mcursor.getColumnIndex(L1)));

        details.setL2(mcursor.getString(mcursor.getColumnIndex(L2)));
        details.setTest_id(mcursor.getString(mcursor.getColumnIndex(TEST_ID)));
       // details.setLtid(mcursor.getString(mcursor.getColumnIndex(LtId)));
       // details.setC2();
      //  details.setPackage_name(mcursor.getString(mcursor.getColumnIndex(TEST_PROFILE_NAME)));
       // details.setPackage_code(mcursor.getString(mcursor.getColumnIndex(TEST_PACKAGE_CODE)));
       // TablePackageTestDetail subTestDetails = new TablePackageTestDetail(mContext);
       // details.setTest_list(subTestDetails.getAllSubTestList(new ArrayList<SubTestDetails>(),
               // mcursor.getString(mcursor.getColumnIndex(TEST_PROFILE_NAME))));
        return details;
    }

    private ContentValues getContentValues(QcData qcdata) {
        ContentValues values = new ContentValues();
        values.put(TEST_ID,qcdata.getTest_id());
       //values.put(LtId,qcdata.getLtid());
        values.put(LtId,qcdata.getLab_id());
        values.put(Time,qcdata.getTime());
        values.put(Date,qcdata.getDate());
        values.put(C1, qcdata.getC1());
        values.put(C2,qcdata.getC2());
        //Log.e("value of c2",qcdata.getC2());
        values.put(STATUS,qcdata.getStatus());
        values.put(C3,qcdata.getC3());
        values.put(L1,qcdata.getL1());
        values.put(L2,qcdata.getL2());
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
            mDbObject.insertWithOnConflict(TABLE_TEST_PACKAGE, null, getContentValues(data)
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
                values.put(L1, data.getL1());
                values.put(L2, data.getL2());
                mDbObject.updateWithOnConflict(TABLE_TEST_PACKAGE, values, "time = ?",
                        new String[]{Heleprec.qc_time}, SQLiteDatabase.CONFLICT_REPLACE);
                Heleprec.isc3=false;
            }
        }
        else{
            Log.e("insert calls","insert");
            mDbObject.beginTransaction();
            try {
                mDbObject.insertWithOnConflict(TABLE_TEST_PACKAGE, null, getContentValues(data)
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
        Cursor mcursor = makeRawQuery("SELECT *  from " + TABLE_TEST_PACKAGE);
        if (mcursor != null && mcursor.moveToFirst()) {
            do {
                patientList.add(addPackageDetails(mcursor));
            } while (mcursor.moveToNext());
        }
        return patientList;
    }


    public ArrayList<QcData> getQcListToSyn(ArrayList<QcData> patientList) {
        Cursor mcursor = makeRawQuery("SELECT *  from " + TABLE_TEST_PACKAGE+ " where SYN = 0");
        if (mcursor != null && mcursor.moveToFirst()) {
            do {
                patientList.add(addPackageDetails(mcursor));
            } while (mcursor.moveToNext());
        }
        return patientList;
    }

    public void deleteTableContent() {
        mDbObject.delete(TABLE_TEST_PACKAGE, null, null);
        //new TablePackageTestDetail(mContext).deleteTableContent();
    }

}
