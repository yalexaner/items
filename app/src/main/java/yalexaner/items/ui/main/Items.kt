package yalexaner.items.ui.main

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.AmbientContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.navigate
import yalexaner.items.OrdersApplication
import yalexaner.items.data.OrderViewModel
import yalexaner.items.data.OrderViewModelFactory

@Composable
fun Items(navController: NavController) {
    val application: OrdersApplication =
        AmbientContext.current.applicationContext as OrdersApplication
    val viewModel: OrderViewModel =
        viewModel(factory = OrderViewModelFactory(application.repository))

    val items by viewModel.itemsForToday.observeAsState()

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = items.toString(),
            modifier = Modifier.clickable(onClick = { navController.navigate("orders_list") }),
            style = MaterialTheme.typography.h1
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = {}) {
            Text(stringResource(id = R.string.button_add_a_new_order), fontSize = 16.sp)
        }
    }
}