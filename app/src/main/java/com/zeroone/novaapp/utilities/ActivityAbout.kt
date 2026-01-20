package com.zeroone.novaapp.utilities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.zeroone.novaapp.databinding.ActivityAboutBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ActivityAbout: AppCompatActivity() {

    lateinit var binding: ActivityAboutBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityAboutBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //event listeners
        setListeners()
    }

    fun setListeners(){
        binding.imgBack.setOnClickListener {
            finish()
        }


    }
}