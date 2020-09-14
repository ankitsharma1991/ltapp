package com.accusterltapp.app;

/**
 * Created by appideas-user2 on 17/7/17.
 */

import android.support.multidex.MultiDexApplication;

import com.accusterltapp.service.Controller;

public class LabTechnicianApplication extends MultiDexApplication {
    private static Controller controller;

    @Override
    public void onCreate() {
        super.onCreate();
    }


    public static Controller getController(String baseUrl) {
        if (controller == null) {
            controller = Controller.buildNetworkInterface(baseUrl);
        }
        return controller;
    }

}
