package com.zeroone.novaapp.home

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.zeroone.novaapp.databinding.AdminLandingPageBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AdminLandingPage: AppCompatActivity() {
    lateinit var binding: AdminLandingPageBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = AdminLandingPageBinding.inflate(layoutInflater)
        setContentView(binding.root)

    }
}