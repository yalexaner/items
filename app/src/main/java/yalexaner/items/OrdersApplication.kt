package yalexaner.items

import android.app.Application
import yalexaner.items.db.OrderRepository
import yalexaner.items.db.OrderRoomDatabase

class OrdersApplication : Application() {

    private val database by lazy { OrderRoomDatabase.getDatabase(this) }
    val repository by lazy { OrderRepository(database.orderDao()) }
}