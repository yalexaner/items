package yalexaner.items.db

import androidx.annotation.WorkerThread
import kotlinx.coroutines.flow.Flow

class OrderRepository(private val orderDao: OrderDao) {

    val allOrders: Flow<List<Order>> = orderDao.getAllOrders()
    val itemsCount: Flow<Int> = orderDao.getItemsCount()

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insert(order: Order) {
        orderDao.insert(order)
    }
}