package com.accusterltapp.app;

/**
 * Created by appideas-user2 on 17/7/17.
 */

import android.content.Context;
import android.support.multidex.MultiDexApplication;
import android.util.Log;

import androidx.work.ExistingPeriodicWorkPolicy;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;

import com.accusterltapp.service.Controller;
import com.base.utility.SuncDataWorkManager;

import java.io.File;
import java.util.concurrent.TimeUnit;

public class LabTechnicianApplication extends MultiDexApplication {
    private static Controller controller;

    @Override
    public void onCreate() {
        super.onCreate();

        deleteCache(this);
        PeriodicWorkRequest refreshWork =
                new PeriodicWorkRequest.Builder(SuncDataWorkManager.class, 1, TimeUnit.HOURS)
                        .addTag("SyncDataWorkManager")
                        .build();
        WorkManager.getInstance().enqueueUniquePeriodicWork("WORKER_TAG", ExistingPeriodicWorkPolicy.REPLACE, refreshWork);
        //WorkManager.getInstance().enqueue(refreshWork);
        Log.d("Application called","TEST");
    }


    public static Controller getController(String baseUrl) {
        if (controller == null) {
            controller = Controller.buildNetworkInterface(baseUrl);
        }
        return controller;
    }
    public static void deleteCache(Context context) {
        try {
            File dir = context.getCacheDir();
            deleteDir(dir);
        } catch (Exception e) {}
    }public static boolean deleteDir(File dir) {
        if (dir != null && dir.isDirectory()) {
            String[] children = dir.list();
            for (int i = 0; i < children.length; i++) {
                boolean success = deleteDir(new File(dir, children[i]));
                if (!success) {
                    return false;
                }
            }
            return dir.delete();
        } else if(dir!= null && dir.isFile()) {
            return dir.delete();
        } else {
            return false;
        }
    }
}
