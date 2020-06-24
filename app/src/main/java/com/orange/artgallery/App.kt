package com.orange.artgallery

import android.app.Application
import com.facebook.stetho.Stetho
import com.orange.artgallery.instances.Instances

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) {
            Stetho.initializeWithDefaults(this)
        }
        Instances.init(this)
    }
}