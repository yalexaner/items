package yalexaner.items.ui.main

import androidx.annotation.DrawableRes
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.AmbientContext
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import yalexaner.items.R
import yalexaner.items.data.OrderViewModel
import yalexaner.items.db.Order
import yalexaner.items.other.toFormattedString
import yalexaner.items.ui.theme.Green
import yalexaner.items.ui.theme.Orange
import yalexaner.items.ui.theme.Red

@ExperimentalMaterialApi
@Composable
fun OrdersList(viewModel: OrderViewModel) {
    val scaffoldState = rememberScaffoldState()
    val scope = rememberCoroutineScope()
    val resources = AmbientContext.current.resources

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

@Composable
private fun Header() {
    Text(
        text = "Header",
        modifier = Modifier
            .fillMaxWidth()
            .background(color = Color.LightGray)
            .padding(horizontal = 16.dp, vertical = 8.dp)
    )
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

    ListItem(
        icon = { ListIcon(orderPercentage = orderPercentage) },
        text = { Text(text = "$itemsCollected of $itemsOrdered items collected") },
        secondaryText = { Text(text = order.date.toFormattedString()) },
        trailing = {
            Row {
                ActionButton(imageId = R.drawable.ic_baseline_edit_24, onClick = onItemEdited)
                Spacer(modifier = Modifier.width(8.dp))
                ActionButton(imageId = R.drawable.ic_baseline_delete_24, onClick = onItemDeleted)
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

@ExperimentalMaterialApi
@Composable
private fun ActionButton(@DrawableRes imageId: Int, onClick: () -> Unit) {

    IconButton(onClick = onClick) {
        Icon(imageVector = vectorResource(id = imageId))
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