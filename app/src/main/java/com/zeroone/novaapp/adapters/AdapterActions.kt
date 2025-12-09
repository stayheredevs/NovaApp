package com.zeroone.novaapp.adapters

import android.content.Context
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.zeroone.novaapp.databinding.AdapterActionsBinding
import com.zeroone.novaapp.responseModels.ActionsModel
import com.zeroone.novaapp.responseModels.AssetCategoriesModel

class AdapterActions (
    val onClickListener: (ActionsModel) -> Unit = {}
): ListAdapter<ActionsModel, AdapterActions.MyViewHolder>(ActionsDiffCallBack())  {

    lateinit var context: Context
    lateinit var binding: AdapterActionsBinding

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyViewHolder {
        context = parent.context
        binding = AdapterActionsBinding.inflate(android.view.LayoutInflater.from(parent.context), parent, false)

        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: MyViewHolder,
        position: Int
    ) {
        holder.bind(getItem(position))

    }

    inner class MyViewHolder(val binding: AdapterActionsBinding) : RecyclerView.ViewHolder(binding.root){

        fun bind(actionsModel: ActionsModel){
            with(binding){
                txtAction.text = actionsModel.action


                root.setOnClickListener {
                    onClickListener(actionsModel)
                }
            }

        }


    }


    class ActionsDiffCallBack : DiffUtil.ItemCallback<ActionsModel>() {
        override fun areItemsTheSame(oldItem: ActionsModel, newItem: ActionsModel): Boolean {
            return oldItem.action == newItem.action
        }

        override fun areContentsTheSame(oldItem: ActionsModel, newItem: ActionsModel): Boolean {
            return oldItem.action == newItem.action

        }
    }


}