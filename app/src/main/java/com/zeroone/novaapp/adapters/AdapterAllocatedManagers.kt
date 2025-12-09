package com.zeroone.novaapp.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.zeroone.novaapp.R
import com.zeroone.novaapp.databinding.AdapterAllocatedManagersBinding
import com.zeroone.novaapp.responseModels.PropertyManagersModel
import com.zeroone.novaapp.utilities.AppLog
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class AdapterAllocatedManagers(
    val onClickListener: (PropertyManagersModel) -> Unit

): ListAdapter<PropertyManagersModel, AdapterAllocatedManagers.MyViewHolder>(
    ManagerDiffCallBack()
) {

    lateinit var context: Context
    lateinit var binding: AdapterAllocatedManagersBinding


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        context = parent.context

        binding = AdapterAllocatedManagersBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return MyViewHolder(binding)


    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val propertyManager = getItem(position)
        holder.bing(propertyManager)
    }

    inner class MyViewHolder (var binding: AdapterAllocatedManagersBinding): RecyclerView.ViewHolder(binding.root){
        fun bing(propertyManager: PropertyManagersModel){
            with(binding){

                Glide.with(root.context)
                    .load(propertyManager.profilePicture)
                    .apply(
                        RequestOptions()
                            .placeholder(R.drawable.user_placeholder)
                            .error(R.drawable.user_placeholder)
                    )
                    .into(imgProfilePicture)

                txtManagerName.text = propertyManager.managerName


                txtAllocationDate.text = convertDate(propertyManager.allocationDate)


                AppLog.Log("status_", "Manager Status: ${propertyManager.status}")

                txtStatus.text = propertyManager.status

                when(propertyManager.status){
                    "Active" -> {
                        txtStatus.setBackgroundResource(R.drawable.status_badge_active)
                    }
                    "Suspended" -> {
                        txtStatus.setBackgroundResource(R.drawable.status_badge_suspended)
                    }
                    else -> {
                        txtStatus.setBackgroundResource(R.drawable.status_badge_active)
                    }

                }


                root.setOnClickListener {
                    onClickListener(propertyManager)
                }
            }

            }
        }

    class ManagerDiffCallBack: DiffUtil.ItemCallback<PropertyManagersModel>(){
        override fun areItemsTheSame(oldItem: PropertyManagersModel, newItem: PropertyManagersModel): Boolean {
            return  oldItem.managerId == newItem.managerId

        }

        override fun areContentsTheSame(oldItem: PropertyManagersModel, newItem: PropertyManagersModel): Boolean {
            return oldItem.managerId == newItem.managerId

        }


    }

    fun convertDate(dateString: String?): String{
        val inputFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val date = inputFormat.parse(dateString!!)

        // Change year using Calendar
        val calendar = Calendar.getInstance()
        calendar.time = date!!
        calendar.set(Calendar.YEAR, 2025)

        val outputFormat = SimpleDateFormat("dd MMM yyyy", Locale.getDefault())
        val newDate = outputFormat.format(calendar.time)

        return newDate;

    }

}




