package com.accusterltapp.database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.accusterltapp.table.TableCamp;
import com.accusterltapp.table.TableGenericPacakge;
import com.accusterltapp.table.TableGenericTest;
import com.accusterltapp.table.TablePackageList;
import com.accusterltapp.table.TablePackageTestDetail;
import com.accusterltapp.table.TablePatient;
import com.accusterltapp.table.TablePatientTest;
import com.accusterltapp.table.TableQcData;
import com.accusterltapp.table.TableReportSetting;
import com.accusterltapp.table.TableWidalData;
import com.accusterltapp.table.TableWidalTest;


public class LtSqliteOpenHelper extends SQLiteOpenHelper {

    // All Static variables
    // Database Version
    public static final int DATABASE_VERSION = 1;

    // Database Name
    public static final String DATABASE_NAME = "LtData";


    public Context mContext;
    public static LtSqliteOpenHelper sInstance;
    public SQLiteDatabase database;

    public static synchronized LtSqliteOpenHelper getInstance(Context context) {

        // Use the application context, which will ensure that you
        // don't accidentally leak an Activity's context.
        if (sInstance == null) {
            sInstance = new LtSqliteOpenHelper(context.getApplicationContext());
        }
        return sInstance;
    }

    public synchronized SQLiteDatabase getDatabase() {
        if (database == null) {
            database = sInstance.getReadableDatabase();
            database = sInstance.getWritableDatabase();
        }
        return database;
    }

    public LtSqliteOpenHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        mContext = context;
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {

        database = db;
     //   db.execSQL(ReportSetting.create_table_report);
        db.execSQL(TableWidalTest.CREATE_WIDAL_TEST_TABLE);
        db.execSQL(TableWidalData.CREATE_WIDAL_DATA_TABLE);
        db.execSQL(TableCamp.create_table_camp);
        db.execSQL(TablePatient.create_table_patient);
        db.execSQL(TablePatientTest.create_table_patient_test);
//        db.execSQL(TableSubTestDetails.create_table_test_Details);
        db.execSQL(TablePackageList.create_table_test_package);
        db.execSQL(TableGenericPacakge.create_table_generic_package);
        db.execSQL(TableGenericTest.create_table_generic_test_Details);
        db.execSQL(TablePackageTestDetail.create_table_packege_test_Details);
        db.execSQL(TableQcData.create_table_qcData);
        db.execSQL(TableReportSetting.create_table_reports);


        Log.e("table creatid","tb");

    }



    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        // db.execSQL("DROP TABLE IF EXISTS " + TABLE_NOTIFICATIONS);
        database = db;
//        if (newVersion > 1) {
//        }
    }


    public boolean isColumnExist(SQLiteDatabase db, String tableName, String fieldName) {
        Cursor cursor = db.query(tableName, null, null, null, null, null, null);
        try {
            cursor.getColumnIndexOrThrow(fieldName);
            cursor.close();
            return true;
        } catch (Exception e) {
            cursor.close();
            return false;
        }

    }

}
