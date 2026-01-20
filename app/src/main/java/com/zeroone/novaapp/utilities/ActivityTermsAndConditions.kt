package com.zeroone.novaapp.utilities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.zeroone.novaapp.R
import com.zeroone.novaapp.databinding.ActivityTermsAndConditionsBinding
import com.zeroone.novaapp.utils.EdgeToEdgeManager
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@AndroidEntryPoint
class ActivityTermsAndConditions: AppCompatActivity() {

    lateinit var binding: ActivityTermsAndConditionsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityTermsAndConditionsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //event listeners
        setListeners()

        //set date
        setCurrentDateTime()


        // Enable edge-to-edge for Android 15+ devices
        EdgeToEdgeManager.handleEdgeToEdge(window, binding.root, R.color.primary_color)
    }

    fun setListeners(){
        binding.imgBack.setOnClickListener {
            finish()
        }
    }

    fun setCurrentDateTime(){
        val currentDate = Date()
        val dateFormat = SimpleDateFormat("dd MMM yyyy", Locale.getDefault())
        val dateOnly = dateFormat.format(currentDate)

        binding.textLastUpdated.text = "Last updated: $dateOnly"
    }

}