package com.zeroone.novaapp.home

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.zeroone.novaapp.databinding.ActivityDetailsConfirmationBinding
import com.zeroone.novaapp.responseModels.PropertyDetailsModel

class ActivityConfirmDetails: AppCompatActivity() {

    lateinit var binding: ActivityDetailsConfirmationBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDetailsConfirmationBinding.inflate(layoutInflater)
        setContentView(binding.root)

    }

    fun init(){
        //passing the params
        val propertyDetails = intent.getParcelableExtra<PropertyDetailsModel>("propertyDetails")
        binding.txtPropertyType.text = propertyDetails?.propertyType

        binding.txtPropertySizeDetails.text =
            "${propertyDetails?.propertySize} ${Typography.bullet} ${propertyDetails?.propertySqrFts} sq ft"

        binding.txtPropertyAddress.text = propertyDetails?.location

    }
}