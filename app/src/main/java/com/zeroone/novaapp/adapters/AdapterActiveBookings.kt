package com.zeroone.novaapp.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.zeroone.novaapp.R
import com.zeroone.novaapp.databinding.AdapterActiveBookingsBinding
import com.zeroone.novaapp.responseModels.ActiveBookingsModel

class AdapterActiveBookings(
    val onItemClick: (ActiveBookingsModel) -> Unit = {}
) : ListAdapter<ActiveBookingsModel, AdapterActiveBookings.BookingViewHolder>(VideoDiffCallback()){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookingViewHolder {
        val binding = AdapterActiveBookingsBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return BookingViewHolder(binding)
    }

    override fun onBindViewHolder(holder: BookingViewHolder, position: Int) {
        holder.bind(getItem(position), position)
    }

    inner class BookingViewHolder(
        private val binding: AdapterActiveBookingsBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(booking: ActiveBookingsModel, position: Int) {
            // Set property name
            binding.txtPropertyName.text = booking.propertyName

            // Set service type with icon
            binding.textServiceType.text = booking.serviceType
            binding.imgServiceTypeIcon.setImageResource(getServiceTypeIcon(booking.serviceType))

            // Set main service icon
            //binding.imgServiceNme.setImageResource(getServiceIcon(booking.serviceCategory))

            // Set date/time if available
            booking.timestamp?.let {
                binding.txtDateTime.text = it
                binding.txtDateTime.visibility = View.VISIBLE
            } ?: run {
                binding.txtDateTime.visibility = View.GONE
            }

            // Set job status badge
            setStatusBadge(booking.status!!)

            // Set click listener
            binding.root.setOnClickListener {
                onItemClick(booking)
            }
        }

        private fun setStatusBadge(status: String) {
            val context = binding.root.context
            when (status) {
                "ACCEPTED" -> {
                    binding.statusBadge.text = context.getString(R.string.status_accepted)
                    binding.statusBadge.setBackgroundResource(R.drawable.status_badge_accepted)
                }
                "STARTED" -> {
                    binding.statusBadge.text = context.getString(R.string.status_started)
                    binding.statusBadge.setBackgroundResource(R.drawable.status_badge_started)
                }
                "ENDED" -> {
                    binding.statusBadge.text = context.getString(R.string.status_ended)
                    binding.statusBadge.setBackgroundResource(R.drawable.status_badge_ended)
                }
                "RATED" -> {
                    binding.statusBadge.text = context.getString(R.string.status_rated)
                    binding.statusBadge.setBackgroundResource(R.drawable.status_badge_rated)
                }
            }
        }

        private fun getServiceTypeIcon(serviceType: String?): Int {
            return when (serviceType?.lowercase()) {
                "general cleaning" -> R.drawable.ic_general_cleaning
                "deep cleaning" -> R.drawable.ic_deep_cleaning
                "carpet cleaning" -> R.drawable.ic_carpet_cleaning
                "sofa set cleaning" -> R.drawable.ic_sofa_cleaning
                "mattress cleaning" -> R.drawable.ic_mattress_cleaning
                else -> R.drawable.ic_general_cleaning
            }
        }

        private fun getServiceIcon(serviceCategory: String): Int {
            return when (serviceCategory.lowercase()) {
                "cleaning" -> R.drawable.ic_cleaning
                "electrical" -> R.drawable.ic_electrical
                "plumbing" -> R.drawable.ic_plumbing
                "furniture" -> R.drawable.ic_furniture
                else -> R.drawable.ic_cleaning
            }
        }
    }

    /**
     * DiffUtil callback for efficient list updates
     */
    private class VideoDiffCallback : DiffUtil.ItemCallback<ActiveBookingsModel>() {
        override fun areItemsTheSame(
            oldItem: ActiveBookingsModel,
            newItem: ActiveBookingsModel
        ): Boolean {
            return oldItem.propertyId == newItem.propertyId
        }

        override fun areContentsTheSame(
            oldItem: ActiveBookingsModel,
            newItem: ActiveBookingsModel
        ): Boolean {
            return oldItem == newItem
        }
    }
}

// Data classes for booking items
data class BookingItem(
    val id: String,
    val propertyName: String,
    val serviceCategory: String, // "Cleaning", "Electrical", "Plumbing", "Furniture"
    val serviceType: String, // "General Cleaning", "Deep Cleaning", etc.
    val jobStatus: JobStatus,
    val dateTime: String? = null
)

enum class JobStatus {
    ACCEPTED,
    STARTED,
    ENDED,
    RATED
}