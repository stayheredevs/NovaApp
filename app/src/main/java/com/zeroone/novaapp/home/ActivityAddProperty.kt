package com.zeroone.novaapp.home

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.zeroone.novaapp.databinding.ActivityAddPropertyBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ActivityAddProperty: AppCompatActivity() {

    lateinit var binding: ActivityAddPropertyBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityAddPropertyBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}