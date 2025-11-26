package com.zeroone.novaapp.home

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.SpannableString
import android.text.Spanned
import android.text.TextWatcher
import android.text.style.UnderlineSpan
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.zeroone.novaapp.R
import com.zeroone.novaapp.adapters.AdapterAllServices
import com.zeroone.novaapp.adapters.AdapterServiceList
import com.zeroone.novaapp.adapters.AdapterServiceOptions
import com.zeroone.novaapp.databinding.ActivityAllServicesBinding
import com.zeroone.novaapp.databinding.BottomsheetServicesDetailsBinding
import com.zeroone.novaapp.responseModels.BookingDetails
import com.zeroone.novaapp.responseModels.ServiceOptions
import com.zeroone.novaapp.responseModels.ServicesModel
import com.zeroone.novaapp.utilities.AppLog
import com.zeroone.novaapp.utilities.AppUtils
import com.zeroone.novaapp.utilities.EdgeToEdgeManager
import com.zeroone.novaapp.utilities.SharedPreference
import com.zeroone.novaapp.viewModels.ServicesViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class ActivityAllServices: AppCompatActivity() {

    lateinit var binding: ActivityAllServicesBinding
    lateinit var adapterAllServices: AdapterAllServices
    lateinit var servicesViewModel: ServicesViewModel
    private var originalServicesList: MutableList<ServicesModel> = mutableListOf()
    lateinit var adapterServiceOptions: AdapterServiceOptions
    lateinit var adapterServiceList: AdapterServiceList
    var lstServiceOptions: MutableList<ServiceOptions> = mutableListOf()
    var optionName: String = ""
    var optionPrice: String = ""
    var serviceName: String = ""

    @Inject
    lateinit var sharedPreference: SharedPreference



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityAllServicesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Enable edge-to-edge for Android 15+ devices
        EdgeToEdgeManager.handleEdgeToEdge(window, binding.root, R.color.primary_color)

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


        //init service options
        servicesViewModel.serviceOptionsLiveData?.observe(this, Observer { serviceOptions ->

            lstServiceOptions = serviceOptions


        })
        servicesViewModel.processServiceOptions()
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

                serviceName = serviceModel.serviceTypeName.toString()

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

        showBottomSheetDialog(this, lstServiceOptions, serviceName)
    }



    private fun showBottomSheetDialog(context: Context?, serviceOptions: MutableList<ServiceOptions>?, serviceName: String) {
        val binding = BottomsheetServicesDetailsBinding.inflate(LayoutInflater.from(context), null, false)

        val dialog = BottomSheetDialog(context!!, R.style.bottomSheetDialogTheme)
        dialog.setContentView(binding.root)

        binding.txtServiceName.text = serviceName

        val reviews = "12K reviews"
        val spannableString = SpannableString(reviews)
        spannableString.setSpan(UnderlineSpan(), 0, spannableString.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        binding.txtReviews.text = spannableString



        adapterServiceOptions = AdapterServiceOptions(
            onOptionClick = { serviceOption ->

                AppLog.Log("clicked_option", "Option clicked: ${serviceOption.optionName}")


                optionName = serviceOption.optionName.toString()
                optionPrice = serviceOption.optionPrice.toString()

                binding.btnAction.text = "Book (${serviceOption.optionName} - KES ${AppUtils.numberFormatter(serviceOption.optionPrice)})"


            }
        )

        val linearLayoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)

        binding.recyclerViewExtras.apply {
            layoutManager = linearLayoutManager
            hasFixedSize()
            adapter = adapterServiceOptions
            itemAnimator = DefaultItemAnimator()

        }

        // Set the service options in the adapter
        adapterServiceOptions.submitList(serviceOptions)

        //event listener
        binding.btnAction.setOnClickListener {
            if (binding.btnAction.text.toString() == "Continue"){

                AppUtils.ToastMessage("Please select an option", this)

            } else {
                AppUtils.ToastMessage("Option selected!!! ", this)

                val bookingDetails = BookingDetails(
                    "",
                    "",
                    serviceName,
                    optionName,
                    optionPrice,
                    "2 hrs"
                )

                sharedPreference.selectedService = mutableListOf(bookingDetails)
                dialog.dismiss()

                finish()




            }
        }


        dialog.show()
    }

}