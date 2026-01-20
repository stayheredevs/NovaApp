package com.zeroone.novaapp.home

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.zeroone.novaapp.R
import com.zeroone.novaapp.adapters.AdapterJobStages
import com.zeroone.novaapp.databinding.ActivityOrderDetailsBinding
import com.zeroone.novaapp.responseModels.PastJobsModel
import com.zeroone.novaapp.utils.AppLog
import com.zeroone.novaapp.utils.EdgeToEdgeManager
import com.zeroone.novaapp.viewModels.JobsViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class ActivityOrderDetails: AppCompatActivity() {

    lateinit var binding: ActivityOrderDetailsBinding
    lateinit var adapterJobStages: AdapterJobStages
    lateinit var jobsViewModel: JobsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityOrderDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //init view model
        jobsViewModel = ViewModelProvider(this)[JobsViewModel::class.java]

        //init adapter
        initJobStepsAdapter()

        //init
        init()

        //Set Listeners
        setListeners()

        // Enable edge-to-edge for Android 15+ devices
        EdgeToEdgeManager.handleEdgeToEdge(window, binding.root, R.color.primary_color)

    }

    fun setListeners(){
        binding.imgBack.setOnClickListener {
            finish()
        }
    }

    fun init(){
        val jobDetails: PastJobsModel? = intent.extras?.getParcelable("job")

        binding.txtServiceName.text = jobDetails?.serviceType
        binding.txtDate.text = jobDetails?.serviceDate
        binding.txtStartTime.text = jobDetails?.serviceTime

        AppLog.Log("service_type", jobDetails?.serviceType?.lowercase())

        when (jobDetails?.serviceType?.lowercase()) {
            "general cleaning" -> binding.imgServiceIcon.setImageResource(R.drawable.bucket)
            "deep cleaning" -> binding.imgServiceIcon.setImageResource(R.drawable.vacuum)
            "carpet cleaning" -> binding.imgServiceIcon.setImageResource(R.drawable.power_washing)
            "sofa cleaning" -> binding.imgServiceIcon.setImageResource(R.drawable.broom)
            "mattress cleaning" -> binding.imgServiceIcon.setImageResource(R.drawable.mattress_cleaner)
            "electrical" -> binding.imgServiceIcon.setImageResource(R.drawable.ic_electrical)
            "plumbing" -> binding.imgServiceIcon.setImageResource(R.drawable.ic_plumbing)
            "furniture" -> binding.imgServiceIcon.setImageResource(R.drawable.ic_furniture)
            else -> binding.imgServiceIcon.setImageResource(R.drawable.ic_cleaning)
        }


        jobsViewModel.jobStageLiveData?.observe(this, Observer {
            stages->
            adapterJobStages.submitList(stages)
        })
        jobsViewModel.processJobStages()


    }

    fun initJobStepsAdapter(){
        adapterJobStages = AdapterJobStages()

        val linearLayoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

        binding.recyclerViewStages.apply {
            layoutManager = linearLayoutManager
            hasFixedSize()
            adapter = adapterJobStages
        }
    }
}