package com.zeroone.novaapp.adapters

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.zeroone.novaapp.databinding.AdapterAssetsCategoryBinding
import com.zeroone.novaapp.responseModels.ServicesModel

class AdapterMyAssetCategories: ListAdapter<ServicesModel, AdapterMyAssetCategories.MyViewHolder>(CategoriesDiffCallback()) {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyViewHolder {
        TODO("Not yet implemented")
    }

    override fun onBindViewHolder(
        holder: MyViewHolder,
        position: Int
    ) {
        TODO("Not yet implemented")
    }

    inner class MyViewHolder(var binding: AdapterAssetsCategoryBinding) : RecyclerView.ViewHolder(binding.root){

        fun bind(category: String){
            with(binding){

                txtCategory.text = category

            }

        }

    }

    class CategoriesDiffCallback : DiffUtil.ItemCallback<ServicesModel>() {
        override fun areItemsTheSame(oldItem: ServicesModel, newItem: ServicesModel): Boolean {
            return oldItem.serviceTypeId == newItem.serviceTypeId
        }

        override fun areContentsTheSame(oldItem: ServicesModel, newItem: ServicesModel): Boolean {
            return oldItem.serviceTypeId == newItem.serviceTypeId

        }
    }
}