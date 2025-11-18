package com.zeroone.novaapp.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.zeroone.novaapp.databinding.AdapterPropertiesListBinding
import com.zeroone.novaapp.responseModels.PropertyModel

class AdapterMyProperties(
    val onPropertyClicked: (PropertyModel) -> Unit = {}
): ListAdapter<PropertyModel, AdapterMyProperties.MyViewHolder>(PropertiesDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {

        val binding = AdapterPropertiesListBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: MyViewHolder,
        position: Int
    ) {

        val propertyModel = getItem(position)
        holder.bind(propertyModel)

    }


    inner class MyViewHolder(val binding: AdapterPropertiesListBinding): RecyclerView.ViewHolder(binding.root){

        fun bind(propertyModel: PropertyModel){
            with(binding){
                txtPropertyName.text = propertyModel.propertyName
                txtPropertySize.text = "${propertyModel.bedrooms} bedrooms, (${propertyModel.size})"


                //handling event listener
                binding.root.setOnClickListener {
                    onPropertyClicked(propertyModel)
                }


            }


        }

    }


    class PropertiesDiffCallback : DiffUtil.ItemCallback<PropertyModel>() {
        override fun areItemsTheSame(oldItem: PropertyModel, newItem: PropertyModel): Boolean {
            return oldItem.propertyId == newItem.propertyId
        }

        override fun areContentsTheSame(oldItem: PropertyModel, newItem: PropertyModel): Boolean {
            return oldItem.propertyId == newItem.propertyId

        }
    }


}