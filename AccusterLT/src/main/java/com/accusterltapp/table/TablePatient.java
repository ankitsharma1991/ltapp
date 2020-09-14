package com.accusterltapp.table;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.accusterltapp.database.BaseTable;
import com.accusterltapp.database.LtSqliteOpenHelper;
import com.accusterltapp.model.RegisterPatient;

import java.util.ArrayList;

/**
 * Created by Jyoti on 11/1/2017.
 */

public class TablePatient extends BaseTable {
    public static final String TABLE_PATIENT = "tbl_patient";
    public static final String PATIENT_ID = "patient_id";
    public static final String ID = "id";
    public  static  final  String  BP="bp";
    public static  final String BMI="bmi";
    public static final String PATIENT_Name = "patientName";
    public static final String PATIENT_GENDER = "patientGender";
    public static final String PATIENT_ID_TYPE = "patientIdType";
    public static final String PATIENT_ID_NUMBER = "patientIdNumber";
    public static final String PATIENT_CONTACT = "patientContact";
    public static final String PATIENT_EMAIL = "patientEmail";
    public static final String PATIENT_ADDRESS = "patientAddress";
    public static final String PATIENT_MEDICAL_HISTORY = "patientMedicalHistory";
    public static final String PATIENT_MEDICAL_HISTORY_DEATILS = "histroy_detail";
    public static final String PATIENT_DIET = "patientDiet";
    public static final String PATIENT_AGE = "patientAge";
    public static final String PATIENT_REGISTERDATE = "patientRegisterDate";
    public static final String CREATE_DATE_TIME = "date_time";
    public static final String CAMP_CODE = "camp_code";
    public static final String CAMP_NAME = "camp_name";
    public static final String SYN_STATUS = "SYN";
    public static final String ORG_ID = "org_id";
    public static final String LT_ID = "lt_id";

    public static final String create_table_patient = "create table if not exists " + TABLE_PATIENT + " ("
            + ID + " integer primary key autoincrement, "
            + PATIENT_ID + " text not null unique, "
            + PATIENT_REGISTERDATE + " text, "
            + PATIENT_CONTACT + " text, "
            + PATIENT_Name + " text, "
            + PATIENT_AGE + " text, "
            + PATIENT_GENDER + " text, "
            + BP + " text, "
            + BMI + " text, "
            + PATIENT_ID_TYPE + " text, "
            + CREATE_DATE_TIME + " text, "
            + PATIENT_MEDICAL_HISTORY_DEATILS + " text, "
            + ORG_ID + " text, "
            + PATIENT_ID_NUMBER + " text, "
            + PATIENT_DIET + " text, "
            + LT_ID + " text, "
            + SYN_STATUS + " text DEFAULT 0 , "
            + CAMP_CODE + " text, "
            + CAMP_NAME + " text, "
            + PATIENT_EMAIL + " text, "
            + PATIENT_ADDRESS + " text, "
            + PATIENT_MEDICAL_HISTORY + " text);";
    private ContentValues getContentValues(RegisterPatient accountDetail) {
        ContentValues values = new ContentValues();
        values.put(BMI,accountDetail.getUserregistration_bmi());
        values.put(BP,accountDetail.getUserregistration_bp());
        values.put(PATIENT_ID, accountDetail.getUserregistration_code());
        values.put(PATIENT_Name, accountDetail.getUserregistration_complete_name());
        values.put(PATIENT_REGISTERDATE, accountDetail.getDate());
        values.put(PATIENT_CONTACT, accountDetail.getUserregistration_mobile_number());
        values.put(PATIENT_AGE, accountDetail.getUserregistration_age());
        values.put(PATIENT_GENDER, accountDetail.getUserregistration_gender_id());
        values.put(PATIENT_ID_TYPE, accountDetail.getUserregistration_Id_type());
        values.put(PATIENT_ID_NUMBER, accountDetail.getUserregistration_Id_no());
        values.put(PATIENT_DIET, accountDetail.getUserregistration_diet());
        values.put(PATIENT_EMAIL, accountDetail.getUserregistration_email_address());
        values.put(PATIENT_ADDRESS, accountDetail.getUserregistration_address_line_1());
        values.put(PATIENT_MEDICAL_HISTORY, accountDetail.getUserregistration_history_type());
        values.put(LT_ID, accountDetail.getUserregistration_Lt_id());
        values.put(CAMP_CODE, accountDetail.getUserregistration_camp_code());
        values.put(CAMP_NAME, accountDetail.getCampName());
        values.put(CREATE_DATE_TIME, accountDetail.getUserregistration_created_time());
        values.put(ORG_ID, accountDetail.getUserregistration_org_id());
        values.put(PATIENT_MEDICAL_HISTORY_DEATILS, accountDetail.getUserregistration_history_type_detail());
        return values;
    }

