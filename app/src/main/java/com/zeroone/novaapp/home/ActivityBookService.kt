package com.zeroone.novaapp.home

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.zeroone.novaapp.adapters.AdapterServiceList
import com.zeroone.novaapp.databinding.ActivityBookServiceBinding
import com.zeroone.novaapp.viewModels.BookingsViewModel
import com.zeroone.novaapp.viewModels.ServicesViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class ActivityBookService: AppCompatActivity() {

    lateinit var binding: ActivityBookServiceBinding
    lateinit var viewModel: BookingsViewModel
    lateinit var adapterServiceList: AdapterServiceList
    lateinit var serviceViewModel: ServicesViewModel





    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityBookServiceBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //init view model
        serviceViewModel = ViewModelProvider(this)[ServicesViewModel::class.java]

        //init service list adapter
        initServiceListAdapter()

        //init
        init()

    }

    fun init(){
        serviceViewModel.servicesLiveData?.observe(this, Observer { services ->

            adapterServiceList.submitList(services)

        })
        serviceViewModel.processServices()

    }

    fun initServiceListAdapter(){
        adapterServiceList = AdapterServiceList()

        val linearLayoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        binding.recyclerViewBookings.apply {
            layoutManager = linearLayoutManager
            hasFixedSize()
            adapter = adapterServiceList

        }



    }
}