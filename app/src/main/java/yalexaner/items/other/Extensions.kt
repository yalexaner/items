package yalexaner.items.other

import java.time.OffsetDateTime
import java.time.format.DateTimeFormatter

fun OffsetDateTime.toFormattedString(): String {
    return format(DateTimeFormatter.ofPattern("d MMM, uuuu"))
}