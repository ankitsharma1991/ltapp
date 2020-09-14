package com.accusterltapp.table;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.accusterltapp.database.BaseTable;
import com.accusterltapp.database.LtSqliteOpenHelper;
import com.accusterltapp.model.SubTestDetails;
import com.accusterltapp.model.TestDetails;

import java.util.ArrayList;

/**
 * Created by Jyoti on 11/1/2017.
 */

public class TablePackageList extends BaseTable {
    private Context mContext;
    public static final String TABLE_TEST_PACKAGE = "tbl_test_profile";
    public static final String TEST_PROFILE_ID = "test_profile_id";
    public static final String TEST_PROFILE_NAME = "test_profile_name";
    public static final String TEST_PACKAGE_CODE = "test_package_code";
    public static final String SYN_STATUS = "SYN";

    public static final String create_table_test_package = "create table if not exists " + TABLE_TEST_PACKAGE + " ("
            + TEST_PROFILE_ID + " integer primary key autoincrement, "
            + TEST_PACKAGE_CODE + " text, "
            + SYN_STATUS + " text DEFAULT 0 , "
            + TEST_PROFILE_NAME + " text unique);";

    public TablePackageList(Context context) {
        mContext = context;
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


    private TestDetails addPackageDetails(Cursor mcursor) {
        TestDetails details = new TestDetails();
        details.setPackage_name(mcursor.getString(mcursor.getColumnIndex(TEST_PROFILE_NAME)));
        details.setPackage_code(mcursor.getString(mcursor.getColumnIndex(TEST_PACKAGE_CODE)));
        TablePackageTestDetail subTestDetails = new TablePackageTestDetail(mContext);
        details.setTest_list(subTestDetails.getAllSubTestList(new ArrayList<SubTestDetails>(),
                mcursor.getString(mcursor.getColumnIndex(TEST_PROFILE_NAME))));
        return details;
    }

    private ContentValues getContentValues(ArrayList<SubTestDetails> packageDetails, String packageName, String packageCode) {
        ContentValues values = new ContentValues();
        values.put(TEST_PROFILE_NAME, packageName);
        values.put(TEST_PACKAGE_CODE, packageCode);
        TablePackageTestDetail subTestDetails = new TablePackageTestDetail(mContext);
        ArrayList<SubTestDetails> testlist=new ArrayList<>();
      testlist=subTestDetails.getAllSubTestList(testlist,packageName);
      for(int i=0;i<packageDetails.size();i++)
      {
          boolean flag=false;
          for(int j=0;j<testlist.size();j++)
          {
              if (packageDetails.get(i).getTest_id().equals(testlist.get(j).getTest_id()))
                  flag=true;
          }
          if (!flag)
              subTestDetails.insertSubTestDetails(packageDetails.get(i),packageName,packageCode);
      }

        return values;
    }

    public void insertPackageList(ArrayList<TestDetails> accountData) {
        mDbObject.beginTransaction();
        try {
            for (TestDetails testData : accountData) {
                mDbObject.insertWithOnConflict(TABLE_TEST_PACKAGE, null, getContentValues(testData.getTest_list()
                        ,testData.getPackage_name(),testData.getPackage_code())
                        , SQLiteDatabase.CONFLICT_REPLACE);
            }
            mDbObject.setTransactionSuccessful();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            mDbObject.endTransaction();
        }
    }

    public void insertPacakge(ArrayList<SubTestDetails> subTestDetails, String packageName, String packageCode) {
        mDbObject.beginTransaction();
        try {
            mDbObject.insertWithOnConflict(TABLE_TEST_PACKAGE, null, getContentValues(subTestDetails,packageName,packageCode)
                    , SQLiteDatabase.CONFLICT_REPLACE);
            mDbObject.setTransactionSuccessful();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            mDbObject.endTransaction();
        }
    }


    public ArrayList<TestDetails> getPackageList(ArrayList<TestDetails> patientList) {
        Cursor mcursor = makeRawQuery("SELECT *  from " + TABLE_TEST_PACKAGE);
        if (mcursor != null && mcursor.moveToFirst()) {
            do {
                patientList.add(addPackageDetails(mcursor));
            } while (mcursor.moveToNext());
        }
        return patientList;
    }


    public ArrayList<TestDetails> getPackageListToSyn(ArrayList<TestDetails> patientList) {
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
        new TablePackageTestDetail(mContext).deleteTableContent();
    }

}
