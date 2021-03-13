package com.accusterltapp.table;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.accusterltapp.database.AppPreference;
import com.accusterltapp.database.BaseTable;
import com.accusterltapp.database.LtSqliteOpenHelper;
import com.accusterltapp.model.CampDetails1;
import com.base.model.CampDetails;

import java.util.ArrayList;

/**
 * Created by Jyoti on 11/1/2017.
 */

public class TableCamp extends BaseTable {
    public static final String TABLE_CAMP = "tbl_camp";
    public static final String CAMP_ID = "camp_id";
    public static final String CAMP_CODE = "camp_code";
    public static final String ORAGANIZATION_ID = "oraganization_id";
    public static final String ORAGANIZATION_NAME = "oraganization_name";
    public static final String CAMP_NAME = "camp_name";
    public static final String LT_ID = "lt_id";
    public static final String CAMP_START_TIME = "camp_from_time";
    public static final String CAMP_END_TIME = "camp_to_time";
    public static final String CAMP_START_DATE = "camp_from_date";
    public static final String CAMP_END_DATE = "camp_to_date";
    public static final String CAMP_ADDRESS = "campAddress";
    public static final String CAMP_PATHOLOGIST_ID = "camp_pathlogist_Id";
    public static final String DESCRIPTION = "camp_venue";
    public static final String SYN_STATUS = "SYN";
    public static final String CAMP_CREATED_TIME = "camp_created_date_time";
    public static final String CAMP_UPDATED_TIME = "camp_updated_time";
    public static final String CAMP_ACTIVE = "camp_active";
    private Context mContext;

    public static final String create_table_camp = "create table if not exists " + TABLE_CAMP + " ("
            + CAMP_ID + " integer primary key autoincrement, "
            + ORAGANIZATION_ID + " integer, "
            + CAMP_NAME + " text unique, "
            + CAMP_ADDRESS + " text, "
            + CAMP_CODE + " text , "
            + CAMP_START_DATE + " text , "
            + CAMP_END_DATE + " text, "
            + CAMP_START_TIME + " text, "
            + CAMP_END_TIME + " text, "
            + DESCRIPTION + " text, "
            + ORAGANIZATION_NAME + " text, "
            + CAMP_PATHOLOGIST_ID + " text, "
            + CAMP_CREATED_TIME + " text, "
            + CAMP_UPDATED_TIME + " text, "
            + CAMP_ACTIVE + " text DEFAULT 1, "
            + SYN_STATUS + "  text DEFAULT 0, "
            + LT_ID + " text );";

    public TableCamp(Context context) {
        mContext = context;
        mDbObject = LtSqliteOpenHelper.getInstance(context).getReadableDatabase();
    }

    public boolean isTableEmpty() {
        Cursor mcursor = makeRawQuery("SELECT count(*)AS count FROM " + TABLE_CAMP);
        mcursor.moveToFirst();
        Log.e("no of elements ",mcursor.getColumnIndex("count")+"");
        return mcursor.getInt(mcursor.getColumnIndex("count")) <= 0;
    }



