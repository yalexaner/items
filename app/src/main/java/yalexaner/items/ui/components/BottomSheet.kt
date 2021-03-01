package yalexaner.items.ui.components

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

@ExperimentalComposeUiApi
@ExperimentalMaterialApi
@Composable
fun BottomSheet(
    bottomSheetState: BottomSheetState = rememberBottomSheetState(BottomSheetValue.Collapsed)
) {
    val keyboardController = LocalSoftwareKeyboardController.current
    val focusRequester = FocusRequester()
    val scope = CoroutineScope(Dispatchers.Default)

    Column {
        BottomSheetHeader(
            icon = if (bottomSheetState.isCollapsed) {
                Icons.Default.KeyboardArrowUp
            } else {
                Icons.Default.KeyboardArrowDown
            },

            text = if (bottomSheetState.isCollapsed) {
                stringResource(R.string.swipe_to_add)
            } else {
                stringResource(R.string.adding_order)
            },

            onClicked = {
                if (bottomSheetState.isCollapsed) {
                    scope.launch { bottomSheetState.expand() }
                } else {
                    scope.launch {
                        bottomSheetState.collapse()
                        keyboardController?.hideSoftwareKeyboard()
                    }
                }
            }
        )

        Divider()

        NewOrder(collectedFocus = focusRequester) {
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