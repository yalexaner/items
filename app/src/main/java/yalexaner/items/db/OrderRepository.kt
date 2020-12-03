package yalexaner.items.db

import androidx.annotation.WorkerThread
import kotlinx.coroutines.flow.Flow

class OrderRepository(private val orderDao: OrderDao) {

    val itemsCount: Flow<Int> = orderDao.getItemsCount()
    val allOrders: Flow<List<Order>> = orderDao.getAllOrders()

    @WorkerThread
    suspend fun insert(order: Order) {
        orderDao.insert(order)
    }
}