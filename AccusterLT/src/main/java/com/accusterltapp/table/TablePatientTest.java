package com.accusterltapp.table;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.text.TextUtils;
import android.util.Log;

import com.accusterltapp.database.AppPreference;
import com.accusterltapp.database.BaseTable;
import com.accusterltapp.database.LtSqliteOpenHelper;
import com.accusterltapp.model.SubTestDetails;
import com.accusterltapp.model.TesTDetailsToSyn;
import com.accusterltapp.model.WidalTest;
import com.base.utility.DateTimeUtils;

import java.util.ArrayList;
import java.util.Random;


/**
 * Created by pbadmin on 3/11/17.
 */

@SuppressWarnings("ALL")
public class TablePatientTest extends BaseTable {
    private Context mContext;
    private static final String COLUMN_ID = "_id";
    public static final String TABLE_PATIENT_TEST = "patient_test";
    public static final String TEST_PATIENT_ID = "pid";
    public static final String TEST_NAME = "test_name";
    public static final String TEST_CODE = "test_code";
    public static final String TEST_RESULT = "test_result";
    public static final String TEST_COST = "test_cost";
    public static final String MANUAL_RESULT = "manual_result";
    public static final String RAPID_TEST_IMAGE = "result_image";
    public static final String PACKAGE_NAME = "package_name";
    public static final String TEST_UNIT = "test_unit";
    public static final String LT_ID = "lt_id";
    public static final String SYN_STATUS = "SYN";
    public static final String MALE_UPPER_BOUND = "m_upper";
    public static final String FEMALE_UPPER_BOUND = "f_upper";
    public static final String MALE_LOWER_BOUND = "m_lower";
    public static final String FEMALE_LOWER_BOUND = "f_lower";
    public static final String TEST_ID = "t_id";
    public static final String TEST_CREATE_TIME = "date_time";
    public static final String CAMP_CODE = "camp_code";
    public static final String REPORT_ID = "report_id";
    public static final String LABEL_ID = "label_id";
    public static final String TEST_PROFILE_NAME = "test_profile_name";
    public static final String create_table_patient_test = "create table if not exists " + TABLE_PATIENT_TEST + " ("
            + COLUMN_ID + " integer primary key autoincrement, "
            + TEST_NAME + " text, "
            + TEST_PATIENT_ID + " text not null , "
            + TEST_CODE + " text  , "
            + TEST_RESULT + " text, "
            + PACKAGE_NAME + " text, "
            + REPORT_ID + " text, "
            + CAMP_CODE + " text, "
            + TEST_UNIT + " text, "
            + LABEL_ID + " text, "
            + TEST_ID + " text, "
            + TEST_CREATE_TIME + " text, "
            + MALE_UPPER_BOUND + " text , "
            + FEMALE_UPPER_BOUND + " text , "
            + MALE_LOWER_BOUND + " text , "
            + FEMALE_LOWER_BOUND + " text , "
            + LT_ID + " text, "
            +"test_interpretation" + " text , "
            +"test_precautions"+" text , "
            +"image_permission"+" text , "
            + TEST_PROFILE_NAME + " text , "
            + SYN_STATUS + " text DEFAULT 0 , "
            + RAPID_TEST_IMAGE + " text, "
            + MANUAL_RESULT + " INTEGER DEFAULT 1, "
            + TEST_COST + " text , unique ( pid,test_name) );";
    public TablePatientTest(Context context) {
        mContext = context;
        mDbObject = LtSqliteOpenHelper.getInstance(context).getReadableDatabase();
    }
    public boolean isTableEmpty() {
        Cursor mcursor = makeRawQuery("SELECT count(*)AS count FROM " + TABLE_PATIENT_TEST);
        mcursor.moveToFirst();
        return mcursor.getInt(mcursor.getColumnIndex("count")) <= 0;
    }
    public boolean getReportStatus(String patientId) {
        try {
            Cursor mcursor = makeRawQuery("SELECT sum(test_cost) as total from " + TABLE_PATIENT_TEST + " where pid = " + patientId + " and " +
                    TEST_RESULT + " is null");
            if (mcursor != null && mcursor.moveToFirst())
                return mcursor.getInt(mcursor.getColumnIndex("count")) <= 0;
            else
                return false;
        } catch (Exception e) {
return false;
        }
    }
    public boolean getPatientTestSynStatus() {
        Cursor mcursor = makeRawQuery("SELECT count(*)AS count  from " + TABLE_PATIENT_TEST + " where SYN = 0 ");
        mcursor.moveToFirst();
        return mcursor.getInt(mcursor.getColumnIndex("count")) <= 0;
    }
    public int getTotalEntry() {
        Cursor mcursor = makeRawQuery("SELECT count(*)AS count FROM " + TABLE_PATIENT_TEST);
        mcursor.moveToFirst();
        return mcursor.getInt(mcursor.getColumnIndex("count"));
    }
    public void insertSinglePatient(ArrayList<SubTestDetails> accountData, String patientId, String campCode, String labelId) {
        mDbObject.beginTransaction();
        try {
            for (SubTestDetails testData : accountData) {
                mDbObject.insertWithOnConflict(TABLE_PATIENT_TEST, null, getContentValues(testData, patientId, campCode, labelId)
                        , SQLiteDatabase.CONFLICT_REPLACE);
            }
            mDbObject.setTransactionSuccessful();
            Log.e("test insertid","test");

        } catch (Exception e) {
Log.e("test  not insertid","test");
            e.printStackTrace();
        } finally {
            mDbObject.endTransaction();
        }
    }
    public ArrayList<TesTDetailsToSyn> getallPatientTestToSyn(ArrayList<TesTDetailsToSyn> patientList) {
        Cursor mcursor = makeRawQuery("SELECT *  from " + TABLE_PATIENT_TEST + " where SYN = 0 and " + TEST_RESULT + " is not null");
        if (mcursor != null && mcursor.moveToFirst()) {
            do {
                patientList.add(addPatientToListToSyn(mcursor));
            } while (mcursor.moveToNext());
        }
        return patientList;
    }
    public ArrayList<SubTestDetails> getallPatientTest(ArrayList<SubTestDetails> patientList,
                                                       String id) {
        Cursor mcursor = makeRawQuery("SELECT *  from " + TABLE_PATIENT_TEST + " where pid = '" +
                id + "' ORDER BY " + TEST_PROFILE_NAME);
        if (mcursor != null && mcursor.moveToFirst()) {
            do {
                patientList.add(addPatientToList(mcursor));
            } while (mcursor.moveToNext());
        }
        return patientList;
    }
    private SubTestDetails addPatientToList(Cursor mcursor) {
        SubTestDetails details = new SubTestDetails();
        details.setTest_interpretation(mcursor.getString(mcursor.getColumnIndex("test_interpretation")));
        details.setTest_precautions(mcursor.getString(mcursor.getColumnIndex("test_precautions")));
        details.setImage_permission(mcursor.getString(mcursor.getColumnIndex("image_permission")));
        details.setPatientId(mcursor.getString(mcursor.getColumnIndex(TEST_PATIENT_ID)));
        details.setTest_name(mcursor.getString(mcursor.getColumnIndex(TEST_NAME)));
        details.setTest_code(mcursor.getString(mcursor.getColumnIndex(TEST_CODE)));
        if (TextUtils.isEmpty(mcursor.getString(mcursor.getColumnIndex(RAPID_TEST_IMAGE))))
            details.setImagepre(false);
        else {
            details.setImagepre(true);
            details.setImagePath(mcursor.getString(mcursor.getColumnIndex(RAPID_TEST_IMAGE)));
        }
        if (details.getImage_permission().equals("0"))
            details.setImg_pri(false);
        else details.setImg_pri(true);
details.setIm_p(mcursor.getString(mcursor.getColumnIndex(TEST_ID)));
        details.setTest_result(mcursor.getString(mcursor.getColumnIndex(TEST_RESULT)));
        details.setTest_price(mcursor.getString(mcursor.getColumnIndex(TEST_COST)));
        details.setTest_manual_status(mcursor.getInt(mcursor.getColumnIndex(MANUAL_RESULT)));
        details.setPackageName(mcursor.getString(mcursor.getColumnIndex(PACKAGE_NAME)));
        details.setTest_unit(mcursor.getString(mcursor.getColumnIndex(TEST_UNIT)));
        details.setTest_low_bound_female(mcursor.getString(mcursor.getColumnIndex(FEMALE_LOWER_BOUND)));
        details.setTest_low_bound_male(mcursor.getString(mcursor.getColumnIndex(MALE_LOWER_BOUND)));
        details.setTest_upper_bound_female(mcursor.getString(mcursor.getColumnIndex(FEMALE_UPPER_BOUND)));
        details.setTest_upper_bound_male(mcursor.getString(mcursor.getColumnIndex(MALE_UPPER_BOUND)));
        details.setTest_type_name(mcursor.getString(mcursor.getColumnIndex(TEST_PROFILE_NAME)));
        return details;
    }
    private ContentValues getContentValues(SubTestDetails testDetails, String patientId, String campCode
            , String labelId) {
        ContentValues values = new ContentValues();
        values.put(TEST_PATIENT_ID, patientId);
        values.put(TEST_NAME, testDetails.getTest_name());
        values.put(TEST_CODE, testDetails.getTest_code());
        values.put(TEST_COST, testDetails.getTest_price());
        values.put(PACKAGE_NAME, testDetails.getPackageName());
        values.put(MANUAL_RESULT, testDetails.isTest_manual_status());
        values.put(TEST_UNIT, testDetails.getTest_unit());
        values.put(RAPID_TEST_IMAGE, testDetails.getImagePath());
        values.put(FEMALE_LOWER_BOUND, testDetails.getTest_low_bound_female());
        values.put(MALE_LOWER_BOUND, testDetails.getTest_low_bound_male());
        values.put(FEMALE_UPPER_BOUND, testDetails.getTest_upper_bound_female());
        values.put(MALE_UPPER_BOUND, testDetails.getTest_upper_bound_male());
        values.put(TEST_CREATE_TIME,  DateTimeUtils.getCurrentDate());
        TableWidalTest tableWidalTest=new TableWidalTest(mContext);
        String report_id=tableWidalTest.getReport_id(patientId);
        if (report_id!=null)
            values.put(REPORT_ID, report_id);
        else
        values.put(REPORT_ID, getSaltString());
        values.put(TEST_ID, testDetails.getTest_id());
        values.put(CAMP_CODE, campCode);
        values.put(LABEL_ID, labelId);
        values.put(TEST_PROFILE_NAME, testDetails.getTest_type_name());
        values.put("test_precautions",testDetails.getTest_precautions());
        values.put("image_permission",testDetails.getImage_permission());
        values.put("test_interpretation",testDetails.getTest_interpretation());
        return values;
    }
    public void addTestResult(String labelId, String testName, String testResult) {
     //   Log.e("imagepath 2",testResult);
//        mDbObject.updateWithOnConflict("UPDATE patient_test SET test_result = '" + testResult + "' WHERE label_id ="
//                + " '" + labelId + "' and test_code = '" + testName + "'");
        mDbObject.beginTransaction();
        try {
            ContentValues args = new ContentValues();
            args.put("test_result", testResult);
          long id=  mDbObject.updateWithOnConflict("patient_test",args,"label_id = ? AND test_code = ?"
                    ,new String[]{labelId,testName}, SQLiteDatabase.CONFLICT_REPLACE);

            mDbObject.setTransactionSuccessful();
            Log.e("test result insertid",testResult);
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("result not insertid",testResult);
        } finally {
            mDbObject.endTransaction();
        }
    }
    public void addTestResultByName(String labelId, String testName, String testResult, String imagePath) {
//        mDbObject.beginTransaction();
//        try {
//            ContentValues args = new ContentValues();
//            args.put("test_result", testResult);
//            args.put("tes");
//            long id=  mDbObject.updateWithOnConflict("patient_test",args,"label_id = ? AND test_code = ?"
//                    ,new String[]{labelId,testName}, SQLiteDatabase.CONFLICT_REPLACE);
//
//            mDbObject.setTransactionSuccessful();
//            Log.e("test result insertid",testResult);
//        } catch (Exception e) {
//            e.printStackTrace();
//            Log.e("result not insertid",testResult);
//        } finally {
//            mDbObject.endTransaction();
//        }
       // Log.e("imagepath 1",imagePath);
        mDbObject.execSQL("UPDATE patient_test SET test_result = '" + testResult + "', result_image = '"
                + imagePath + "',SYN = '" + "0" + "' WHERE pid = '" + labelId + "' and test_name = '" + testName + "'");
    }
    public String getTestCode(String id) {
        StringBuilder testCode = new StringBuilder();
        Cursor mcursor = makeRawQuery("SELECT test_code  from " + TABLE_PATIENT_TEST + " where pid = '" + id + "' limit 2");
        if (mcursor != null && mcursor.moveToFirst()) {
            do {
                if (TextUtils.isEmpty(testCode)) {
                    testCode.append(mcursor.getString(mcursor.getColumnIndex(TEST_CODE)));
                } else {
                    testCode.append(",").append(mcursor.getString(mcursor.getColumnIndex(TEST_CODE)));
                }
            } while (mcursor.moveToNext());
        }
        return TextUtils.isEmpty(testCode.toString()) ? "-" : testCode.toString();
    }
    public String getTotalTestCost(String id) {
        String testCode = "-";
        Cursor mcursor = makeRawQuery("SELECT sum(test_cost) as total from " + TABLE_PATIENT_TEST + " where pid = '" + id + "'");
        if (mcursor != null && mcursor.moveToFirst()) {
            testCode = mcursor.getString(mcursor.getColumnIndex("total"));
        }
        return TextUtils.isEmpty(testCode) ? "-" : testCode;
    }
    private TesTDetailsToSyn addPatientToListToSyn(Cursor mcursor) {
        TesTDetailsToSyn patient = new TesTDetailsToSyn();
        patient.setReport_patient_code(mcursor.getString(mcursor.getColumnIndex(TEST_PATIENT_ID)));
        if (TextUtils.isEmpty(mcursor.getString(mcursor.getColumnIndex(TEST_RESULT))))
            patient.setReport_result("null");
        else
            patient.setReport_result(mcursor.getString(mcursor.getColumnIndex(TEST_RESULT)));
        patient.setReport_created_time(mcursor.getString(mcursor.getColumnIndex(TEST_CREATE_TIME)));
        if (TextUtils.isEmpty(mcursor.getString(mcursor.getColumnIndex(RAPID_TEST_IMAGE))))
            patient.setReport_ref_img_name("null");
        else
            patient.setReport_ref_img_name(mcursor.getString(mcursor.getColumnIndex(RAPID_TEST_IMAGE)));
        patient.setReport_camp_code(mcursor.getString(mcursor.getColumnIndex(CAMP_CODE)));
        patient.setReport_lt_id(AppPreference.getString(mContext, AppPreference.USER_ID));
        patient.setReport_id(mcursor.getString(mcursor.getColumnIndex(REPORT_ID)));
        patient.setReport_test_id(mcursor.getString(mcursor.getColumnIndex(TEST_ID)));
        return patient;
    }
    public String getReport_id(String pid)
    {
        String report_id=null;
       SubTestDetails widalTest=null;
        Cursor mcursor = makeRawQuery("SELECT *  from " + TABLE_PATIENT_TEST+ " where pid = '" + pid + "'");
        if (mcursor != null && mcursor.moveToFirst()) {
            do {
               report_id=mcursor.getString(mcursor.getColumnIndex(REPORT_ID));
            } while (mcursor.moveToNext());
        }

        return report_id;
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
    public void updateSynStatus(String[] list) {
        try {
            for (String s : list) {
                ContentValues values = new ContentValues();
                values.put(SYN_STATUS, "1");
                mDbObject.updateWithOnConflict(TABLE_PATIENT_TEST, values, "report_id = ?",
                        new String[]{s}, SQLiteDatabase.CONFLICT_REPLACE);
                Log.e("the id of test",s);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void deleteTableContent() {
        mDbObject.delete(TABLE_PATIENT_TEST, null, null);
    }
    public void deletTest(ArrayList<SubTestDetails> list)
    {
        for (SubTestDetails testData : list) {
        // int t=   mDbObject.delete(TABLE_PATIENT_TEST, "pid="+testData.getPatientId(), null);
           // Log.d("data base ",t+"");
          int tr= mDbObject .delete(TABLE_PATIENT_TEST,
                    "pid=? AND "+"test_code"+ "=?",
                    new String[] {testData.getPatientId(),testData.getTest_code()});
         // mDbObject.close();

        }
        mDbObject.close();
    }
}