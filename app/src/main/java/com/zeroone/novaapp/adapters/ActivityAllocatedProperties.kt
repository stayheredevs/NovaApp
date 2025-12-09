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
import com.zeroone.novaapp.databinding.AdapterAllocatedPropertiesBinding
import com.zeroone.novaapp.responseModels.AllocatedPropertiesModel
import com.zeroone.novaapp.responseModels.ServicesModel

class ActivityAllocatedProperties(
    val onPropertyClicked: (AllocatedPropertiesModel) -> Unit
): ListAdapter<AllocatedPropertiesModel, ActivityAllocatedProperties.MyViewModel>(PropertiesDiffCallback()) {

    lateinit var binding: AdapterAllocatedPropertiesBinding
    lateinit var context: Context

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyViewModel {
        context = parent.context
        binding = AdapterAllocatedPropertiesBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewModel(binding)
    }

    override fun onBindViewHolder(
        holder: MyViewModel,
        position: Int
    ) {
        val allocatedPropertiesModel = getItem(position)
        holder.bind(allocatedPropertiesModel)

    }

    inner class MyViewModel(var binding: AdapterAllocatedPropertiesBinding): RecyclerView.ViewHolder(binding.root){

        fun bind(allocatedPropertiesModel: AllocatedPropertiesModel){
            with(binding){

                Glide.with(root.context)
                    .load(allocatedPropertiesModel.propertyPicture)
                    .apply(
                        RequestOptions()
                            .placeholder(R.drawable.user_placeholder)
                            .error(R.drawable.user_placeholder)
                    )
                    .into(binding.imgProperty)

                txtPropertyName.text = allocatedPropertiesModel.propertyName
                txtPropertyAddress.text = allocatedPropertiesModel.propertyAddress

                root.setOnClickListener {
                    onPropertyClicked(allocatedPropertiesModel)
                }
            }


            }
        }

    }

    class PropertiesDiffCallback : DiffUtil.ItemCallback<AllocatedPropertiesModel>() {
        override fun areItemsTheSame(oldItem: AllocatedPropertiesModel, newItem: AllocatedPropertiesModel): Boolean {
            return oldItem.propertyName == newItem.propertyName
        }

        override fun areContentsTheSame(oldItem: AllocatedPropertiesModel, newItem: AllocatedPropertiesModel): Boolean {
            return oldItem.propertyName == newItem.propertyName

        }

}