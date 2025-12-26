package com.zeroone.novaapp.home

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.zeroone.novaapp.databinding.ActivityManageNotificationsBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class ActivityManageNotifications: AppCompatActivity() {

    lateinit var binding: ActivityManageNotificationsBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityManageNotificationsBinding.inflate(layoutInflater)
        setContentView(binding.root)


    }

    fun setListener(){
        binding.imgBack.setOnClickListener {
            finish()
        }

    }
}