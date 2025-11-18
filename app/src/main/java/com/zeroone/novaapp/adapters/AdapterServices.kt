package com.zeroone.novaapp.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.zeroone.novaapp.R
import com.zeroone.novaapp.databinding.AdapterServicesBinding
import com.zeroone.novaapp.responseModels.ServicesModel

class AdapterServices(
    val onItemClick: (ServicesModel) -> Unit = {}

) : ListAdapter<ServicesModel, AdapterServices.MyViewHolder>(ServicesDiffCallback()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {

        val binding = AdapterServicesBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return MyViewHolder(binding)

    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        val serviceModel = getItem(position)
        holder.bind(serviceModel)

    }

    inner class MyViewHolder(val binding: AdapterServicesBinding): RecyclerView.ViewHolder(binding.root){

        fun bind(serviceModel: ServicesModel){
            with(binding){
                txtServiceName.text = serviceModel.serviceTypeName


                when(serviceModel.serviceTypeId?.lowercase()) {
                    "1" -> {
                        Glide.with(binding.root.context)
                            .load(serviceModel.iconUrl)
                            .apply(
                                RequestOptions()
                                    .placeholder(R.drawable.bucket)
                                    .error(R.drawable.bucket)
                            )
                            .into(imgServiceIcon)


                    }

                    "2"->{
                        Glide.with(binding.root.context)
                            .load(serviceModel.iconUrl)
                            .apply(
                                RequestOptions()
                                    .placeholder(R.drawable.vacuum)
                                    .error(R.drawable.vacuum)
                            )
                            .into(imgServiceIcon)
                    }

                    "3" ->{
                        Glide.with(binding.root.context)
                            .load(serviceModel.iconUrl)
                            .apply(
                                RequestOptions()
                                    .placeholder(R.drawable.sofa_cleaning)
                                    .error(R.drawable.sofa_cleaning)
                            )
                            .into(imgServiceIcon)
                    }

                    "4" ->{
                        Glide.with(binding.root.context)
                            .load(serviceModel.iconUrl)
                            .apply(
                                RequestOptions()
                                    .placeholder(R.drawable.power_washing)
                                    .error(R.drawable.power_washing)
                            )
                            .into(imgServiceIcon)
                    }

                    "5"->{
                        Glide.with(binding.root.context)
                            .load(serviceModel.iconUrl)
                            .apply(
                                RequestOptions()
                                    .placeholder(R.drawable.mattress_cleaner)
                                    .error(R.drawable.mattress_cleaner)
                            )
                            .into(imgServiceIcon)
                    }


                    "6"->{
                        Glide.with(binding.root.context)
                            .load(serviceModel.iconUrl)
                            .apply(
                                RequestOptions()
                                    .placeholder(R.drawable.broom)
                                    .error(R.drawable.broom)
                            )
                            .into(imgServiceIcon)
                    }

                    else -> {
                        Glide.with(binding.root.context)
                            .load(serviceModel.iconUrl)
                            .apply(
                                RequestOptions()
                                    .placeholder(R.drawable.broom)
                                    .error(R.drawable.broom)
                            )
                            .into(imgServiceIcon)
                    }
                }




                //event listener
                binding.root.setOnClickListener {
                    onItemClick(serviceModel)
                }

            }

        }

    }

    class ServicesDiffCallback : DiffUtil.ItemCallback<ServicesModel>() {
        override fun areItemsTheSame(oldItem: ServicesModel, newItem: ServicesModel): Boolean {
            return oldItem.serviceTypeId == newItem.serviceTypeId
        }

        override fun areContentsTheSame(oldItem: ServicesModel, newItem: ServicesModel): Boolean {
            return oldItem.serviceTypeId == newItem.serviceTypeId

        }
    }


}