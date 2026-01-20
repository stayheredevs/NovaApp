package com.zeroone.novaapp.home

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.zeroone.novaapp.adapters.AdapterPropertySize
import com.zeroone.novaapp.databinding.ActivityPropertySizeBinding
import com.zeroone.novaapp.responseModels.PropertyDetailsModel
import com.zeroone.novaapp.viewModels.PropertiesViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ActivityPropertySize: AppCompatActivity() {

    lateinit var binding: ActivityPropertySizeBinding
    private val propertiesViewModel: PropertiesViewModel by viewModels()
    lateinit var adapterPropertySize: AdapterPropertySize

    val propertyDetailsModel: PropertyDetailsModel? = null



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityPropertySizeBinding.inflate(layoutInflater)
        setContentView(binding.root)


        //init adapter
        initSizeAdapter()

        //init
        init()

        //set listeners
        setListeners()
    }

    fun setListeners(){
        binding.imgBack.setOnClickListener {
            finish()
        }
    }

    fun init(){
        propertiesViewModel.propertySizeLiveData?.observe(this, Observer { propertyList ->

            // Submit to adapter
            adapterPropertySize.submitList(propertyList)

        })
        propertiesViewModel.processPropertySize()



    }

    fun initSizeAdapter(){
        adapterPropertySize = AdapterPropertySize()

        val linearLayoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        binding.recyclerView.apply {
            layoutManager = linearLayoutManager
            hasFixedSize()
            adapter = adapterPropertySize
        }

    }
}