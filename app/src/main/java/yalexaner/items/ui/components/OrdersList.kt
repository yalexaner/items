package yalexaner.items.ui.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.AmbientContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import yalexaner.items.R
import yalexaner.items.db.Order
import yalexaner.items.other.toFormattedString
import yalexaner.items.ui.theme.Green
import yalexaner.items.ui.theme.Orange
import yalexaner.items.ui.theme.Red

@ExperimentalMaterialApi
@Composable
fun OrdersList(
    modifier: Modifier = Modifier,
    orders: List<Order>?,
    onItemDeleted: (Order) -> Unit
) {
    if (!orders.isNullOrEmpty()) {
        List(modifier = modifier, orders = orders, onItemDeleted = onItemDeleted)
    } else {
        Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()) {
            Text(
                text = if (orders == null) {
                    stringResource(R.string.loading)
                } else {
                    stringResource(R.string.no_orders)
                }
            )
        }
    }
}

@ExperimentalMaterialApi
@Composable
private fun List(
    modifier: Modifier = Modifier,
    orders: List<Order>,
    onItemDeleted: (Order) -> Unit
) {
    LazyColumn(modifier = modifier) {
        itemsIndexed(orders) { index, order ->
            OrdersListItem(
                order = order,
                onItemDeleted = { onItemDeleted(order) }
            )

            if (index != orders.size - 1) {
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
        icon = { PercentageIcon(orderPercentage = orderPercentage) },
        text = { Text(text = textMessage, maxLines = 1) },
        secondaryText = {
            Text(text = stringResource(id = R.string.at, order.date.toFormattedString("h:mm a")))
        },
        trailing = {
            Row {
                IconButton(onClick = onItemEdited) { Icon(imageVector = Icons.Default.Edit) }
                Spacer(modifier = Modifier.preferredWidth(8.dp))
                IconButton(onClick = onItemDeleted) { Icon(imageVector = Icons.Default.Delete) }
            }
        }
    )
}

@Composable
private fun PercentageIcon(orderPercentage: Int) {
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