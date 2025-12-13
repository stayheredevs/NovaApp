package com.zeroone.novaapp.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.zeroone.novaapp.R
import com.zeroone.novaapp.databinding.AdapterJobsStageBinding
import com.zeroone.novaapp.responseModels.JobsStagesModel

class AdapterJobStages (
    val onStageClick: (JobsStagesModel) -> Unit = {}):
    ListAdapter<JobsStagesModel, AdapterJobStages.JobStepsViewHolder>(JobStepsDiffCallback()) {

        lateinit var context: Context
        lateinit var binding: AdapterJobsStageBinding


        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): JobStepsViewHolder {
            context = parent.context

            binding = AdapterJobsStageBinding.inflate(LayoutInflater.from(context), parent, false)
            return JobStepsViewHolder(binding)

    }

    override fun onBindViewHolder(holder: JobStepsViewHolder, position: Int) {
        holder.bind(getItem(position))

        val isLast = position == itemCount - 1
        if (isLast) {
            holder.binding.view.visibility = View.GONE
        }
    }

    inner class JobStepsViewHolder(var binding: AdapterJobsStageBinding): RecyclerView.ViewHolder(binding.root){

        fun bind(jobStage: JobsStagesModel){
            binding.apply {

                txtStage.text = jobStage.stageName
                txtActionDescription.text = jobStage.stageDescription

                root.setOnClickListener {
                    onStageClick(jobStage)
                }

                when(jobStage.stageStatus){

                    "passed"->{
                        imgStage.setImageResource(R.drawable.icon_checkmark_filled)
                        view.setBackgroundColor(context.resources.getColor(R.color.primary_color))
                        txtStage.setTextColor(context.resources.getColor(R.color.primary_color))

                    }
                    "active"->{
                        imgStage.setImageResource(R.drawable.icon_green_dot)
                        view.setBackgroundColor(context.resources.getColor(R.color.success_color))
                        txtStage.setTextColor(context.resources.getColor(R.color.success_color))

                    }
                    "pending"->{
                        llDescription.visibility = View.GONE
                        //imgStage.setImageResource(R.drawable.icon_circle_stage)

                    }

                }



            }
        }
    }

    class JobStepsDiffCallback: DiffUtil.ItemCallback<JobsStagesModel>(){
        override fun areItemsTheSame(oldItem: JobsStagesModel, newItem: JobsStagesModel): Boolean {
            return oldItem.stageID == newItem.stageID
        }

        override fun areContentsTheSame(
            oldItem: JobsStagesModel, newItem: JobsStagesModel): Boolean {
            return oldItem.stageName == newItem.stageName

        }

    }
}