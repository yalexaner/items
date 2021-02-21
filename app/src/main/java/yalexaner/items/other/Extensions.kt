package yalexaner.items.other

import androidx.compose.ui.input.key.Key
import java.time.OffsetDateTime
import java.time.format.DateTimeFormatter

fun OffsetDateTime.toFormattedString(): String {
    return format(DateTimeFormatter.ofPattern("d MMM, uuuu"))
}

fun String.isZeroOrEmpty(): Boolean = this == "" || this == "0"

fun Key.isDigit() = this in arrayOf(
    Key.Zero, Key.One,
    Key.Two, Key.Three,
    Key.Four, Key.Five,
    Key.Six, Key.Seven,
    Key.Eight, Key.Nine
)