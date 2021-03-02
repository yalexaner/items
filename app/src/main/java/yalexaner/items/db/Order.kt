package yalexaner.items.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.OffsetDateTime

@Entity(tableName = "orders")
data class Order(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo(name = "items_collected") val collected: Int,
    @ColumnInfo(name = "items_ordered") val ordered: Int,
    val date: OffsetDateTime = OffsetDateTime.now(),
    @ColumnInfo(name = "coast_per_item") val coastPerItem: Int = 8
)