    private CampDetails addCampToList(Cursor mcursor) {
        CampDetails details = new CampDetails();
        details.setCampID(mcursor.getString(mcursor.getColumnIndex(CAMP_ID)));
        details.setCampName(mcursor.getString(mcursor.getColumnIndex(CAMP_NAME)));
        details.setAddress(mcursor.getString(mcursor.getColumnIndex(CAMP_ADDRESS)));
        details.setStartDate(mcursor.getString(mcursor.getColumnIndex(CAMP_START_DATE)));
        details.setEndDate(mcursor.getString(mcursor.getColumnIndex(CAMP_END_DATE)));
        details.setStartTime(mcursor.getString(mcursor.getColumnIndex(CAMP_START_TIME)));
        details.setEndTime(mcursor.getString(mcursor.getColumnIndex(CAMP_END_TIME)));
        details.setCamp_description(mcursor.getString(mcursor.getColumnIndex(DESCRIPTION)));
        details.setCamp_created_user_id(mcursor.getString(mcursor.getColumnIndex(LT_ID)));
        details.setCamp_sync_status(mcursor.getString(mcursor.getColumnIndex(SYN_STATUS)));
        details.setCamp_created_date_time(mcursor.getString(mcursor.getColumnIndex(CAMP_CREATED_TIME)));
        details.setCamp_pathlogist_Id(mcursor.getString(mcursor.getColumnIndex(CAMP_PATHOLOGIST_ID)));
        details.setCamp_code(mcursor.getString(mcursor.getColumnIndex(CAMP_CODE)));
        details.setCamp_organization_id(mcursor.getString(mcursor.getColumnIndex(ORAGANIZATION_ID)));
        return details;
    }
    private CampDetails addCampToListForZeroPos() {
        CampDetails details = new CampDetails();
        details.setCampName("Select Camp");
        return details;
    }
    private ContentValues getContentValues(CampDetails campDetails) {
        ContentValues values = new ContentValues();
        values.put(CAMP_NAME, campDetails.getCampName());
        values.put(CAMP_ADDRESS, campDetails.getAddress());
        values.put(CAMP_START_DATE, campDetails.getStartDate());
        values.put(CAMP_END_DATE, campDetails.getEndDate());
        values.put(CAMP_START_TIME, campDetails.getStartTime());
        values.put(CAMP_END_TIME, campDetails.getEndTime());
        values.put(LT_ID, AppPreference.getString(mContext, AppPreference.USER_ID));
        values.put(CAMP_CODE, campDetails.getCamp_code());
        values.put(CAMP_PATHOLOGIST_ID, campDetails.getCamp_pathlogist_Id());
        values.put(CAMP_CREATED_TIME, campDetails.getCamp_created_date_time());
        values.put(DESCRIPTION, campDetails.getCamp_description());
        values.put(ORAGANIZATION_ID, campDetails.getCamp_organization_id());
        return values;
    }

    private ContentValues getContentValuesFromServer(CampDetails campDetails) {
        ContentValues values = new ContentValues();
        values.put(CAMP_NAME, campDetails.getCampName());
        values.put(CAMP_ADDRESS, campDetails.getAddress());
        values.put(CAMP_START_DATE, campDetails.getStartDate());
        values.put(CAMP_END_DATE, campDetails.getEndDate());
        values.put(CAMP_START_TIME, campDetails.getStartTime());
        values.put(CAMP_END_TIME, campDetails.getEndTime());
        values.put(LT_ID, campDetails.getCamp_created_user_id());
        values.put(CAMP_CODE, campDetails.getCamp_code());
        values.put(CAMP_PATHOLOGIST_ID, campDetails.getCamp_pathlogist_Id());
        values.put(CAMP_CREATED_TIME, campDetails.getCamp_created_date_time());
        values.put(DESCRIPTION, campDetails.getCamp_description());
        values.put(ORAGANIZATION_ID, campDetails.getCamp_organization_id());
        values.put(SYN_STATUS, "1");
        return values;
    }

    private ContentValues getContentValuesFromServer1(CampDetails1 campDetails) {
        ContentValues values = new ContentValues();
        values.put(CAMP_NAME, campDetails.getName());
        values.put(CAMP_ADDRESS, campDetails.getOrganization_name());
        values.put(CAMP_START_DATE, campDetails.getStartdate());
        values.put(CAMP_END_DATE, campDetails.getEnddate());
        values.put(CAMP_START_TIME, campDetails.getStarttime());
       values.put(CAMP_CODE, campDetails.getCamp_code());
        values.put(CAMP_END_TIME, campDetails.getEndtime());
        values.put(LT_ID, campDetails.getCamp_created_user_id());
        values.put(ORAGANIZATION_ID, campDetails.getCamp_organization_id());
        values.put(SYN_STATUS, "1");
        return values;
    }

    public void insertCampList(ArrayList<CampDetails> accountData) {
        mDbObject.beginTransaction();
        try {
            for (CampDetails testData : accountData) {
                mDbObject.insertWithOnConflict(TABLE_CAMP, null, getContentValues(testData)
                        , SQLiteDatabase.CONFLICT_REPLACE);
            }
            mDbObject.setTransactionSuccessful();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            mDbObject.endTransaction();
        }

    }