    public TablePatient(Context context) {
        mDbObject = LtSqliteOpenHelper.getInstance(context).getReadableDatabase();
    }

    public boolean isTableEmpty() {
        Cursor mcursor = makeRawQuery("SELECT count(*)AS count FROM " + TABLE_PATIENT);
        mcursor.moveToFirst();
        return mcursor.getInt(mcursor.getColumnIndex("count")) <= 0;
    }

    public boolean getallPatientSynStatus() {
        Cursor mcursor = makeRawQuery("SELECT count(*)AS count  from " + TABLE_PATIENT + " where SYN = 0");
        mcursor.moveToFirst();
        return mcursor.getInt(mcursor.getColumnIndex("count")) <= 0;
    }

    public int getTotalEntry() {
        Cursor mcursor = makeRawQuery("SELECT count(*)AS count FROM " + TABLE_PATIENT);
        mcursor.moveToFirst();
        return mcursor.getInt(mcursor.getColumnIndex("count"));
    }

    public void insertSinglePatient(RegisterPatient accountData) {
        mDbObject.insertWithOnConflict(TABLE_PATIENT, null, getContentValues(accountData), SQLiteDatabase.CONFLICT_REPLACE);
    }

    public void upadteSinglePatient(RegisterPatient accountData) {
        mDbObject.updateWithOnConflict(TABLE_PATIENT, getContentValues(accountData),"patient_id=?",
                new String[]{accountData.getUserregistration_code()}, SQLiteDatabase.CONFLICT_REPLACE);
    }

    public ArrayList<RegisterPatient> getallPatient(ArrayList<RegisterPatient> patientList) {
        Cursor mcursor = makeRawQuery("SELECT *  from " + TABLE_PATIENT + " ORDER BY id DESC");
        if (mcursor != null && mcursor.moveToFirst()) {
            do {
                patientList.add(addPatientToList(mcursor));
            } while (mcursor.moveToNext());
        }
        return patientList;
    }


    public ArrayList<RegisterPatient> getallPatientByCamp(ArrayList<RegisterPatient> patientList, String campCode) {
        Cursor mcursor = makeRawQuery("SELECT *  from " + TABLE_PATIENT + " where camp_code = '" + campCode + "' ORDER BY id DESC");
        if (mcursor != null && mcursor.moveToFirst()) {
            do {
                patientList.add(addPatientToList(mcursor));
            } while (mcursor.moveToNext());
        }
        return patientList;
    }


    public ArrayList<RegisterPatient> getallPatientToSync(ArrayList<RegisterPatient> patientList) {
        Cursor mcursor = makeRawQuery("SELECT *  from " + TABLE_PATIENT + " where SYN = 0");
        if (mcursor != null && mcursor.moveToFirst()) {
            do {
                patientList.add(addPatientToList(mcursor));
            } while (mcursor.moveToNext());
        }
        return patientList;
    }

    public ArrayList<RegisterPatient> getallPatientAndReport(ArrayList<RegisterPatient> patientList) {
        Cursor mcursor = makeRawQuery("SELECT *  from tbl_patient AS TP  left JOIN patient_test \n" +
                "ON TP.patient_id=patient_test.pid where patient_test.SYN = 0 or TP.SYN = 0 group by TP.patient_id");
        if (mcursor != null && mcursor.moveToFirst()) {
            do {
                patientList.add(addPatientToList(mcursor));
            } while (mcursor.moveToNext());
        }
        return patientList;
    }

