package com.zeroone.novaapp.home

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.SpannableString
import android.text.Spanned
import android.text.style.UnderlineSpan
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.core.text.set
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.zeroone.novaapp.R
import com.zeroone.novaapp.adapters.AdapterServiceList
import com.zeroone.novaapp.adapters.AdapterServiceOptions
import com.zeroone.novaapp.databinding.ActivityBookServiceBinding
import com.zeroone.novaapp.databinding.BottomsheetServicesDetailsBinding
import com.zeroone.novaapp.responseModels.BookingDetails
import com.zeroone.novaapp.responseModels.PropertyModel
import com.zeroone.novaapp.responseModels.ServiceOptions
import com.zeroone.novaapp.utilities.AppLog
import com.zeroone.novaapp.utilities.AppUtils
import com.zeroone.novaapp.utilities.EdgeToEdgeManager
import com.zeroone.novaapp.viewModels.BookingsViewModel
import com.zeroone.novaapp.viewModels.ServicesViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class ActivityBookService: AppCompatActivity() {

    lateinit var binding: ActivityBookServiceBinding
    lateinit var viewModel: BookingsViewModel
    lateinit var adapterServiceList: AdapterServiceList
    lateinit var serviceViewModel: ServicesViewModel
    lateinit var adapterServiceOptions: AdapterServiceOptions

    var serviceName: String = ""
    var propertyName: String = ""
    var propertyAddress: String = ""
    var optionName: String = ""
    var optionPrice: String = ""

    var lstServiceOptions: MutableList<ServiceOptions> = mutableListOf()




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityBookServiceBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Enable edge-to-edge for Android 15+ devices
        EdgeToEdgeManager.handleEdgeToEdge(window, binding.root, R.color.primary_color)

        //init view model
        serviceViewModel = ViewModelProvider(this)[ServicesViewModel::class.java]

        //init service list adapter
        initServiceListAdapter()

        //init
        init()

        //set listener
        setListener()

    }

    fun init(){

        val property: PropertyModel? = intent.extras?.getParcelable("property")

        binding.txtPropertyName.text = property?.propertyName

        //picking property details
        propertyName = property?.propertyName.toString()
        propertyAddress = binding.txtPropertyAddress.text.toString()


        //init services

        serviceViewModel.servicesLiveData?.observe(this, Observer { services ->

            adapterServiceList.submitList(services)

        })
        serviceViewModel.processServices()


        //init service options
        serviceViewModel.serviceOptionsLiveData?.observe(this, Observer { serviceOptions ->

            lstServiceOptions = serviceOptions


        })
        serviceViewModel.processServiceOptions()


    }

    fun setListener(){

        binding.imgBack.setOnClickListener {
            finish()
        }


    }

    fun initServiceListAdapter(){
        adapterServiceList = AdapterServiceList(
            onServiceClicked = {
                serviceModel ->

                serviceName = serviceModel.serviceTypeName.toString()

                showBottomSheetDialog(this, lstServiceOptions, serviceName)

            }
        )

        val linearLayoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        binding.recyclerViewBookings.apply {
            layoutManager = linearLayoutManager
            hasFixedSize()
            adapter = adapterServiceList

        }

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
                    propertyName,
                    propertyAddress.toString(),
                    serviceName,
                    optionName,
                    optionPrice,
                    "2 hrs"
                )

                dialog.dismiss()

                val intent = Intent(this, ActivityCompleteBooking::class.java)
                intent.putExtra("booking_details", bookingDetails)
                startActivity(intent)



            }
        }


        dialog.show()
    }
}