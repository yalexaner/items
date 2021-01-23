package yalexaner.items.ui.main

import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.viewModel
import yalexaner.items.data.OrderViewModel

@Composable
fun ItemsMainScreen(model: OrderViewModel = viewModel()) {
    val items by model.itemsForToday.observeAsState()

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = items.toString(), fontSize = 72.sp)
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = {}) {
            Text("Add a new order", fontSize = 16.sp)
        }
    }
}

@Preview
@Composable
fun ItemsPreview() {
    ItemsMainScreen()
}