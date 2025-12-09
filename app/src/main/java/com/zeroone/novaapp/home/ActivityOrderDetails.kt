package com.zeroone.novaapp.home

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.zeroone.novaapp.adapters.AdapterJobStages
import com.zeroone.novaapp.databinding.ActivityOrderDetailsBinding
import com.zeroone.novaapp.responseModels.PastJobsModel
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