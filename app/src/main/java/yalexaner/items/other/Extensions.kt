package yalexaner.items.other

import yalexaner.items.db.Order
import java.time.OffsetDateTime
import java.time.format.DateTimeFormatter

fun OffsetDateTime.toFormattedString(pattern: String): String {
    return format(DateTimeFormatter.ofPattern(pattern))
}

fun String.isZeroOrEmpty() = this == "" || this == "0"

fun List<Order>.average() = when (size) {
    0 -> 0
    else -> sumBy { it.collected } / size
}