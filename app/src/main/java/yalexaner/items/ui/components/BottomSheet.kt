package yalexaner.items.ui.components

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import yalexaner.items.R

private const val TAG = "ApplicationTag"

@ExperimentalComposeUiApi
@ExperimentalMaterialApi
@Composable
fun BottomSheet(
    bottomSheetState: BottomSheetState
) {
    val keyboardController = LocalSoftwareKeyboardController.current
    val focusRequester = FocusRequester()
    val scope = CoroutineScope(Dispatchers.Main)

    Log.i(TAG, "BottomSheet: ${bottomSheetState.progress.from} to ${bottomSheetState.progress.to}")
    Log.i(TAG, "BottomSheet: ${bottomSheetState.currentValue}")

    Column {
        BottomSheetHeader(
            icon = when (bottomSheetState.progress.to) {
                BottomSheetValue.Collapsed -> Icons.Default.KeyboardArrowUp
                BottomSheetValue.Expanded -> Icons.Default.KeyboardArrowDown
            },

            text = when (bottomSheetState.progress.to) {
                BottomSheetValue.Collapsed -> stringResource(id = R.string.swipe_to_add)
                BottomSheetValue.Expanded -> stringResource(id = R.string.adding_order)
            },

            onClicked = {
                when (bottomSheetState.isCollapsed) {
                    true -> scope.launch { bottomSheetState.expand() }
                    false -> scope.launch {
                        bottomSheetState.collapse()
                        keyboardController?.hideSoftwareKeyboard()
                    }
                }
            }
        )

        Divider()

        NewOrder(firstFieldFocus = focusRequester) {
            scope.launch {
                bottomSheetState.collapse()
                keyboardController?.hideSoftwareKeyboard()
            }
        }
    }
}

@Composable
private fun BottomSheetHeader(
    icon: ImageVector,
    text: String,
    onClicked: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClicked() }
            .padding(16.dp)
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            modifier = Modifier.size(24.dp)
        )

        Spacer(modifier = Modifier.width(16.dp))

        Text(
            text = text,
            modifier = Modifier.align(alignment = Alignment.CenterVertically)
        )
    }
}