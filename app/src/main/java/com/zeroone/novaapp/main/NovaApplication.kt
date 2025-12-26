package com.zeroone.novaapp.main

import android.app.Application
import com.freshchat.consumer.sdk.Freshchat
import com.freshchat.consumer.sdk.FreshchatConfig
import com.zeroone.novaapp.R
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class NovaApplication: Application() {

    override fun onCreate() {
        super.onCreate()

        val freshChatConfig = FreshchatConfig(resources.getString(R.string.freshchat_app_id), resources.getString(R.string.freshchat_app_key))
        freshChatConfig.isCameraCaptureEnabled = true
        freshChatConfig.isGallerySelectionEnabled = true
        freshChatConfig.isResponseExpectationEnabled = true
        freshChatConfig.domain = "msdk.me.freshchat.com"


        Freshchat.getInstance(applicationContext).init(freshChatConfig)

    }
}