package yalexaner.items.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign

@Composable
fun MainHeader(
    modifier: Modifier = Modifier,
    textDate: String
) {
    Text(
        text = textDate,
        style = MaterialTheme.typography.h6,
        textAlign = TextAlign.Center,
        modifier = modifier.fillMaxWidth()
    )
}