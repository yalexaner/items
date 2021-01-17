package yalexaner.items.other

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import yalexaner.items.R
import java.time.OffsetDateTime
import java.time.format.DateTimeFormatter

object Convert {
    @RequiresApi(Build.VERSION_CODES.O)
    @JvmStatic
    fun toSimpleDateString(date: OffsetDateTime): String {
        return date.format(
            DateTimeFormatter.ofPattern("d MMM, uuuu")
        )
    }

    @JvmStatic
    fun itemsToString(itemsCollected: Int, itemsOrdered: Int, context: Context): String {
        return context.getString(
            R.string.order_items,
            itemsCollected,
            itemsOrdered,
            context.resources.getQuantityString(R.plurals.items, itemsOrdered)
        )
    }

    @JvmStatic
    fun coastToString(coast: Int) = "$coast " + if (coast == 1) "ruble" else "rubles"
}