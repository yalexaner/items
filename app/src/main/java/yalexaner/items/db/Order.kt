package yalexaner.items.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.OffsetDateTime

@Entity(tableName = "orders")
data class Order(
    @PrimaryKey(autoGenerate = true) val id: Int,
    val date: OffsetDateTime,
    val items: Int,
    @ColumnInfo(name = "cpi") val coastPerItem: Int
)
