package com.accusterltapp.service;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.accusterltapp.activity.SynDataToServer;
import com.birbit.android.jobqueue.Job;
import com.birbit.android.jobqueue.Params;
import com.birbit.android.jobqueue.RetryConstraint;

public class SynDataService extends Job {
    protected SynDataService(Params params) {
        super(params);
    }

    @Override
    public void onAdded() {

    }

    @Override
    public void onRun() throws Throwable {
        new SynDataToServer(getApplicationContext());

    }

    @Override
    protected void onCancel(int cancelReason, @Nullable Throwable throwable) {

    }

    @Override
    protected RetryConstraint shouldReRunOnThrowable(@NonNull Throwable throwable, int runCount, int maxRunCount) {
        return null;
    }
}
