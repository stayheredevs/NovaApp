package com.zeroone.novaapp.main

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class NovaApplication: Application() {

    override fun onCreate() {
        super.onCreate()
    }
}