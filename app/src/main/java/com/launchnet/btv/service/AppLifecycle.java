package com.launchnet.btv.service;


import android.content.Context;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.OnLifecycleEvent;

import com.launchnet.btv.db.SharedPrefKt;


public class AppLifecycle implements LifecycleObserver {

    private Context mContext;

    public AppLifecycle(Context context) {
        this.mContext = context;
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    public void onResume() {
        SharedPrefKt.setState(mContext, "F");
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    public void onPause() {
        SharedPrefKt.setState(mContext, "B");
    }
}
