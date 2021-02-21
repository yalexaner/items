package yalexaner.items.ui.main

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.AmbientContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SoftwareKeyboardController
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.viewModel
import yalexaner.items.OrdersApplication
import yalexaner.items.R
import yalexaner.items.data.NewOrderViewModel
import yalexaner.items.data.NewOrderViewModelFactory
import yalexaner.items.db.Order
import yalexaner.items.other.isZeroOrEmpty
import java.time.OffsetDateTime
import java.util.*

@Composable
fun NewOrder(
    collectedFocus: FocusRequester = FocusRequester.Default,
    keyboardController: MutableState<SoftwareKeyboardController?> = mutableStateOf(null),
    onOrderAdded: () -> Unit = {}
) {
    val application: OrdersApplication =
        AmbientContext.current.applicationContext as OrdersApplication
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
        Row {
            NewOrderTextField(
                value = collected,
                onValueChanged = {
                    viewModel.onItemsCollectedChange(it.filter { it.isDigit() }.take(3))
                },
                label = stringResource(R.string.collected),

                focusRequester = collectedFocus,
                keyboardController = keyboardController,

                modifier = Modifier.weight(0.3F)
            )

            Spacer(modifier = Modifier.preferredWidth(16.dp))

            NewOrderTextField(
                value = ordered,
                label = "ordered",
                onValueChanged = {
                    viewModel.onItemsOrderedChange(it.filter { it.isDigit() }.take(3))
                },
                modifier = Modifier.weight(0.3F)
            )
        }

        Spacer(modifier = Modifier.preferredHeight(16.dp))

        Button(
            onClick = {
                viewModel.addOrder(
                    Order(0, collected.toInt(), ordered.toInt(), OffsetDateTime.now(), 8)
                )
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

@Composable
private fun NewOrderTextField(
    modifier: Modifier = Modifier,
    value: String,
    label: String,
    focusRequester: FocusRequester = FocusRequester(),
    keyboardController: MutableState<SoftwareKeyboardController?> = mutableStateOf(null),
    onValueChanged: (String) -> Unit
) {
    OutlinedTextField(
        value = value,
        textStyle = MaterialTheme.typography.h4 + TextStyle(textAlign = TextAlign.Center),
        label = { Text(text = label) },

        onValueChange = onValueChanged,

        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Number,
            imeAction = ImeAction.Next
        ),
        onTextInputStarted = {
            keyboardController.value = it
        },

        modifier = modifier.focusRequester(focusRequester = focusRequester),
    )
}