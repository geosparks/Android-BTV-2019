package com.launchnet.btv

import android.app.Application
import com.geospark.lib.GeoSpark

class MainApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        GeoSpark.initialize(this, "YOUR-PUBLISHABLE-KEY")
    }
}