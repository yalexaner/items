package yalexaner.items.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.AmbientContext
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import yalexaner.items.R

@Composable
fun ItemsCard(
    modifier: Modifier = Modifier,
    items: Int,
    extras: Array<Int?>
) {
    Card(
        elevation = 10.dp,
        modifier = modifier
    ) {
        Column {
            ItemsCardHeader(items = items, modifier = Modifier.padding(top = 16.dp))

            Spacer(modifier = Modifier.preferredHeight(16.dp))

            ItemsCardExtra(
                modifier = Modifier.padding(bottom = 16.dp),
                extrasItems = extras,
                extrasInfos = stringArrayResource(id = R.array.extras)
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
    extrasItems: Array<Int?>,
    extrasInfos: Array<String>
) {
    Row(
        horizontalArrangement = Arrangement.SpaceEvenly,
        modifier = modifier.fillMaxWidth()
    ) {
        repeat(extrasItems.size) { index ->
            val items = extrasItems[index] ?: return@repeat
            val stringItems =
                AmbientContext.current.resources.getQuantityString(R.plurals.items, items)
            val info = extrasInfos[index]

            Text(
                text = "$items $stringItems\n$info",
                style = MaterialTheme.typography.caption,
            )
        }
    }
}