package com.zeroone.novaapp.home

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.zeroone.novaapp.adapters.AdapterJobStages
import com.zeroone.novaapp.databinding.ActivityActiveJobsBinding
import com.zeroone.novaapp.viewModels.JobsViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ActivityActiveJob: AppCompatActivity() {

    lateinit var binding: ActivityActiveJobsBinding
    lateinit var adapterJobStages: AdapterJobStages
    val jobsViewModel: JobsViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityActiveJobsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //init adapter
        initJobStepsAdapter()

        //init
        init()

        //set listeners
        setListener()


    }

    fun setListener(){
        binding.imgBack.setOnClickListener {
            finish()
        }
    }

    fun init(){

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