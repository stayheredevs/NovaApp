package com.zeroone.novaapp.home

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.zeroone.novaapp.R
import com.zeroone.novaapp.adapters.AdapterMyProperties
import com.zeroone.novaapp.databinding.ActivityAllPropertiesBinding
import com.zeroone.novaapp.responseModels.PropertyModel
import com.zeroone.novaapp.utils.EdgeToEdgeManager
import com.zeroone.novaapp.utils.SharedPreference
import com.zeroone.novaapp.viewModels.PropertiesViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class ActivityAllProperties: AppCompatActivity() {

    lateinit var binding: ActivityAllPropertiesBinding
    lateinit var adapterMyProperties: AdapterMyProperties
    lateinit var propertiesViewModel: PropertiesViewModel
    private var originalPropertiesList: MutableList<PropertyModel> = mutableListOf()

    var lstSelectedProperty: MutableList<PropertyModel> = mutableListOf()

    @Inject
    lateinit var sharedPreference: SharedPreference


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityAllPropertiesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Enable edge-to-edge for Android 15+ devices
        EdgeToEdgeManager.handleEdgeToEdge(window, binding.root, R.color.primary_color)

        propertiesViewModel = ViewModelProvider(this)[PropertiesViewModel::class.java]

        //init adapter
        initAdapter()

        //init
        init()

        //set listeners
        setListeners()


    }

    fun init(){

        propertiesViewModel.propertiesLiveData?.observe(this, Observer {
            propertyList ->

            // Populate original list for filtering
            originalPropertiesList.clear()
            originalPropertiesList = propertyList.toMutableList()

            // Submit to adapter
            adapterMyProperties.submitList(propertyList)


        })
        propertiesViewModel.processProperties()



    }

    fun setListeners(){

        binding.imgClose.setOnClickListener {
            finish()
        }

        //setup filter
        setUpFilter()

        binding.btnClearSearch.setOnClickListener {
            binding.editTextSearch.text?.clear()
        }
    }



    fun initAdapter(){
        adapterMyProperties = AdapterMyProperties(
            onPropertyClicked = { propertyModel ->

                handlePropertyClick(propertyModel)

            }


        )

        val linearLayoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        binding.recyclerViewBookings.apply {
            layoutManager = linearLayoutManager
            hasFixedSize()
            adapter = adapterMyProperties

        }
    }

    fun setUpFilter(){
        binding.editTextSearch.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(characters: CharSequence?, p1: Int, p2: Int, p3: Int) {
                filterProperties(characters.toString())
            }

            override fun afterTextChanged(p0: Editable?) {

            }

        })
    }

    fun filterProperties(query: String){

        //determining visibility of clear button
        binding.btnClearSearch.visibility = if (query.isNotEmpty()) {
            android.view.View.VISIBLE
        } else {
            android.view.View.GONE
        }

        val filteredList = if (query.isEmpty()) {
            originalPropertiesList
        } else {

            //filtering using multiple fields
            originalPropertiesList.filter {
                property ->
                property.propertyName?.contains(query, ignoreCase = true) == true
                //||
                //                property.size?.contains(query, ignoreCase = true) == true
            }
        }

        //update adapter with filtered list
        adapterMyProperties.submitList(filteredList.toMutableList())

    }

    fun handlePropertyClick(propertyModel: PropertyModel){

        if (sharedPreference.eventSource == "BOOKING_COMPLETE"){

            lstSelectedProperty.add(propertyModel)

            sharedPreference.selectedProperty = lstSelectedProperty

            finish()

        }




    }

}