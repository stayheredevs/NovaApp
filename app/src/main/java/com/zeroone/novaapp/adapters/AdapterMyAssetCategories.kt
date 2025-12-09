package com.zeroone.novaapp.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.zeroone.novaapp.R
import com.zeroone.novaapp.databinding.AdapterAssetsCategoryBinding
import com.zeroone.novaapp.responseModels.AssetCategoriesModel
import com.zeroone.novaapp.responseModels.ServicesModel
import com.zeroone.novaapp.utilities.AppLog

class AdapterMyAssetCategories (
    val onCategoryClicked: (AssetCategoriesModel) -> Unit = {}
): ListAdapter<AssetCategoriesModel, AdapterMyAssetCategories.MyViewHolder>(CategoriesDiffCallback()) {

    // Track the currently selected category position - default to first item (position 0)
    private var selectedPosition = RecyclerView.NO_POSITION

    // Track if default selection has been initialized
    private var isInitialSelectionDone = false

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = AdapterAssetsCategoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(getItem(position), position)
    }

    override fun submitList(list: MutableList<AssetCategoriesModel>?) {
        val previousListSize = currentList.size
        super.submitList(list)
        // Ensure first item is selected by default only on first load
        if (list != null && list.isNotEmpty() && !isInitialSelectionDone) {
            selectedPosition = 0
            isInitialSelectionDone = true
            // Notify that position 0 has changed to apply selected styling
            notifyItemChanged(0)
            // Trigger callback for the first category to notify parent
            onCategoryClicked(list[0])
        } else if (list != null && list.isNotEmpty() && previousListSize == 0) {
            // If list was empty before and now has items, select first
            selectedPosition = 0
            notifyItemChanged(0)
            onCategoryClicked(list[0])
        }
    }

    inner class MyViewHolder(var binding: AdapterAssetsCategoryBinding) : RecyclerView.ViewHolder(binding.root){

        fun bind(category: AssetCategoriesModel, position: Int){
            with(binding){
                txtCategory.text = category.categoryName

                // Update background color based on selection state
                val context = root.context
                if (position == selectedPosition) {
                    // Selected: Use primary color background
                    subtypeCard.setCardBackgroundColor(
                        ContextCompat.getColor(context, R.color.primary_color)
                    )
                    txtCategory.setTextColor(
                        ContextCompat.getColor(context, R.color.white)
                    )
                } else {
                    // Not selected: Use white background (default)
                    subtypeCard.setCardBackgroundColor(
                        ContextCompat.getColor(context, R.color.white)
                    )
                    txtCategory.setTextColor(
                        ContextCompat.getColor(context, R.color.text_primary)
                    )
                }

                // Set click listener on the card view instead of root
                // The MaterialCardView has clickable="true" which intercepts clicks
                subtypeCard.setOnClickListener {
                    // Use adapterPosition to handle RecyclerView recycling properly
                    val clickedPosition = adapterPosition
                    if (clickedPosition == RecyclerView.NO_POSITION) return@setOnClickListener

                    // Update selected position
                    val previousSelected = selectedPosition
                    selectedPosition = clickedPosition
                    
                    // Notify adapter of changes to update UI
                    if (previousSelected != RecyclerView.NO_POSITION && previousSelected != clickedPosition) {
                        notifyItemChanged(previousSelected)

                    }

                    notifyItemChanged(selectedPosition)
                    
                    // Call the click callback with the actual item
                    onCategoryClicked(getItem(clickedPosition))
                }
            }
        }
    }

    class CategoriesDiffCallback : DiffUtil.ItemCallback<AssetCategoriesModel>() {
        override fun areItemsTheSame(oldItem: AssetCategoriesModel, newItem: AssetCategoriesModel): Boolean {
            return oldItem.categoryID == newItem.categoryID
        }

        override fun areContentsTheSame(oldItem: AssetCategoriesModel, newItem: AssetCategoriesModel): Boolean {
            return oldItem.categoryID == newItem.categoryID

        }
    }
}