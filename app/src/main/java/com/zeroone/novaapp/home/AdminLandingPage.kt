package com.zeroone.novaapp.home

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.view.MenuItem
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import com.freshchat.consumer.sdk.Freshchat
import com.freshchat.consumer.sdk.FreshchatConfig
import com.google.android.material.navigation.NavigationBarView
import com.google.firebase.messaging.FirebaseMessaging
import com.zeroone.novaapp.fragments.FragmentAdminHome
import com.zeroone.novaapp.R
import com.zeroone.novaapp.databinding.AdminLandingPageBinding
import com.zeroone.novaapp.fragments.FragmentAccount
import com.zeroone.novaapp.fragments.FragmentAssets
import com.zeroone.novaapp.fragments.FragmentJobs
import com.zeroone.novaapp.utilities.AppLog
import com.zeroone.novaapp.utilities.EdgeToEdgeManager
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AdminLandingPage: AppCompatActivity() {
    lateinit var binding: AdminLandingPageBinding

    lateinit var fragment: Fragment


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = AdminLandingPageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Enable edge-to-edge for Android 15+ devices
        EdgeToEdgeManager.handleEdgeToEdge(window, binding.root, R.color.primary_color)


        // Load initial fragment first (before setting listener to avoid conflicts)
        fragment = FragmentAdminHome()
        loadInitialFragment(fragment, "fragment_admin_home")

        // Set initial selected item
        binding.bottomNavView.selectedItemId = R.id.nav_providers

        //event listeners
        setListeners()

        val freshChatConfig = FreshchatConfig(resources.getString(R.string.freshchat_app_id), resources.getString(R.string.freshchat_app_key))
        freshChatConfig.isCameraCaptureEnabled = true
        freshChatConfig.isGallerySelectionEnabled = true
        freshChatConfig.isResponseExpectationEnabled = true
        freshChatConfig.domain = "msdk.me.freshchat.com"


        Freshchat.getInstance(applicationContext).init(freshChatConfig)

        // Get FCM Token
        getCurrentFcmToken()

    }

    fun setListeners(){

        binding.bottomNavView.setOnItemSelectedListener(bottomNavigationItemSelectedListener)
    }

    private val bottomNavigationItemSelectedListener: NavigationBarView.OnItemSelectedListener = object : NavigationBarView.OnItemSelectedListener {
        override fun onNavigationItemSelected(item: MenuItem): Boolean {

            when (item.itemId) {
                R.id.nav_providers -> {

                    AppLog.Log("bottomNavigationItemSelectedListener", "nav_providers")

                    fragment = FragmentAdminHome()
                    loadFragment(fragment, "fragment_admin_home")
                    return true
                }

                R.id.nav_my_assets -> {

                    AppLog.Log("bottomNavigationItemSelectedListener", "nav_my_assets")

                    fragment = FragmentAssets()
                    loadFragment(fragment, "fragment_admin_assets")
                    return true
                }

                R.id.nav_history -> {

                    AppLog.Log("bottomNavigationItemSelectedListener", "nav_history")

                    fragment = FragmentJobs()
                    loadFragment(fragment, "fragment_admin_history")
                    return true
                }

                R.id.nav_account -> {

                    AppLog.Log("bottomNavigationItemSelectedListener", "nav_account")

                    fragment = FragmentAccount()
                    loadFragment(fragment, "fragment_admin_account")
                    return true
                }
            }

            return false
        }
    }

    private fun loadInitialFragment(fragment: Fragment, fragment_tag: String) {
        try {
            val transaction = supportFragmentManager.beginTransaction()
            transaction.add(R.id.frame_container, fragment, fragment_tag)
            transaction.commitAllowingStateLoss()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun loadFragment(fragment: Fragment, fragment_tag: String) {
        try {
            val transaction = supportFragmentManager.beginTransaction()
            transaction.replace(R.id.frame_container, fragment, fragment_tag)
            // Don't add to back stack for bottom navigation
            transaction.commitAllowingStateLoss()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun getCurrentFcmToken(){
        FirebaseMessaging.getInstance().token.addOnCompleteListener {
            task->
            if (!task.isSuccessful) {
                AppLog.Log("firebase_token", "Fetching FCM registration token failed::: ${task.exception}")
                return@addOnCompleteListener
            }

            // Get new FCM registration token
            val token = task.result
            AppLog.Log("firebase_token", "FCM Token: $token")

            // → Send this token to your server / save it / use it
            // Example: sendRegistrationToServer(token)
        }
    }

}