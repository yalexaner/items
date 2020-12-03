package yalexaner.items.ui.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import yalexaner.items.R
import yalexaner.items.db.Order

class OrderListAdapter() : ListAdapter<Order, OrderListAdapter.OrderViewHolder>(ordersComparator) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderViewHolder {
        return OrderViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: OrderViewHolder, position: Int) {
        val current = getItem(position)
        holder.bind(current.items.toString())
    }

    class OrderViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val orderView: TextView = view.findViewById(R.id.order)

        fun bind(items: String) {
            orderView.text = items
        }

        companion object {
            fun create(parent: ViewGroup): OrderViewHolder {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.fragment_order, parent, false)
                return OrderViewHolder(view)
            }
        }
    }

    companion object {
        private val ordersComparator = object : DiffUtil.ItemCallback<Order>() {
            override fun areItemsTheSame(oldItem: Order, newItem: Order): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: Order, newItem: Order): Boolean {
                return oldItem.items == newItem.items
            }
        }
    }
}