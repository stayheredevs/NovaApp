package com.zeroone.novaapp.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.zeroone.novaapp.R
import com.zeroone.novaapp.databinding.AdapterPastJobsBinding
import com.zeroone.novaapp.responseModels.PastJobsModel
import java.text.SimpleDateFormat
import java.util.Locale

class AdapterPastJobs(
    val onItemClick: (PastJobsModel) -> Unit = {}
) : ListAdapter<PastJobsModel, AdapterPastJobs.JobViewHolder>(JobDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): JobViewHolder {
        val binding = AdapterPastJobsBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return JobViewHolder(binding)
    }

    override fun onBindViewHolder(holder: JobViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class JobViewHolder(
        private val binding: AdapterPastJobsBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(job: PastJobsModel) {
            binding.apply {
                txtServiceName.text = job.serviceType ?: "Service"
                txtDate.text = formatDate(job.serviceDate ?: "")

                txtStartTime.text = job.serviceTime ?: ""

                // Set service icon based on type
                imgServiceIcon.setImageResource(getServiceIcon(job.serviceType))

                cardJob.setOnClickListener {
                    onItemClick(job)
                }
            }
        }

        private fun getServiceIcon(serviceType: String?): Int {
            return when (serviceType?.lowercase()) {
                "general cleaning" -> R.drawable.bucket
                "deep cleaning" -> R.drawable.vacuum
                "carpet cleaning" -> R.drawable.power_washing
                "sofa cleaning" -> R.drawable.broom
                "mattress cleaning" -> R.drawable.mattress_cleaner
                "electrical" -> R.drawable.ic_electrical
                "plumbing" -> R.drawable.ic_plumbing
                "furniture" -> R.drawable.ic_furniture
                else -> R.drawable.ic_cleaning
            }
        }


        fun formatDate(date: String): String{

        // Input format
            val inputFormat = SimpleDateFormat("yyyy-dd-MM", Locale.getDefault())

        // Output format (dd MMM yyyy → 12 Mar 2025)
            val outputFormat = SimpleDateFormat("dd MMM yyyy", Locale.getDefault())

            val date = inputFormat.parse(date)
            val formattedDate = outputFormat.format(date)

            return formattedDate

        }
    }

    class JobDiffCallback : DiffUtil.ItemCallback<PastJobsModel>() {
        override fun areItemsTheSame(oldItem: PastJobsModel, newItem: PastJobsModel): Boolean {
            return oldItem.propertyId == newItem.propertyId
        }

        override fun areContentsTheSame(oldItem: PastJobsModel, newItem: PastJobsModel): Boolean {
            return oldItem == newItem
        }
    }
}