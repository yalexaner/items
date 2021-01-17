package yalexaner.items.data

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import yalexaner.items.R
import yalexaner.items.databinding.ItemOrderBinding
import yalexaner.items.db.Order

class OrderListAdapter(private val context: Context) :
    ListAdapter<Order, OrderListAdapter.OrderViewHolder>(Companion) {

    class OrderViewHolder(val binding: ItemOrderBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemOrderBinding.inflate(layoutInflater)

        return OrderViewHolder(binding)
    }

    override fun onBindViewHolder(holder: OrderViewHolder, position: Int) {
        val listItem: Order = getItem(position)
        val orderPercentage: Int =
            (listItem.itemsCollected.toDouble() / listItem.itemsOrdered * 100).toInt()
        val background = when {
            orderPercentage >= 92 -> R.drawable.green_background
            orderPercentage >= 60 -> R.drawable.orange_background
            else -> R.drawable.red_background
        }

        holder.binding.apply {
            order = listItem
            context = this@OrderListAdapter.context
            this.orderPercentage.setBackgroundResource(background)
            this.orderPercentage.text =
                this@OrderListAdapter.context.getString(R.string.order_percentage, orderPercentage)
        }
    }

    companion object : DiffUtil.ItemCallback<Order>() {
        override fun areItemsTheSame(oldItem: Order, newItem: Order) = oldItem == newItem
        override fun areContentsTheSame(oldItem: Order, newItem: Order) =
            oldItem.itemsCollected == newItem.itemsCollected && oldItem.itemsOrdered == newItem.itemsOrdered
    }
}