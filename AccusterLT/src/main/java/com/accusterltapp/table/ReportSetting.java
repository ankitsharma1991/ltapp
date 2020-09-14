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

public class ReportSetting extends BaseTable { public static final String TABLE_REPORTSETTING = "tbreports";
    public static final String ID = "id";
    public static final String CAMP_NAME = "campname";
    public static final String CAMP_Id = "campid";
    public static final String LOGO = "logoname";
    public static final String Header = "header";
    public static final String CAMP_ADDRESS = "patientaddress";



    public static final String create_table_report = "create table if not exists " + TABLE_REPORTSETTING +"("
            + CAMP_Id + " text, "
            +CAMP_NAME +" text, "
            +CAMP_ADDRESS +" text, "
            +LOGO +" text, "
            + Header + " text);";

    private ContentValues getContentValues(CampDetails1 accountDetail) {
        ContentValues values = new ContentValues();
        values.put(CAMP_Id,accountDetail.getId());
        values.put(CAMP_NAME, accountDetail.getName());
        values.put(CAMP_ADDRESS, accountDetail.getCamp_address());
        values.put(LOGO, accountDetail.getCampLogo());
        values.put(Header,accountDetail.getReport_header());


        return values;
    }

    public ReportSetting(Context context) {
   //     mContext = context;
        mDbObject = LtSqliteOpenHelper.getInstance(context).getDatabase();

        Log.e("databse is "+mDbObject,"hi");
    }

    public boolean isTableEmpty() {
        Cursor mcursor = makeRawQuery("SELECT count(*)AS count FROM " + TABLE_REPORTSETTING);
        mcursor.moveToFirst();
        return mcursor.getInt(mcursor.getColumnIndex("count")) <= 0;
    }



    public int getTotalEntry() {
        Cursor mcursor = makeRawQuery("SELECT count(*)AS count FROM " + TABLE_REPORTSETTING);
        mcursor.moveToFirst();
        return mcursor.getInt(mcursor.getColumnIndex("count"));
    }

    public void insertSingleSetting(CampDetails1 accountData) {
        mDbObject.insertWithOnConflict(TABLE_REPORTSETTING, null, getContentValues(accountData), SQLiteDatabase.CONFLICT_REPLACE);
    }

    public void upadteSingleSetting(CampDetails1 accountData) {
        mDbObject.updateWithOnConflict(TABLE_REPORTSETTING, getContentValues(accountData),"campname=?",
                new String[]{accountData.getName()}, SQLiteDatabase.CONFLICT_REPLACE);
    }

    public ArrayList<CampDetails1> getallPatient(ArrayList<CampDetails1> patientList) {
        Cursor mcursor = makeRawQuery("SELECT *  from " + TABLE_REPORTSETTING + " ORDER BY id DESC");
        if (mcursor != null && mcursor.moveToFirst()) {
            do {
                patientList.add(addPatientToList(mcursor));
            } while (mcursor.moveToNext());
        }
        return patientList;
    }



    public ArrayList<CampDetails1> getallPatientToSync(ArrayList<CampDetails1> patientList) {
        Cursor mcursor = makeRawQuery("SELECT *  from " + TABLE_REPORTSETTING + " where SYN = 0");
        if (mcursor != null && mcursor.moveToFirst()) {
            do {
                patientList.add(addPatientToList(mcursor));
            } while (mcursor.moveToNext());
        }
        return patientList;
    }



    private CampDetails1 addPatientToList(Cursor mcursor) {
        CampDetails1 d=new CampDetails1();
        d.setId(mcursor.getString(mcursor.getColumnIndex(CAMP_Id)));
        d.setName(mcursor.getString(mcursor.getColumnIndex(CAMP_NAME)));
        d.setReport_header(mcursor.getString(mcursor.getColumnIndex(Header)));
        d.setCamp_address(mcursor.getString(mcursor.getColumnIndex(CAMP_ADDRESS)));
       return d;
    }
    public void deleteTableContent() {
        mDbObject.delete(TABLE_REPORTSETTING, null, null);
        //new TablePackageTestDetail(mContext).deleteTableContent();
    }



}
