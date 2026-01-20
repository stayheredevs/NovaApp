package com.zeroone.novaapp.utilities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.zeroone.novaapp.R
import com.zeroone.novaapp.databinding.ActivityManageNotificationsBinding
import com.zeroone.novaapp.utils.EdgeToEdgeManager
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ActivityManageNotifications: AppCompatActivity() {

    lateinit var binding: ActivityManageNotificationsBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityManageNotificationsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //event listeners
        setListener()

        // Enable edge-to-edge for Android 15+ devices
        EdgeToEdgeManager.handleEdgeToEdge(window, binding.root, R.color.primary_color)

    }

    fun setListener(){
        binding.imgBack.setOnClickListener {
            finish()
        }

    }
}