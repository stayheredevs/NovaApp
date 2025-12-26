package com.zeroone.novaapp.home

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.zeroone.novaapp.R
import com.zeroone.novaapp.adapters.AdapterJobStages
import com.zeroone.novaapp.databinding.ActivityActiveJobsBinding
import com.zeroone.novaapp.utilities.AppLog
import com.zeroone.novaapp.viewModels.JobsViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ActivityActiveJob: AppCompatActivity() {

    lateinit var binding: ActivityActiveJobsBinding
    lateinit var adapterJobStages: AdapterJobStages
    val jobsViewModel: JobsViewModel by viewModels()

    private val REQUEST_CALL_PHONE = 1
    var phoneNumber = ""

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

    fun setListener() {
        binding.imgBack.setOnClickListener {
            finish()
        }

        binding.cardCallButton.setOnClickListener {
            makeDirectCall(phoneNumber)
        }
    }

    fun init() {

        jobsViewModel.activeJobLiveData?.observe(this, Observer { activeJob ->

            //Agent details
            Glide.with(this)
                .load(activeJob[0].assignedAgent?.profilePicUrl)
                .apply(
                    RequestOptions()
                        .placeholder(R.drawable.user_placeholder)
                        .error(R.drawable.user_placeholder)
                )
                .into(binding.imgProfilePic)

            binding.txtAgentName.text = activeJob[0].assignedAgent?.name
            binding.txtCompletedJobs.text = activeJob[0].assignedAgent?.jobsCompleted.toString()
            binding.txtRating.text = activeJob[0].assignedAgent?.rating.toString()

            //Direct call
            phoneNumber = activeJob[0].assignedAgent?.phoneNumber.toString()


            //Job details
            binding.txtServiceName.text = activeJob[1].jobDetails?.serviceName
            binding.txtDate.text = activeJob[1].jobDetails?.date
            binding.txtStartTime.text = activeJob[1].jobDetails?.time?.split(" - ")?.get(0)
            binding.txtEndTime.text = activeJob[1].jobDetails?.time?.split(" - ")?.get(1)


            //Job stages
            adapterJobStages.submitList(activeJob[2].jobStages)

            AppLog.Log("service_name", activeJob[1].jobDetails?.serviceName)

            when (activeJob[1].jobDetails?.serviceName?.lowercase()) {
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

        })
        jobsViewModel.processActiveJob()

        /*jobsViewModel.activeJobLiveData?.observe(this, Observer { stages ->
            adapterJobStages.submitList(stages?.get(2)?.jobStages)
        })*/

        /*jobsViewModel.jobStageLiveData?.observe(this, Observer { stages ->
            adapterJobStages.submitList(stages)
        })
        jobsViewModel.processJobStages()*/
    }

    fun initJobStepsAdapter() {
        adapterJobStages = AdapterJobStages()

        val linearLayoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

        binding.recyclerViewStages.apply {
            layoutManager = linearLayoutManager
            hasFixedSize()
            adapter = adapterJobStages
        }
    }

    fun makeDirectCall(phoneNumber: String) {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE)
            == PackageManager.PERMISSION_GRANTED
        ) {

            val intent = Intent(Intent.ACTION_DIAL).apply {
                data = Uri.parse("tel:$phoneNumber")
            }
            startActivity(intent)
        } else {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.CALL_PHONE),
                REQUEST_CALL_PHONE
            )
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_CALL_PHONE && grantResults.isNotEmpty()
            && grantResults[0] == PackageManager.PERMISSION_GRANTED
        ) {
            // Permission granted, make the call
            makeDirectCall(phoneNumber)
        }
    }
}