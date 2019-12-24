package com.launchnet.btv.service

import android.app.Service
import android.content.Intent
import android.content.IntentFilter
import android.os.IBinder

class ImplicitService : Service() {
    var receiver: MyGeoSparkReceiver? = null

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onCreate() {
        super.onCreate()
        receiver = MyGeoSparkReceiver()
        register()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        return START_STICKY
    }

    override fun onDestroy() {
        super.onDestroy()
        unRegister()
    }

    private fun register() {
        try {
            registerReceiver(receiver, IntentFilter("com.geospark.android.RECEIVED"))
        } catch (e: Exception) {
        }
    }

    private fun unRegister() {
        try {
            if (receiver != null) {
                unregisterReceiver(receiver)
            }
        } catch (e: Exception) {
        }
    }
}