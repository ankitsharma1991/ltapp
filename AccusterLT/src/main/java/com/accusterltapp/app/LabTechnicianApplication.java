package com.accusterltapp.app;

/**
 * Created by appideas-user2 on 17/7/17.
 */

import android.support.multidex.MultiDexApplication;
import android.util.Log;

import androidx.work.ExistingPeriodicWorkPolicy;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;

import com.accusterltapp.service.Controller;
import com.base.utility.SuncDataWorkManager;

import java.util.concurrent.TimeUnit;

public class LabTechnicianApplication extends MultiDexApplication {
    private static Controller controller;

    @Override
    public void onCreate() {
        super.onCreate();
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

}
