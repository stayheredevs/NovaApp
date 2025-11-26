package com.zeroone.novaapp.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.zeroone.novaapp.R
import com.zeroone.novaapp.databinding.AdapterServiceOptionsBinding
import com.zeroone.novaapp.responseModels.ServiceOptions
import com.zeroone.novaapp.utilities.AppUtils

class AdapterServiceOptions(
    val onOptionClick: (ServiceOptions) -> Unit = {}
): ListAdapter<ServiceOptions, AdapterServiceOptions.MyViewHolder>(OptionsDiffCallback()) {

    private var selectedPosition: Int? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = AdapterServiceOptionsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val serviceOption = getItem(position)
        holder.bind(serviceOption, position == selectedPosition)
    }


    inner class MyViewHolder(val binding: AdapterServiceOptionsBinding): RecyclerView.ViewHolder(binding.root){

        fun bind(serviceOption: ServiceOptions, isSelected: Boolean){
            with(binding){
                txtOptionName.text = serviceOption.optionName
                txtOptionPrice.text = "KES ${AppUtils.numberFormatter(serviceOption.optionPrice)}"

                // Apply background based on selection state
                val innerLayout = root.getChildAt(0) as? android.view.ViewGroup
                innerLayout?.background = if (isSelected) {
                    ResourcesCompat.getDrawable(root.resources, R.drawable.circular_edges_layout_highlight, null)
                } else {
                    ResourcesCompat.getDrawable(root.resources, R.drawable.circular_edges_layout, null)

                }

                // Apply text color based on selection state
                val selectedTextColor = ResourcesCompat.getColor(root.resources, R.color.primary_color, null)
                val unselectedTextColor = ResourcesCompat.getColor(root.resources, R.color.black, null)
                
                txtOptionName.setTextColor(if (isSelected) selectedTextColor else unselectedTextColor)
                txtOptionPrice.setTextColor(if (isSelected) selectedTextColor else unselectedTextColor)

                //handling event listener
                root.setOnClickListener {
                    if (adapterPosition == RecyclerView.NO_POSITION) return@setOnClickListener
                    
                    val previousSelected = selectedPosition
                    selectedPosition = adapterPosition
                    
                    // Notify previous item to remove highlight
                    previousSelected?.let { 
                        if (it != adapterPosition) {
                            notifyItemChanged(it)
                        }
                    }
                    // Notify current item to add highlight
                    notifyItemChanged(adapterPosition)
                    
                    onOptionClick(serviceOption)
                }
            }

        }

    }

    class OptionsDiffCallback : DiffUtil.ItemCallback<ServiceOptions>() {
        override fun areItemsTheSame(oldItem: ServiceOptions, newItem: ServiceOptions): Boolean {
            return oldItem.optionName == newItem.optionName
        }

        override fun areContentsTheSame(oldItem: ServiceOptions, newItem: ServiceOptions): Boolean {
            return oldItem.optionName == newItem.optionName

        }
    }

}