    public void insertCampListFormServer(ArrayList<CampDetails> accountData) {
        mDbObject.beginTransaction();
        try {
            for (CampDetails testData : accountData) {
                mDbObject.insertWithOnConflict(TABLE_CAMP, null, getContentValuesFromServer(testData)
                        , SQLiteDatabase.CONFLICT_REPLACE);
            }
            mDbObject.setTransactionSuccessful();
            Log.e("data insertid","data");
        } catch (Exception e) {
            Log.e("data not insertid ","not");
            e.printStackTrace();
        } finally {
            mDbObject.endTransaction();
        }
    }

    public void insertCampListFormServer1(ArrayList<CampDetails1> accountData) {
        mDbObject.beginTransaction();
        try {
            for (CampDetails1 testData : accountData) {
                mDbObject.insertWithOnConflict(TABLE_CAMP, null, getContentValuesFromServer1(testData)
                        , SQLiteDatabase.CONFLICT_REPLACE);
            }
            mDbObject.setTransactionSuccessful();
            Log.e("data insertid","data");
        } catch (Exception e) {
            Log.e("data not insertid ","not");
           // e.printStackTrace();
        } finally {
            mDbObject.endTransaction();
        }
    }
    public void insertCamp(CampDetails campDetails) {
        mDbObject.beginTransaction();
        try {
            mDbObject.insertWithOnConflict(TABLE_CAMP, null, getContentValues(campDetails)
                    , SQLiteDatabase.CONFLICT_REPLACE);
            mDbObject.setTransactionSuccessful();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            mDbObject.endTransaction();
        }
    }


    public ArrayList<CampDetails> getCampList(ArrayList<CampDetails> patientList) {
        Cursor mcursor = makeRawQuery("SELECT *  from " + TABLE_CAMP);
        if (mcursor != null && mcursor.moveToFirst()) {
            do {

                patientList.add(addCampToList(mcursor));
            } while (mcursor.moveToNext());
        }
        return patientList;
    }

    public ArrayList<CampDetails> getCampListWithSelectCampTitle(ArrayList<CampDetails> patientList) {
        Cursor mcursor = makeRawQuery("SELECT *  from " + TABLE_CAMP);
        patientList.add(0,addCampToListForZeroPos());
        if (mcursor != null && mcursor.moveToFirst()) {
            do {
                patientList.add(addCampToList(mcursor));
            } while (mcursor.moveToNext());
        }
        return patientList;
    }
/*

    public ArrayList<CampDetails1> getCampListForCampDetails1(ArrayList<CampDetails1> patientList1) {
        Cursor mcursor = makeRawQuery("SELECT *  from " + TABLE_CAMP);
        if (mcursor != null && mcursor.moveToFirst()) {
            do {
                patientList1.add(addCampToList(mcursor));
            } while (mcursor.moveToNext());
        }
        return patientList1;
    }
*/

    public ArrayList<CampDetails> getCampListToSyn(ArrayList<CampDetails> patientList) {
        Cursor mcursor = makeRawQuery("SELECT *  from " + TABLE_CAMP + " where " + SYN_STATUS + " = 0");
        if (mcursor != null && mcursor.moveToFirst()) {
            do {
                patientList.add(addCampToList(mcursor));
            } while (mcursor.moveToNext());
        }
        return patientList;
    }

    public boolean getSynStatus() {
        Cursor mcursor = makeRawQuery("SELECT count(*)AS count  from " + TABLE_CAMP + " where " + SYN_STATUS + " = 0");
        mcursor.moveToFirst();
        return mcursor.getInt(mcursor.getColumnIndex("count")) <= 0;
    }

    public void deleteTableContent() {
        mDbObject.delete(TABLE_CAMP, null, null);
    }

    public void updateSynStatus(String[] list) {
        try {
            for (String s : list) {
                ContentValues values = new ContentValues();
                values.put(SYN_STATUS, "1");
                mDbObject.updateWithOnConflict(TABLE_CAMP, values, "camp_code = ?", new String[]{s}, SQLiteDatabase.CONFLICT_REPLACE);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
