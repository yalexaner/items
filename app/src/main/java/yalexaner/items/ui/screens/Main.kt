package yalexaner.items.ui.screens

import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.AmbientContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.viewModel
import kotlinx.coroutines.launch
import yalexaner.items.OrdersApplication
import yalexaner.items.R
import yalexaner.items.data.MainViewModel
import yalexaner.items.data.MainViewModelFactory
import yalexaner.items.ui.components.MainHeader
import yalexaner.items.ui.components.BottomSheet
import yalexaner.items.ui.components.ItemsCard
import yalexaner.items.ui.components.OrdersList

@ExperimentalMaterialApi
@Composable
fun MainScreen() {
    val scaffoldState = rememberBottomSheetScaffoldState()
    val scope = rememberCoroutineScope()
    val context = AmbientContext.current

    val application = context.applicationContext as OrdersApplication
    val viewModel: MainViewModel =
        viewModel(factory = MainViewModelFactory(application.repository))

    val items by viewModel.itemsForToday.observeAsState(0)
    val orders by viewModel.ordersForToday.observeAsState(emptyList())

    BottomSheetScaffold(
        sheetContent = {
            BottomSheet(bottomSheetState = scaffoldState.bottomSheetState)
        },

        scaffoldState = scaffoldState,
    ) { innerPadding ->
        MainHeader(
            textDate = "Mon, 14th February",
            modifier = Modifier.padding(vertical = 16.dp)
        )

        ItemsCard(
            items = items,
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