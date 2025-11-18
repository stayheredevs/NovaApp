package com.zeroone.novaapp.home

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.zeroone.novaapp.adapters.AdapterAllServices
import com.zeroone.novaapp.databinding.ActivityAllServicesBinding
import com.zeroone.novaapp.responseModels.ServicesModel
import com.zeroone.novaapp.utilities.AppUtils
import com.zeroone.novaapp.viewModels.ServicesViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class ActivityAllServices: AppCompatActivity() {

    lateinit var binding: ActivityAllServicesBinding
    lateinit var adapterAllServices: AdapterAllServices
    lateinit var servicesViewModel: ServicesViewModel
    private var originalServicesList: MutableList<ServicesModel> = mutableListOf()



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityAllServicesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //init view model
        servicesViewModel = ViewModelProvider(this)[ServicesViewModel::class.java]


        //init services adapter
        initAllServicesAdapter()

        //int
        init()

        //set listeners
        setListeners()


    }

    fun init(){
        servicesViewModel.servicesLiveData?.observe(this, Observer {
            services->

            // Store the original list
            originalServicesList = services.toMutableList()

            //submit list to adapter
            adapterAllServices.submitList(services)


        })
        servicesViewModel.processServices()
    }

    fun setListeners(){
        binding.imgClose.setOnClickListener {
            finish()
        }

        // Setup search functionality
        setupSearchFilter()

        // Clear search button
        binding.btnClearSearch.setOnClickListener {
            binding.editTextSearch.text?.clear()
        }
    }

    private fun setupSearchFilter() {
        binding.editTextSearch.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                filterServices(s.toString())
            }

            override fun afterTextChanged(s: Editable?) {}
        })
    }

    private fun filterServices(query: String) {
        // Show/hide clear button based on text
        binding.btnClearSearch.visibility = if (query.isNotEmpty()) {
            android.view.View.VISIBLE
        } else {
            android.view.View.GONE
        }

        // Filter the list
        val filteredList = if (query.isEmpty()) {
            originalServicesList
        } else {
            originalServicesList.filter { service ->
                service.serviceTypeName?.contains(query, ignoreCase = true) == true
            }
        }

        // Update adapter with filtered list
        adapterAllServices.submitList(filteredList.toMutableList())
    }

    fun initAllServicesAdapter() {
        adapterAllServices = AdapterAllServices(
            onServiceClick = { serviceModel ->

                //handle service click
                handleOnServiceClick(serviceModel = serviceModel)

            }
        )

        val linerLayoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        binding.recyclerViewServices.apply {
            layoutManager = linerLayoutManager
            hasFixedSize()
            adapter = adapterAllServices
        }

    }

    fun handleOnServiceClick(serviceModel: ServicesModel){
        AppUtils.ToastMessage("onServiceClick: ${serviceModel.serviceTypeName}", this)
    }

}