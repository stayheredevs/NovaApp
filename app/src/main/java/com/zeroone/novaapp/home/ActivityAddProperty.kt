package com.zeroone.novaapp.home

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.zeroone.novaapp.R
import com.zeroone.novaapp.adapters.AdapterPropertySize
import com.zeroone.novaapp.adapters.AdapterPropertyType
import com.zeroone.novaapp.databinding.ActivityAddPropertyBinding
import com.zeroone.novaapp.databinding.PropertySizeBottomSheetBinding
import com.zeroone.novaapp.responseModels.PropertySize
import com.zeroone.novaapp.viewModels.PropertiesViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ActivityAddProperty: AppCompatActivity() {

    lateinit var binding: ActivityAddPropertyBinding
    lateinit var adapterPropertyType: AdapterPropertyType
    lateinit var adapterPropertySize: AdapterPropertySize

    val lstSizeOptions: MutableList<PropertySize> = mutableListOf()

    //initialize view model
    val propertiesViewModel: PropertiesViewModel by viewModels ()

    var property_type: String? = ""
    var property_size: String? = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityAddPropertyBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //initialize property types adapter
        initPropertyTypes()

        //initialize view model
        init()

        //event listeners
        setListeners()

    }

    fun setListeners(){
        binding.imgBack.setOnClickListener {
            finish()
        }
    }

    fun init(){
        propertiesViewModel.propertyTypesLiveData?.observe(this, Observer {
            propertyTypes ->
                adapterPropertyType.submitList(propertyTypes)
            })
        propertiesViewModel.processPropertyTypes()


        propertiesViewModel.propertySizeLiveData?.observe(this, Observer { propertyList ->

            // Submit to adapter
            lstSizeOptions.clear()
            lstSizeOptions.addAll(propertyList)

            showBottomSheetDialog(this, lstSizeOptions)


        })


        }

    fun initPropertyTypes(){
        adapterPropertyType = AdapterPropertyType(
            onPropertyTypeClicked = { propertyType ->

                property_type = propertyType.propertyTypeName

                //making the call to get the property size
                propertiesViewModel.processPropertySize()

            })

        val linearLayoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

        binding.recyclerView.apply {
            layoutManager = linearLayoutManager
            hasFixedSize()
            adapter = adapterPropertyType
        }

    }

    private fun showBottomSheetDialog(context: Context?, sizeOptions: MutableList<PropertySize>?) {
        val binding = PropertySizeBottomSheetBinding.inflate(LayoutInflater.from(context), null, false)

        val dialog = BottomSheetDialog(context!!, R.style.bottomSheetDialogTheme)
        dialog.setContentView(binding.root)
        dialog.setCancelable(true)


        adapterPropertySize = AdapterPropertySize(
            onOptionClick = {
                propertySize->

                property_size = propertySize.sizeName

                val intent = Intent(this, ActivityPropertyAddress::class.java)
                intent.putExtra("propertyType", property_type)
                intent.putExtra("propertySize", property_size)
                startActivity(intent)

            }

        )

        val linearLayoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

        binding.recyclerView.apply {
            layoutManager = linearLayoutManager
            hasFixedSize()
            adapter = adapterPropertySize
        }

        // Set the service options in the adapter
        adapterPropertySize.submitList(sizeOptions)

        //event listener


        dialog.show()
    }
}