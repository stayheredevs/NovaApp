package com.zeroone.novaapp.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.zeroone.novaapp.databinding.AdapterPropertyTypesBinding
import com.zeroone.novaapp.responseModels.PropertyTypes

class AdapterPropertyType(
    val onPropertyTypeClicked: (PropertyTypes) -> Unit) :
    ListAdapter<PropertyTypes, AdapterPropertyType.ViewHolder>(PropertyTypesDiffCallBack()) {

    lateinit var context: Context
    lateinit var binding: AdapterPropertyTypesBinding

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        context = parent.context

        binding = AdapterPropertyTypesBinding.inflate(LayoutInflater.from(context), parent, false)

        return ViewHolder(binding)

    }

    override fun onBindViewHolder(
        holder: ViewHolder,
        position: Int
    ) {
        val propertyType = getItem(position)
        holder.bind(propertyType)

    }

    inner class ViewHolder(val binding: AdapterPropertyTypesBinding) : RecyclerView.ViewHolder(binding.root){

        fun bind(propertyType: PropertyTypes){
            binding.apply {
                txtApartment.text = propertyType.propertyTypeName

                //handle click
                binding.cardApartment.setOnClickListener {
                    onPropertyTypeClicked(propertyType)
                }

            }
        }

    }

    class PropertyTypesDiffCallBack: DiffUtil.ItemCallback<PropertyTypes>(){
        override fun areItemsTheSame(
            oldItem: PropertyTypes,
            newItem: PropertyTypes
        ): Boolean {
            return oldItem.propertyTypeId == newItem.propertyTypeId
        }

        override fun areContentsTheSame(
            oldItem: PropertyTypes,
            newItem: PropertyTypes
        ): Boolean {
            return oldItem.propertyTypeId == newItem.propertyTypeId
        }

    }
}