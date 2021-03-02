package yalexaner.items.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import yalexaner.items.OrdersApplication
import yalexaner.items.R
import yalexaner.items.data.NewOrderViewModel
import yalexaner.items.data.NewOrderViewModelFactory
import yalexaner.items.db.Order
import yalexaner.items.other.isZeroOrEmpty
import java.util.*

@ExperimentalComposeUiApi
@Composable
fun NewOrder(
    firstFieldFocus: FocusRequester = FocusRequester.Default,
    secondFieldFocus: FocusRequester = FocusRequester.Default,
    onOrderAdded: () -> Unit = {}
) {
    val application: OrdersApplication =
        LocalContext.current.applicationContext as OrdersApplication
    val viewModel: NewOrderViewModel =
        viewModel(factory = NewOrderViewModelFactory(application.repository))

    val collected by viewModel.itemsCollected.observeAsState(initial = "")
    val ordered by viewModel.itemsOrdered.observeAsState(initial = "")

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        TextFields(
            values = collected to ordered,
            focuses = firstFieldFocus to secondFieldFocus,
            onValueChanged = viewModel::onBothItemsChange to viewModel::onItemsOrderedChange
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            enabled = !collected.isZeroOrEmpty() && !ordered.isZeroOrEmpty(),
            onClick = {
                val order = Order(collected = collected.toInt(), ordered = ordered.toInt())

                viewModel.addOrder(order)
                viewModel.clearItems()
                onOrderAdded()
            }
        )
    }
}

@ExperimentalComposeUiApi
@Composable
private fun TextFields(
    values: Pair<String, String>,
    focuses: Pair<FocusRequester, FocusRequester>,
    onValueChanged: Pair<(String) -> Unit, (String) -> Unit>
) {
    Row {
        NewOrderTextField(
            value = values.first,
            trailingText = stringResource(R.string.collected),

            focusRequester = focuses.first,
            onImeActionPerformed = { focuses.second },
            onValueChanged = onValueChanged.first,

            modifier = Modifier.weight(0.3F)
        )

        Spacer(modifier = Modifier.width(16.dp))

        NewOrderTextField(
            value = values.second,
            trailingText = stringResource(R.string.ordered),

            focusRequester = focuses.second,
            onValueChanged = onValueChanged.second,

            modifier = Modifier.weight(0.3F),
        )
    }
}

@Composable
private fun Button(
    enabled: Boolean = true,
    onClick: () -> Unit,
) {
    Button(
        onClick = onClick,
        enabled = enabled,
        shape = RoundedCornerShape(16.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            text = stringResource(id = R.string.button_add_items).toUpperCase(Locale.ROOT),
            style = MaterialTheme.typography.button,
            modifier = Modifier.padding(8.dp)
        )
    }
}

@ExperimentalComposeUiApi
@Composable
private fun NewOrderTextField(
    modifier: Modifier = Modifier,
    value: String,
    trailingText: String,
    focusRequester: FocusRequester = FocusRequester(),
    onImeActionPerformed: () -> Unit = {},
    onValueChanged: (String) -> Unit
) {
    OutlinedTextField(
        value = TextFieldValue(value, TextRange(value.length)),
        textStyle = MaterialTheme.typography.h4 + TextStyle(textAlign = TextAlign.Center),
        trailingIcon = { Text(text = trailingText) },

        onValueChange = { newValue ->
            onValueChanged(newValue.text.filter { it.isDigit() }.take(3))
        },

        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Number,
            imeAction = ImeAction.Next
        ),
        keyboardActions = KeyboardActions(
            onNext = { onImeActionPerformed() }
        ),

        modifier = modifier.focusRequester(focusRequester = focusRequester),
    )
}