package com.zeroone.novaapp.home

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.zeroone.novaapp.databinding.ActivityAddCardBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class ActivityAddCard: AppCompatActivity()  {

    lateinit var binding: ActivityAddCardBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityAddCardBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setListeners()

    }

    fun setListeners(){
        binding.imgBack.setOnClickListener {
            finish()
        }

    }
}