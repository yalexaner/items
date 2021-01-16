package yalexaner.items.data

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import yalexaner.items.databinding.ItemOrderBinding
import yalexaner.items.db.Order

class OrderListAdapter : ListAdapter<Order, OrderListAdapter.OrderViewHolder>(Companion) {

    class OrderViewHolder(val binding: ItemOrderBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemOrderBinding.inflate(layoutInflater)

        return OrderViewHolder(binding)
    }

    override fun onBindViewHolder(holder: OrderViewHolder, position: Int) {
        holder.binding.order = getItem(position)
    }

    companion object : DiffUtil.ItemCallback<Order>() {
        override fun areItemsTheSame(oldItem: Order, newItem: Order) = oldItem == newItem
        override fun areContentsTheSame(oldItem: Order, newItem: Order) =
            oldItem.itemsCollected == newItem.itemsCollected && oldItem.itemsOrdered == newItem.itemsOrdered
    }
}