package me.rkyb.gprofiler.ui.adapter

import android.view.View
import android.view.ViewGroup
import android.view.LayoutInflater
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import me.rkyb.gprofiler.data.remote.response.ItemsResponse
import me.rkyb.gprofiler.databinding.RvListItemsBinding

class MainRecyclerAdapter (private val listener: Listener)
    : RecyclerView.Adapter<MainRecyclerAdapter.DataViewHolder>() {

    companion object {
        private val diffCallBack =
            object : DiffUtil.ItemCallback<ItemsResponse>(){
                override fun areItemsTheSame(
                    oldItem: ItemsResponse,
                    newItem: ItemsResponse
                ): Boolean {
                    return oldItem.userId == newItem.userId
                }

                override fun areContentsTheSame(
                    oldItem: ItemsResponse,
                    newItem: ItemsResponse
                ): Boolean {
                    return oldItem == newItem
                }
            }
    }

    private val differ = AsyncListDiffer(this, diffCallBack)

    interface Listener {
        fun onItemClick(view: View, data: ItemsResponse)
    }

    inner class DataViewHolder(val binding: RvListItemsBinding)
        : RecyclerView.ViewHolder(binding.root)

    override fun getItemCount(): Int = differ.currentList.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataViewHolder {
        return DataViewHolder(
            RvListItemsBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: DataViewHolder, position: Int) {
        val data = differ.currentList[position]

        holder.apply {
            binding.users = data
            itemView.setOnClickListener {
                listener.onItemClick(it, data)
            }
        }
    }

    fun renderList (newList: MutableList<ItemsResponse>) {
        differ.submitList(newList)
        notifyDataSetChanged()
    }

}