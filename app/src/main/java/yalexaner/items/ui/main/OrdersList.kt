package yalexaner.items.ui.main

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.AmbientContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.viewModel
import kotlinx.coroutines.launch
import yalexaner.items.OrdersApplication
import yalexaner.items.R
import yalexaner.items.data.OrderViewModel
import yalexaner.items.data.OrderViewModelFactory
import yalexaner.items.db.Order
import yalexaner.items.other.toFormattedString
import yalexaner.items.ui.theme.Green
import yalexaner.items.ui.theme.Orange
import yalexaner.items.ui.theme.Red

@ExperimentalMaterialApi
@Composable
fun OrdersList() {
    val scaffoldState = rememberScaffoldState()
    val scope = rememberCoroutineScope()

    val context = AmbientContext.current
    val application = context.applicationContext as OrdersApplication
    val resources = context.resources

    val viewModel: OrderViewModel =
        viewModel(factory = OrderViewModelFactory(application.repository))

    val orders by viewModel.orders.observeAsState(emptyList())

    Scaffold(scaffoldState = scaffoldState) {
        LazyColumn {
            items(orders) { order ->
                OrdersListItem(
                    order = order,
                    onItemDeleted = {
                        viewModel.deleteOrder(order.id)

                        scope.launch {
                            scaffoldState.snackbarHostState.currentSnackbarData?.dismiss()

                            val snackbarResult = scaffoldState.snackbarHostState.showSnackbar(
                                message = resources.getString(R.string.order_deleted),
                                actionLabel = resources.getString(R.string.undo),
                            )

                            if (snackbarResult == SnackbarResult.ActionPerformed) {
                                viewModel.addOrder(order = order)
                            }
                        }
                    }
                )
                Divider(startIndent = 72.dp)
            }
        }
    }
}

@ExperimentalMaterialApi
@Composable
private fun OrdersListItem(
    order: Order,
    onItemEdited: () -> Unit = {},
    onItemDeleted: () -> Unit = {}
) {
    val itemsCollected = order.itemsCollected
    val itemsOrdered = order.itemsOrdered
    val orderPercentage = (itemsCollected.toDouble() / itemsOrdered * 100).toInt()

    val resources = AmbientContext.current.resources
    val textMessage: String = if (itemsCollected == itemsOrdered) {
        val items = resources.getQuantityText(R.plurals.items, itemsCollected)
        stringResource(id = R.string.items_fully_collected, itemsCollected, items)
    } else {
        stringResource(id = R.string.items_collected, itemsCollected, itemsOrdered)
    }

    ListItem(
        icon = { ListIcon(orderPercentage = orderPercentage) },
        text = { Text(text = textMessage) },
        secondaryText = { Text(text = order.date.toFormattedString()) },
        trailing = {
            Row {
                IconButton(onClick = onItemEdited) { Icon(imageVector = Icons.Default.Edit) }
                Spacer(modifier = Modifier.width(8.dp))
                IconButton(onClick = onItemDeleted) { Icon(imageVector = Icons.Default.Delete) }
            }
        }
    )
}

@Composable
private fun ListIcon(orderPercentage: Int) {
    val color = when {
        orderPercentage >= 92 -> Green
        orderPercentage >= 70 -> Orange
        else -> Red
    }

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .size(40.dp)
            .border(width = 1.dp, color = color, shape = RoundedCornerShape(10.dp))
    ) {
        Text(
            text = "$orderPercentage%",
            style = MaterialTheme.typography.body2
        )
    }
}

@Preview
@Composable
fun OrdersListItemPreview() {
    Surface {
//        OrdersListItem()
    }
}

@Preview
@Composable
fun OrdersListPreview() {
    Surface {
//        OrdersList()
    }
}