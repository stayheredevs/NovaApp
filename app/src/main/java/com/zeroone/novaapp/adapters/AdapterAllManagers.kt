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
import com.zeroone.novaapp.databinding.AdapterAllManagersBinding
import com.zeroone.novaapp.responseModels.PropertyManagersModel

class AdapterAllManagers (
    val onManagerClicked: (PropertyManagersModel) -> Unit = {}
):
    ListAdapter<PropertyManagersModel, AdapterAllManagers.MyViewModel>(ManagersDiffCallback()) {

        lateinit var context: Context
        lateinit var binding: AdapterAllManagersBinding

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewModel {
            context = parent.context
            binding = AdapterAllManagersBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            return MyViewModel(binding)
    }

    override fun onBindViewHolder(holder: MyViewModel, position: Int) {
        holder.bind(getItem(position))
    }

    inner class MyViewModel(var binding: AdapterAllManagersBinding): RecyclerView.ViewHolder(binding.root){

        fun bind(propertyManager: PropertyManagersModel){
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
                txtManagerContact.text = propertyManager.propertiesManaging.toString() + root.context.getString(R.string.properties)

                //handling click event
                root.setOnClickListener {
                    onManagerClicked(propertyManager)
                }
            }

        }
    }

    class ManagersDiffCallback : DiffUtil.ItemCallback<PropertyManagersModel>() {
        override fun areItemsTheSame(oldItem: PropertyManagersModel, newItem: PropertyManagersModel): Boolean {
            return oldItem.managerId == newItem.managerId
        }

        override fun areContentsTheSame(oldItem: PropertyManagersModel, newItem: PropertyManagersModel): Boolean {
            return oldItem.managerId == newItem.managerId

        }
    }

}