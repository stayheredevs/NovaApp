package com.zeroone.novaapp.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.zeroone.novaapp.databinding.AdapterPropertySizeBinding
import com.zeroone.novaapp.responseModels.PropertySize

class AdapterPropertySize(val onOptionClick: (PropertySize) -> Unit = {}):
    ListAdapter<PropertySize, AdapterPropertySize.ViewHolder>(PropertySizeDiffUtils()) {

        lateinit var context: Context
        lateinit var binding: AdapterPropertySizeBinding

        /**
         * Keeps single-selection state like a RadioGroup.
         * We track by id (not position) so it survives list re-ordering / diff updates.
         */
        private var selectedSizeId: String? = null

        fun setSelectedSizeId(sizeId: String?) {
            val previousSelectedId = selectedSizeId
            if (previousSelectedId == sizeId) return
            selectedSizeId = sizeId

            // Rebind only affected rows (old + new selection) if they are visible in list.
            val prevIndex = currentList.indexOfFirst { it.sizeId == previousSelectedId }
            if (prevIndex != -1) notifyItemChanged(prevIndex)
            val newIndex = currentList.indexOfFirst { it.sizeId == sizeId }
            if (newIndex != -1) notifyItemChanged(newIndex)
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            context = parent.context
            binding = AdapterPropertySizeBinding.inflate(LayoutInflater.from(context), parent, false)
            return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val propertySize = getItem(position)
        holder.bind(propertySize)
    }

    inner class ViewHolder(val binding: AdapterPropertySizeBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(propertySize: PropertySize) {
            binding.apply {
                textPropertySize.text = propertySize.sizeName
                radioPropertySize.isChecked = (propertySize.sizeId == selectedSizeId)

                val selectThis = {
                    setSelectedSizeId(propertySize.sizeId)
                    onOptionClick(propertySize)
                }

                cardSize.setOnClickListener { selectThis() }
                radioPropertySize.setOnClickListener { selectThis() }
                textPropertySize.setOnClickListener { selectThis() }
            }

        }
    }

    class PropertySizeDiffUtils: DiffUtil.ItemCallback<PropertySize>(){
        override fun areItemsTheSame(
            oldItem: PropertySize,
            newItem: PropertySize
        ): Boolean {
            return oldItem.sizeId == newItem.sizeId
        }

        override fun areContentsTheSame(
            oldItem: PropertySize,
            newItem: PropertySize
        ): Boolean {
            return oldItem.sizeId == newItem.sizeId &&
                oldItem.sizeName == newItem.sizeName
        }

    }
}