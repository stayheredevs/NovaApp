package com.zeroone.novaapp.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.zeroone.novaapp.adapters.AdapterActiveBookings
import com.zeroone.novaapp.adapters.AdapterMyProperties
import com.zeroone.novaapp.adapters.AdapterServices
import com.zeroone.novaapp.databinding.FragmentAdminHomeBinding
import com.zeroone.novaapp.home.ActivityAllProperties
import com.zeroone.novaapp.home.ActivityAllServices
import com.zeroone.novaapp.home.ActivityBookService
import com.zeroone.novaapp.responseModels.ActiveBookingsModel
import com.zeroone.novaapp.responseModels.PropertyModel
import com.zeroone.novaapp.responseModels.ServicesModel
import com.zeroone.novaapp.utilities.AppLog
import com.zeroone.novaapp.utilities.AppUtils
import com.zeroone.novaapp.viewModels.BookingsViewModel
import com.zeroone.novaapp.viewModels.PropertiesViewModel
import com.zeroone.novaapp.viewModels.ServicesViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FragmentAdminHome: Fragment() {

    lateinit var binding: FragmentAdminHomeBinding
    lateinit var adapterActiveBookings: AdapterActiveBookings
    lateinit var adapterServices: AdapterServices
    lateinit var adapterMyProperties: AdapterMyProperties

    lateinit var bookingsViewModel: BookingsViewModel
    lateinit var servicesViewModel: ServicesViewModel
    lateinit var propertiesViewModel: PropertiesViewModel


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        //init binding
        binding = FragmentAdminHomeBinding.inflate(inflater, container, false)

        //init view model
        bookingsViewModel = ViewModelProvider(this)[BookingsViewModel::class.java]

        //init services viewModel
        servicesViewModel = ViewModelProvider(this)[ServicesViewModel::class.java]

        //properties viewModel
        propertiesViewModel = ViewModelProvider(this)[PropertiesViewModel::class.java]


        //init adapter
        initActiveBookingsAdapter()

        //init services adapter
        initServicesAdapter()

        //init my properties adapter
        initMyPropertiesAdapter()

        //init
        init()

        //set listeners
        setListeners()


        return binding.root
    }

    fun init(){
        //bookings view model
        bookingsViewModel.activeBookingLiveData?.observe(requireActivity(), Observer {
            bookings->

            adapterActiveBookings.submitList(bookings)


        })
        bookingsViewModel.processActiveBookings()

        //services view model
        servicesViewModel.servicesLiveData?.observe(requireActivity(), Observer {
            services->

            adapterServices.submitList(services)

        })
        servicesViewModel.processServices()


        //properties view model
        propertiesViewModel.propertiesLiveData?.observe(requireActivity(), Observer {
            properties->


            val maxProperties = 3

            val lstFirstThreeProperties = properties.take(maxProperties)

            adapterMyProperties.submitList(lstFirstThreeProperties)

        })
        propertiesViewModel.processProperties()


    }

    fun setListeners(){

        binding.viewAllProperties.setOnClickListener {
            val intent = Intent(requireActivity(), ActivityAllProperties::class.java)
            startActivity(intent)
        }

        binding.viewAllServices.setOnClickListener {
            val intent = Intent(requireActivity(), ActivityAllServices::class.java)
            startActivity(intent)
        }
    }


    fun initActiveBookingsAdapter(){
        adapterActiveBookings = AdapterActiveBookings(
            onItemClick = {
                activeBookings->
                onActiveBookingsClick(activeBookings)
            }
        )

        val linearLayoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.recyclerViewBookings.apply {
            layoutManager = linearLayoutManager
            hasFixedSize()
            adapter = adapterActiveBookings

        }

    }

    fun initServicesAdapter(){
        adapterServices = AdapterServices(
            onItemClick = {
                service->
                onServiceSelection(service)
            }
        )

        val linearLayoutManager = GridLayoutManager(requireActivity(), 3)
        binding.recyclerViewServices.apply {
            layoutManager = linearLayoutManager
            hasFixedSize()
            adapter = adapterServices

        }

    }

    fun initMyPropertiesAdapter() {
        adapterMyProperties = AdapterMyProperties(
            onPropertyClicked = {
                property ->
                onPropertySelection(property)
            }
        )

        val linearLayoutManager = LinearLayoutManager(requireActivity(), LinearLayoutManager.VERTICAL, false)
        binding.recyclerViewProperties.apply {
            layoutManager = linearLayoutManager
            hasFixedSize()
            adapter = adapterMyProperties
        }

    }


    fun onActiveBookingsClick(activeBookings: ActiveBookingsModel){

        AppLog.Log("handleActiveBookingsClick", "clicked ${activeBookings.propertyId}")

    }


    fun onPropertySelection(property: PropertyModel){

        AppLog.Log("handleServiceClick", "clicked ${property.propertyId}")

        AppUtils.ToastMessage("clicked ${property.propertyName}", requireContext())
    }


    fun onServiceSelection(service: ServicesModel){

        AppLog.Log("handleActiveBookingsClick", "clicked ${service.serviceTypeName}")
    }



}