package com.base.utility;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;

import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.accusterltapp.activity.SynDataToServer;

public class SuncDataWorkManager extends Worker {

    public SuncDataWorkManager(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @NonNull
    @Override
    public Result doWork() {
        Context context = getApplicationContext();

        new SynDataToServer(context);
        Log.d("Worker called","TEST");
        return Worker.Result.success();
    }
   /* @NonNull
    @Override
    public Worker.Result doWork() {
        Context context = getApplicationContext();

        Log.d("RefreshDataWorker", "refreshing data....");
        return Worker.Result.SUCCESS;
    }*/
}