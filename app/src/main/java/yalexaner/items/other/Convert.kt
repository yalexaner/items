package yalexaner.items.other

import android.os.Build
import androidx.annotation.RequiresApi
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
    fun itemsToString(items: Int) = "$items " + if (items == 1) "item" else "items"

    @JvmStatic
    fun coastToString(coast: Int) = "$coast " + if (coast == 1) "ruble" else "rubles"
}