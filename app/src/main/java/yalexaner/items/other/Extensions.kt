package yalexaner.items.other

import yalexaner.items.db.Order
import java.time.OffsetDateTime
import java.time.format.DateTimeFormatter

fun OffsetDateTime.toFormattedString(): String {
    return format(DateTimeFormatter.ofPattern("d MMM, uuuu"))
}

fun String.isZeroOrEmpty(): Boolean = this == "" || this == "0"

fun List<Order>.average(): Int {
    if (size == 0) return 0
    return sumBy { it.itemsCollected } / size
}