package com.zeroone.novaapp.home

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.zeroone.novaapp.R
import com.zeroone.novaapp.adapters.ActivityAllocatedProperties
import com.zeroone.novaapp.databinding.ActivityManagerDetailsBinding
import com.zeroone.novaapp.responseModels.PropertyManagersModel
import com.zeroone.novaapp.utils.EdgeToEdgeManager
import com.zeroone.novaapp.viewModels.PropertiesViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class ActivityManagerDetails: AppCompatActivity() {

    lateinit var binding: ActivityManagerDetailsBinding
    lateinit var adapterAllocatedProperties: ActivityAllocatedProperties

    lateinit var propertiesViewModel: PropertiesViewModel



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityManagerDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        propertiesViewModel = ViewModelProvider(this)[PropertiesViewModel::class.java]

        //init
        init()

        //init properties adapter
        initPropertiesAdapter()

        //set listeners
        setListeners()

        // Enable edge-to-edge for Android 15+ devices
        EdgeToEdgeManager.handleEdgeToEdge(window, binding.root, R.color.primary_color)


    }

    fun init(){
        val managerDetails = intent.extras?.getParcelable<PropertyManagersModel>("manager")

        Glide.with(this)
            .load(managerDetails?.profilePicture)
            .apply(
                RequestOptions()
                    .placeholder(R.drawable.user_placeholder)
                    .error(R.drawable.user_placeholder)
            )
            .into(binding.imgProfilePic)

        binding.txtManagerName.text = managerDetails?.managerName
        binding.txtPhoneNumber.text = managerDetails?.phoneNumber


        propertiesViewModel.allocatedPropertiesLiveData?.observe(this) {
                properties->

            adapterAllocatedProperties.submitList(properties)

        }
        propertiesViewModel.processAllocatedProperties()


    }

    fun setListeners(){
        binding.imgBack.setOnClickListener {
            finish()
        }

    }

    fun initPropertiesAdapter(){
        adapterAllocatedProperties = ActivityAllocatedProperties(
            onPropertyClicked = { property ->
                // Handle property click here
            })

        val linearLayoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        binding.recyclerViewProperties.apply {
            layoutManager = linearLayoutManager
            hasFixedSize()
            adapter = adapterAllocatedProperties

        }
    }
}