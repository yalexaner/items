package yalexaner.items.ui.main

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.setContent
import androidx.compose.ui.tooling.preview.Preview
import yalexaner.items.OrdersApplication
import yalexaner.items.data.OrderViewModel
import yalexaner.items.data.OrderViewModelFactory

class MainActivity : AppCompatActivity() {

    private val orderViewModel: OrderViewModel by viewModels {
        OrderViewModelFactory((application as OrdersApplication).repository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            ItemsMainScreen(orderViewModel)
        }
    }
}

@Composable
fun App() {
    ItemsMainScreen()
}

@Preview
@Composable
fun DefaultPreview() {
    App()
}