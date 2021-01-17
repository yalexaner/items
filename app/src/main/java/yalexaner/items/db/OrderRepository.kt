package yalexaner.items.db

import androidx.annotation.WorkerThread
import kotlinx.coroutines.flow.Flow

class OrderRepository(private val orderDao: OrderDao) {

    val itemsCountForToday: Flow<Int> = orderDao.getCollectedItemsCountForToday()
    val allOrders: Flow<List<Order>> = orderDao.getAllOrders()

    @WorkerThread
    suspend fun insert(order: Order) {
        orderDao.insert(order)
    }

    @WorkerThread
    suspend fun delete(id: Int) {
        orderDao.deleteOrder(id)
    }
}