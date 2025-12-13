package com.zeroone.novaapp.home

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.zeroone.novaapp.databinding.ActivityPropertySizeBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ActivityPropertySize: AppCompatActivity() {

    lateinit var binding: ActivityPropertySizeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityPropertySizeBinding.inflate(layoutInflater)
        setContentView(binding.root)

    }
}