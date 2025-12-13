package com.zeroone.novaapp.home

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.ui.graphics.Color
import androidx.core.content.res.ResourcesCompat
import com.google.android.material.color.MaterialColors
import com.zeroone.novaapp.R
import com.zeroone.novaapp.databinding.ActivityPendingBillsBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ActivityPendingBill: AppCompatActivity() {

    lateinit var binding: ActivityPendingBillsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityPendingBillsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setListeners()


    }

    fun setListeners(){
        binding.imgBack.setOnClickListener {
            finish()
        }

        binding.cardMpesa.setOnClickListener {
            binding.viewMpesaSelected.setImageResource(R.drawable.circular_selected_indicator)
            binding.viewAirtelSelected.setImageResource(R.drawable.circular_unselected_indicator)

        }

        binding.cardAirtelMoney.setOnClickListener {
            binding.viewMpesaSelected.setImageResource(R.drawable.circular_unselected_indicator)
            binding.viewAirtelSelected.setImageResource(R.drawable.circular_selected_indicator)

        }


    }
}