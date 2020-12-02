package yalexaner.items.ui

import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import yalexaner.items.OrdersApplication
import yalexaner.items.R
import yalexaner.items.data.OrderViewModel
import yalexaner.items.data.OrderViewModelFactory
import yalexaner.items.databinding.ActivityMainBinding
import yalexaner.items.db.Order
import java.time.OffsetDateTime

class MainActivity : AppCompatActivity() {

    private val newOrderActivityRequestCode = 1
    private val orderViewModel: OrderViewModel by viewModels {
        OrderViewModelFactory((application as OrdersApplication).repository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding: ActivityMainBinding =
            DataBindingUtil.setContentView(this, R.layout.activity_main)

        binding.run {
            viewModel = orderViewModel
            lifecycleOwner = this@MainActivity
        }

        binding.addNewOrder.setOnClickListener {
            val intent = Intent(this@MainActivity, NewOrderActivity::class.java)
            startActivityForResult(intent, newOrderActivityRequestCode)
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == newOrderActivityRequestCode) {
            data?.getIntExtra(NewOrderActivity.EXTRA_REPLY, 0)?.let { items ->
                val order = Order(0, OffsetDateTime.now(), items, 8)
                orderViewModel.addItems(order)
            }
        }
    }
}