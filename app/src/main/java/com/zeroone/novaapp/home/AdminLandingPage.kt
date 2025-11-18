package com.zeroone.novaapp.home

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.material.navigation.NavigationBarView
import com.zeroone.novaapp.fragments.FragmentAdminHome
import com.zeroone.novaapp.R
import com.zeroone.novaapp.databinding.AdminLandingPageBinding
import com.zeroone.novaapp.fragments.FragmentAccount
import com.zeroone.novaapp.fragments.FragmentAssets
import com.zeroone.novaapp.fragments.FragmentHistory
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

                    fragment = FragmentHistory()
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
}