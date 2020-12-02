package yalexaner.items.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import java.time.OffsetDateTime

@Dao
interface OrderDao {
    @Query("SELECT * FROM orders")
    fun getAllOrders(): Flow<List<Order>>

    @Query("SELECT total(items) FROM orders")
    fun getItemsCount(): Flow<Int>

    @Query("SELECT * FROM orders WHERE date(:date) = date(date)")
    fun getAllOrdersOfDate(date: OffsetDateTime): Flow<List<Order>>

    @Query("SELECT total(items) FROM orders WHERE date(:date) = date(date)")
    fun getItemsCountForDate(date: OffsetDateTime): Int

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(order: Order)

    @Query("DELETE FROM orders")
    suspend fun deleteAll()
}
