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
import java.time.OffsetDateTime
import java.util.*

@ExperimentalComposeUiApi
@Composable
fun NewOrder(
    collectedFocus: FocusRequester = FocusRequester.Default,
    onOrderAdded: () -> Unit = {}
) {
    val application: OrdersApplication =
        LocalContext.current.applicationContext as OrdersApplication
    val viewModel: NewOrderViewModel =
        viewModel(factory = NewOrderViewModelFactory(application.repository))

    val orderedFocus: FocusRequester = FocusRequester.Default

    val collected by viewModel.itemsCollected.observeAsState(initial = "")
    val ordered by viewModel.itemsOrdered.observeAsState(initial = "")

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Row {
            NewOrderTextField(
                modifier = Modifier.weight(0.3F),
                value = collected,
                trailingText = stringResource(R.string.collected),

                focusRequester = collectedFocus,
                onImeActionPerformed = { orderedFocus.requestFocus() }
            ) {
                viewModel.onItemsCollectedChange(it.filter { it.isDigit() }.take(3))
                viewModel.onItemsOrderedChange(it.filter { it.isDigit() }.take(3))
            }

            Spacer(modifier = Modifier.width(16.dp))

            NewOrderTextField(
                modifier = Modifier.weight(0.3F),
                value = ordered,
                trailingText = stringResource(R.string.ordered),

                focusRequester = orderedFocus,
                onValueChanged = {
                    viewModel.onItemsOrderedChange(it.filter { it.isDigit() }.take(3))
                }
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                viewModel.addOrder(
                    Order(0, collected.toInt(), ordered.toInt(), OffsetDateTime.now(), 8)
                )
                viewModel.onItemsCollectedChange("")
                viewModel.onItemsOrderedChange("")
                onOrderAdded()
            },
            enabled = !collected.isZeroOrEmpty() && !ordered.isZeroOrEmpty(),
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

        onValueChange = { onValueChanged(it.text) },

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