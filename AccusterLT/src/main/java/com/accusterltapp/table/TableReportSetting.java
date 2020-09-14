package com.accusterltapp.table;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.accusterltapp.database.BaseTable;
import com.accusterltapp.database.LtSqliteOpenHelper;
import com.accusterltapp.model.CampDetails1;

import java.util.ArrayList;

public class TableReportSetting   extends BaseTable {
    private Context mContext;
    public static final String TABLE_TEST_PACKAGE = "tbl_reports";
    public static final String CAMP_NAME = "campname";
    public static final String CAMP_Id = "campid";
    public static final String LOGO = "logoname";
    public static final String Header = "header";
    public static final String ORGANIZATION_NAME = "orgname";
    public static final String CAMP_ADDRESS = "patientaddress";

    public static final String SYN_STATUS = "SYN";

    public static final String create_table_reports = "create table if not exists " + TABLE_TEST_PACKAGE + "("
            + CAMP_Id + " text, "
            + CAMP_NAME + " text, "
            + LOGO + " text, "
            + ORGANIZATION_NAME + " text, "
            + Header + " text, "
            + CAMP_ADDRESS + " text);";

    public TableReportSetting(Context context) {
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


    private CampDetails1 addPackageDetails(Cursor mcursor) {

            CampDetails1 d=new CampDetails1();
        d.setOrganization_name(mcursor.getString(mcursor.getColumnIndex(ORGANIZATION_NAME)));
            d.setId(mcursor.getString(mcursor.getColumnIndex(CAMP_Id)));
            d.setName(mcursor.getString(mcursor.getColumnIndex(CAMP_NAME)));
            d.setReport_header(mcursor.getString(mcursor.getColumnIndex(Header)));
        d.setCampLogo(mcursor.getString(mcursor.getColumnIndex(LOGO)));
            d.setCamp_address(mcursor.getString(mcursor.getColumnIndex(CAMP_ADDRESS)));
            return d;
    }

    private ContentValues getContentValues(CampDetails1 accountDetail) {
        ContentValues values = new ContentValues();
        values.put(CAMP_Id, accountDetail.getId());
        values.put(CAMP_NAME, accountDetail.getName());
        values.put(CAMP_ADDRESS, accountDetail.getCamp_address());
        values.put(ORGANIZATION_NAME, accountDetail.getOrganization_name());
        Log.e("camplogo name",accountDetail.getCampLogo());
        values.put(LOGO, accountDetail.getCampLogo());
        values.put(Header, accountDetail.getReport_header());


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

    public void insertSetting(CampDetails1 data) {
        //mDbObject.execSQL(TableQcData.create_table_qcData);
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
    }


    public ArrayList<CampDetails1> getQcdataList(ArrayList<CampDetails1> patientList) {
        Cursor mcursor = makeRawQuery("SELECT *  from " + TABLE_TEST_PACKAGE);
        if (mcursor != null && mcursor.moveToFirst()) {
            do {
                patientList.add(addPackageDetails(mcursor));
            } while (mcursor.moveToNext());
        }
        return patientList;
    }


//    public ArrayList<QcData> getQcListToSyn(ArrayList<QcData> patientList) {
//        Cursor mcursor = makeRawQuery("SELECT *  from " + TABLE_TEST_PACKAGE+ " where SYN = 0");
//        if (mcursor != null && mcursor.moveToFirst()) {
//            do {
//                patientList.add(addPackageDetails(mcursor));
//            } while (mcursor.moveToNext());
//        }
//        return patientList;
//    }

    public void deleteTableContent() {
        mDbObject.delete(TABLE_TEST_PACKAGE, null, null);
        mDbObject.close();
        //new TablePackageTestDetail(mContext).deleteTableContent();
    }
}