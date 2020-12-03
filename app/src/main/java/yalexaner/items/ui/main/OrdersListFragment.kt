package yalexaner.items.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import yalexaner.items.OrdersApplication
import yalexaner.items.R
import yalexaner.items.data.OrderViewModel
import yalexaner.items.data.OrderViewModelFactory
import yalexaner.items.databinding.FragmentOrdersListBinding

class OrdersListFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        val binding: FragmentOrdersListBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_orders_list, container, false)
        val orderViewModel: OrderViewModel by activityViewModels() {
            OrderViewModelFactory((activity?.application as OrdersApplication).repository)
        }
        val orderAdapter = OrderListAdapter()

        with(binding.ordersList) {
            layoutManager = LinearLayoutManager(context)
            adapter = orderAdapter
        }

        orderViewModel.orders.observe(viewLifecycleOwner) { orders ->
            orderAdapter.submitList(orders)
        }

        return binding.root
    }
}