package yalexaner.items.ui.main

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.Navigation
import yalexaner.items.OrdersApplication
import yalexaner.items.R
import yalexaner.items.data.OrderViewModel
import yalexaner.items.data.OrderViewModelFactory
import yalexaner.items.databinding.FragmentItemsBinding
import yalexaner.items.db.Order
import yalexaner.items.other.Constants.NEW_ORDER_ACTIVITY_REQUEST_CODE
import yalexaner.items.ui.neworder.NewOrderActivity
import java.time.OffsetDateTime

class ItemsFragment : Fragment() {

    private val orderViewModel: OrderViewModel by activityViewModels() {
        OrderViewModelFactory((activity?.application as OrdersApplication).repository)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding: FragmentItemsBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_items, container, false)

        binding.run {
            viewModel = orderViewModel
            lifecycleOwner = this@ItemsFragment
        }

        binding.items.setOnClickListener(
            Navigation.createNavigateOnClickListener(R.id.action_itemsFragment_to_ordersListFragment)
        )

        binding.addNewOrder.setOnClickListener {
            val intent = Intent(context, NewOrderActivity::class.java)
            startActivityForResult(intent, NEW_ORDER_ACTIVITY_REQUEST_CODE)
        }

        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == NEW_ORDER_ACTIVITY_REQUEST_CODE) {
            data?.getIntExtra(NewOrderActivity.EXTRA_REPLY, 0)?.let { items ->
                val order = Order(0, OffsetDateTime.now(), items, 8)
                orderViewModel.addItems(order)
            }
        }
    }
}