    public String getLabelId(String id) {
        Cursor mcursor = makeRawQuery("SELECT *  from " + TABLE_PATIENT + " where patient_id = '" + id + "'");
        if (mcursor != null && mcursor.moveToFirst()) {
            return mcursor.getString(mcursor.getColumnIndex(ID));
        }
        return "";
    }

    private RegisterPatient addPatientToList(Cursor mcursor) {
        RegisterPatient patient = new RegisterPatient();
        patient .setUserregistration_bmi(mcursor.getString(mcursor.getColumnIndex(BMI)));
        patient.setUserregistration_bp(mcursor.getString(mcursor.getColumnIndex(BP)));
        patient.setUserregistration_email_address(mcursor.getString(mcursor.getColumnIndex(PATIENT_EMAIL)));
        patient.setUserregistration_complete_name(mcursor.getString(mcursor.getColumnIndex(PATIENT_Name)));
        patient.setUserregistration_code(mcursor.getString(mcursor.getColumnIndex(PATIENT_ID)));
        patient.setUserregistration_mobile_number(mcursor.getString(mcursor.getColumnIndex(PATIENT_CONTACT)));
        patient.setUserregistration_age(mcursor.getString(mcursor.getColumnIndex(PATIENT_AGE)));
        patient.setUserregistration_gender_id(mcursor.getString(mcursor.getColumnIndex(PATIENT_GENDER)));
        patient.setUserregistration_Id_type(mcursor.getString(mcursor.getColumnIndex(PATIENT_ID_TYPE)));
        patient.setUserregistration_Id_no(mcursor.getString(mcursor.getColumnIndex(PATIENT_ID_NUMBER)));
        patient.setUserregistration_address_line_1(mcursor.getString(mcursor.getColumnIndex(PATIENT_ADDRESS)));
        patient.setUserregistration_diet(mcursor.getString(mcursor.getColumnIndex(PATIENT_DIET)));
        patient.setpLabelId(mcursor.getString(mcursor.getColumnIndex(ID)));
        patient.setDate(mcursor.getString(mcursor.getColumnIndex(PATIENT_REGISTERDATE)));
        patient.setUserregistration_history_type(mcursor.getString(mcursor.getColumnIndex(PATIENT_MEDICAL_HISTORY)));
        patient.setUserregistration_Lt_id(mcursor.getString(mcursor.getColumnIndex(LT_ID)));
        patient.setUserregistration_camp_code(mcursor.getString(mcursor.getColumnIndex(CAMP_CODE)));
        patient.setCampName(mcursor.getString(mcursor.getColumnIndex(CAMP_NAME)));
        patient.setUserregistration_sync_id(mcursor.getString(mcursor.getColumnIndex(SYN_STATUS)));
        patient.setUserregistration_org_id(mcursor.getString(mcursor.getColumnIndex(ORG_ID)));
        patient.setUserregistration_created_time(mcursor.getString(mcursor.getColumnIndex(CREATE_DATE_TIME)));
        patient.setUserregistration_history_type_detail(mcursor.getString(mcursor.getColumnIndex(PATIENT_MEDICAL_HISTORY_DEATILS)));
        return patient;
    }

    public void updateSynStatus(String[] list) {
        try {
            for (String s : list) {
                ContentValues values = new ContentValues();
                values.put(SYN_STATUS, "1");
                mDbObject.updateWithOnConflict(TABLE_PATIENT, values, "patient_id = ?",
                        new String[]{s}, SQLiteDatabase.CONFLICT_REPLACE);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void deleteTableContent() {
        mDbObject.delete(TABLE_PATIENT, null, null);
        mDbObject.execSQL("delete from sqlite_sequence where name='"+TABLE_PATIENT+"'");
    }

}
