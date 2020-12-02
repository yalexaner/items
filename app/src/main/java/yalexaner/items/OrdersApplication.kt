package yalexaner.items

import android.app.Application
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import yalexaner.items.db.OrderRepository
import yalexaner.items.db.OrderRoomDatabase

class OrdersApplication : Application() {

    private val applicationScope = CoroutineScope(SupervisorJob())

    val database by lazy { OrderRoomDatabase.getDatabase(this, applicationScope) }
    val repository by lazy { OrderRepository(database.orderDao()) }
}