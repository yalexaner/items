package yalexaner.items.ui.main

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.ui.platform.setContent
import yalexaner.items.OrdersApplication
import yalexaner.items.data.OrderViewModel
import yalexaner.items.data.OrderViewModelFactory

class MainActivity : AppCompatActivity() {

    private val orderViewModel: OrderViewModel by viewModels {
        OrderViewModelFactory((application as OrdersApplication).repository)
    }

    @ExperimentalMaterialApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MaterialTheme {
                OrdersList(orderViewModel)
            }
        }
    }
}