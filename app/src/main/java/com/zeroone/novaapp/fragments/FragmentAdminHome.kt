package com.zeroone.novaapp.fragments

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.zeroone.novaapp.adapters.AdapterActiveBookings
import com.zeroone.novaapp.adapters.AdapterMyProperties
import com.zeroone.novaapp.adapters.AdapterServices
import com.zeroone.novaapp.databinding.FragmentAdminHomeBinding
import com.zeroone.novaapp.home.ActivityActiveJob
import com.zeroone.novaapp.home.ActivityAllProperties
import com.zeroone.novaapp.home.ActivityAllServices
import com.zeroone.novaapp.home.ActivityBookService
import com.zeroone.novaapp.responseModels.ActiveBookingsModel
import com.zeroone.novaapp.responseModels.PropertyModel
import com.zeroone.novaapp.responseModels.ServicesModel
import com.zeroone.novaapp.utils.AppLog
import com.zeroone.novaapp.utils.AppUtils
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

        //post notification permission
        askNotificationPermission()



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

        val intent = Intent(requireActivity(), ActivityActiveJob::class.java)
        intent.putExtra("propertyId", activeBookings.propertyId)
        startActivity(intent)



    }


    fun onPropertySelection(property: PropertyModel){

        AppLog.Log("handleServiceClick", "clicked ${property.propertyId}")

        AppUtils.ToastMessage("clicked ${property.propertyName}", requireContext())

        val intent = Intent(requireActivity(), ActivityBookService::class.java)
        intent.putExtra("property", property)
        startActivity(intent)

    }


    fun onServiceSelection(service: ServicesModel){

        AppLog.Log("handleActiveBookingsClick", "clicked ${service.serviceTypeName}")
    }


    // Declare the launcher at the top of your Activity/Fragment:
    private val requestPermissionLauncher: ActivityResultLauncher<String> =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted: Boolean ->
            if (isGranted) {
                // FCM SDK (and your app) can post notifications.

                AppLog.Log("notification_permission1", "granted")

            } else {
                // TODO: Inform user that your app will not show notifications.
                // You might want to display a UI explaining why notifications are important.

                AppLog.Log("notification_permission2", "not granted")

            }
        }

    private fun askNotificationPermission() {
        // This is only necessary for API level >= 33 (TIRAMISU)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(requireActivity(), Manifest.permission.POST_NOTIFICATIONS) ==
                PackageManager.PERMISSION_GRANTED
            ) {
                // FCM SDK (and your app) can post notifications.

                AppLog.Log("notification_permission3", "granted")

            } else if (shouldShowRequestPermissionRationale(Manifest.permission.POST_NOTIFICATIONS)) {
                // TODO: Display an educational UI explaining to the user the features that will be enabled
                //       by them granting the POST_NOTIFICATION permission. This UI should provide the user
                //       "OK" and "No thanks" buttons. If the user selects "OK," directly request the permission.
                //       If the user selects "No thanks," allow the user to continue without notifications.

                AppLog.Log("notification_permission4", "not granted")

            } else {
                // Directly ask for the permission
                requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)

                AppLog.Log("notification_permission5", "not granted")


            }
        }
    }

}