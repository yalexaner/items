package yalexaner.items.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import java.time.OffsetDateTime

@Dao
interface OrderDao {

    @Query("SELECT * FROM orders ORDER BY date")
    fun getAllOrders(): Flow<List<Order>>

    @Query("SELECT * FROM orders WHERE date(:date) = date(date)")
    fun getAllOrdersOfDate(date: OffsetDateTime): Flow<List<Order>>

    @Query("SELECT total(items_collected) FROM orders")
    fun getCollectedItemsCount(): Flow<Int>

    @Query("SELECT total(items_ordered) FROM orders")
    fun getOrderedItemsCount(): Flow<Int>

    @Query("SELECT total(items_collected) FROM orders WHERE date('now') = date(date)")
    fun getCollectedItemsCountForToday(): Flow<Int>

    @Query("SELECT total(items_ordered) FROM orders WHERE date('now') = date(date)")
    fun getOrderedItemsCountForToday(): Flow<Int>

    @Query("SELECT total(items_collected) FROM orders WHERE date(:date) = date(date)")
    fun getCollectedItemsCountForDate(date: OffsetDateTime): Int

    @Query("SELECT total(items_ordered) FROM orders WHERE date(:date) = date(date)")
    fun getOrderedItemsCountForDate(date: OffsetDateTime): Int

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(order: Order)

    @Query("DELETE FROM orders WHERE :id = id")
    suspend fun deleteOrder(id: Int)

    @Query("DELETE FROM orders")
    suspend fun deleteAll()
}