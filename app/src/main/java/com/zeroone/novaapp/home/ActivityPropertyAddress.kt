package com.zeroone.novaapp.home

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.zeroone.novaapp.databinding.ActivityPropertyAddressBinding
import com.zeroone.novaapp.responseModels.PropertyDetailsModel
import com.zeroone.novaapp.utils.AppLog
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class ActivityPropertyAddress: AppCompatActivity() {

    lateinit var binding: ActivityPropertyAddressBinding
    var propertyType: String? = ""
    var propertySize: String? = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityPropertyAddressBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //event listeners
        setListeners()

        //init function
        init()

    }

    fun setListeners(){
        binding.btnSubmit.setOnClickListener {
            val intent = Intent(this, ActivityConfirmDetails::class.java)
            startActivity(intent)
        }

        binding.imgBack.setOnClickListener {
            finish()
        }

        binding.btnSubmit.setOnClickListener {

            val propertyDetails = PropertyDetailsModel(
                propertyName = binding.inputPropertyName.text.toString(),
                unitNumber = binding.inputUnitNumber.text.toString(),
                location = "Kahigo, Limuru Road",// binding.inputLocation.text.toString()
                propertySqrFts = binding.inputSizeSqft.text.toString(),
                propertyType,
                propertySize
            )


            val intent = Intent(this, ActivityConfirmDetails::class.java)
            intent.putExtra("propertyDetails", propertyDetails)
            startActivity(intent)
        }
    }

    fun init(){
        propertyType = intent.getStringExtra("propertyType")
        propertySize = intent.getStringExtra("propertySize")

        AppLog.Log("propertyType", propertyType)
        AppLog.Log("propertySize", propertySize)

    }
}