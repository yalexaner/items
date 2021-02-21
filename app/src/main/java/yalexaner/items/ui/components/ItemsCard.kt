package yalexaner.items.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.AmbientContext
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.viewModel
import yalexaner.items.OrdersApplication
import yalexaner.items.R
import yalexaner.items.data.MainViewModel
import yalexaner.items.data.MainViewModelFactory

@Composable
fun ItemsCard(modifier: Modifier = Modifier) {
    val application: OrdersApplication =
        AmbientContext.current.applicationContext as OrdersApplication
    val viewModel: MainViewModel =
        viewModel(factory = MainViewModelFactory(application.repository))

    val items by viewModel.itemsForToday.observeAsState(0)

    Card(
        elevation = 5.dp,
        modifier = modifier.padding(bottom = 8.dp)
    ) {
        Column {
            ItemsCardHeader(items = items)
            ItemsCardExtra(
                modifier = Modifier.padding(vertical = 16.dp),
                itemsPerOrder = 23,
                itemsPerHour = 15,
                itemsMaxPerDay = 53
            )
        }
    }
}

@Composable
private fun ItemsCardHeader(
    modifier: Modifier = Modifier,
    items: Int
) {
    val stringItems = AmbientContext.current.resources.getQuantityString(R.plurals.items, items)

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier.fillMaxWidth()
    ) {
        Text(
            text = "$items $stringItems",
            style = MaterialTheme.typography.h4,
        )

        Text(
            text = stringResource(R.string.today_collected),
            style = MaterialTheme.typography.h5,
        )
    }
}

@Composable
private fun ItemsCardExtra(
    modifier: Modifier = Modifier,
    itemsPerOrder: Int,
    itemsPerHour: Int,
    itemsMaxPerDay: Int,
) {
    val stringItemsPerOrder =
        AmbientContext.current.resources.getQuantityString(R.plurals.items, itemsPerOrder)
    val stringItemsPerHour =
        AmbientContext.current.resources.getQuantityString(R.plurals.items, itemsPerHour)
    val stringItemsMaxPerDay =
        AmbientContext.current.resources.getQuantityString(R.plurals.items, itemsMaxPerDay)

    val extrasStrings = stringArrayResource(id = R.array.extras)

    Row(
        horizontalArrangement = Arrangement.SpaceEvenly,
        modifier = modifier.fillMaxWidth()
    ) {
        Text(
            text = "$itemsPerOrder $stringItemsPerOrder\n${extrasStrings[0]}",
            style = MaterialTheme.typography.caption,
        )

        Text(
            text = "$itemsPerHour $stringItemsPerHour\n${extrasStrings[1]}",
            style = MaterialTheme.typography.caption,
        )

        Text(
            text = "$itemsMaxPerDay $stringItemsMaxPerDay\n${extrasStrings[2]}",
            style = MaterialTheme.typography.caption,
        )
    }
}