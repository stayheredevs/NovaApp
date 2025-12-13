package com.zeroone.novaapp.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.zeroone.novaapp.databinding.AdapterInvoicesBinding
import com.zeroone.novaapp.home.InvoicesModel

class AdapterInvoices(
    val onItemClick: (InvoicesModel) -> Unit = {}
): ListAdapter<InvoicesModel, AdapterInvoices.InvoicesViewHolder>(InvoicesDiffCallback()) {

    lateinit var context: Context


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): InvoicesViewHolder {
        context = parent.context
        val binding = AdapterInvoicesBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return InvoicesViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: InvoicesViewHolder,
        position: Int
    ) {
        holder.bind(getItem(position))
    }

    inner class InvoicesViewHolder(val binding: AdapterInvoicesBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(invoice: InvoicesModel){
            binding.apply {
                txtInvoiceNumber.text = invoice.invoiceNumber
                txtStatus.text = invoice.invoiceStatus


                cardInvoice.setOnClickListener {
                    onItemClick(invoice)
                }

            }
        }

    }

    class InvoicesDiffCallback : DiffUtil.ItemCallback<InvoicesModel>() {
        override fun areItemsTheSame(oldItem: InvoicesModel, newItem: InvoicesModel): Boolean {
            return oldItem.invoiceNumber == newItem.invoiceNumber
        }

        override fun areContentsTheSame(oldItem: InvoicesModel, newItem: InvoicesModel): Boolean {
            return oldItem.invoiceNumber == newItem.invoiceNumber
        }
    }

}