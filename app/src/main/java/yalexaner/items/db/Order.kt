package yalexaner.items.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.OffsetDateTime

@Entity(tableName = "orders")
data class Order(
    @PrimaryKey(autoGenerate = true) val id: Int,
    @ColumnInfo(name = "items_collected") val itemsCollected: Int,
    @ColumnInfo(name = "items_ordered") val itemsOrdered: Int,
    val date: OffsetDateTime,
    @ColumnInfo(name = "coast_per_item") val coastPerItem: Int
)
