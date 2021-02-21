package yalexaner.items.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SoftwareKeyboardController
import androidx.compose.ui.unit.dp
import yalexaner.items.R

@ExperimentalMaterialApi
@Composable
fun BottomSheet(
    bottomSheetState: BottomSheetState = rememberBottomSheetState(BottomSheetValue.Collapsed)
) {
    val keyboardController = remember { mutableStateOf<SoftwareKeyboardController?>(null) }
    val focusRequester = FocusRequester()

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
                    bottomSheetState.expand {
                        focusRequester.requestFocus()
                        keyboardController.value?.showSoftwareKeyboard()
                    }
                } else {
                    bottomSheetState.collapse {
                        keyboardController.value?.hideSoftwareKeyboard()
                    }
                }
            }
        )

        Divider()

        NewOrder(focusRequester, keyboardController, onOrderAdded = {
            bottomSheetState.collapse {
                keyboardController.value?.hideSoftwareKeyboard()
            }
        })
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
        Image(
            imageVector = icon,
            modifier = Modifier.preferredSize(24.dp)
        )

        Spacer(modifier = Modifier.preferredWidth(16.dp))

        Text(
            text = text,
            modifier = Modifier.align(alignment = Alignment.CenterVertically)
        )
    }
}