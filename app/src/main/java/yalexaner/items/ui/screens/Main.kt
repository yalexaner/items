package yalexaner.items.ui.screens

import androidx.compose.foundation.layout.padding
import androidx.compose.material.BottomSheetScaffold
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.SnackbarResult
import androidx.compose.material.rememberBottomSheetScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlinx.coroutines.launch
import yalexaner.items.OrdersApplication
import yalexaner.items.R
import yalexaner.items.data.MainViewModel
import yalexaner.items.data.MainViewModelFactory
import yalexaner.items.other.average
import yalexaner.items.other.toFormattedString
import yalexaner.items.ui.components.MainHeader
import yalexaner.items.ui.components.BottomSheet
import yalexaner.items.ui.components.ItemsCard
import yalexaner.items.ui.components.OrdersList
import java.time.OffsetDateTime

@ExperimentalComposeUiApi
@ExperimentalMaterialApi
@Composable
fun MainScreen() {
    val scaffoldState = rememberBottomSheetScaffoldState()
    val scope = rememberCoroutineScope()
    val context = LocalContext.current

    val application = context.applicationContext as OrdersApplication
    val viewModel: MainViewModel =
        viewModel(factory = MainViewModelFactory(application.repository))

    val items by viewModel.itemsForToday.observeAsState()
    val orders by viewModel.ordersForToday.observeAsState()

    BottomSheetScaffold(
        sheetContent = {
            BottomSheet(bottomSheetState = scaffoldState.bottomSheetState)
        },

        scaffoldState = scaffoldState,
    ) { innerPadding ->
        MainHeader(
            textDate = OffsetDateTime.now().toFormattedString("EEEE, d MMMM"),
            modifier = Modifier.padding(vertical = 16.dp)
        )

        ItemsCard(
            items = items,
            extras = if (!orders.isNullOrEmpty()) { arrayOf(
                orders!!.average(),
                orders!!.minOf { it.collected },
                orders!!.maxOf { it.collected }
            )} else null,
            modifier = Modifier
                .padding(bottom = 8.dp)
                .padding(horizontal = 32.dp)
        )

        OrdersList(modifier = Modifier.padding(innerPadding), orders = orders) { order ->
            viewModel.deleteOrder(order.id)

            scope.launch {
                scaffoldState.snackbarHostState.currentSnackbarData?.dismiss()

                val snackbarResult = scaffoldState.snackbarHostState.showSnackbar(
                    message = context.resources.getString(R.string.order_deleted),
                    actionLabel = context.resources.getString(R.string.undo),
                )

                if (snackbarResult == SnackbarResult.ActionPerformed) {
                    viewModel.addOrder(order = order)
                }
            }
        }
    }
}