package yalexaner.items.db

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import java.time.OffsetDateTime

@Database(entities = [Order::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class OrderRoomDatabase : RoomDatabase() {

    abstract fun orderDao(): OrderDao

    companion object {
        @Volatile
        private var INSTANCE: OrderRoomDatabase? = null

        fun getDatabase(context: Context, scope: CoroutineScope): OrderRoomDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    OrderRoomDatabase::class.java,
                    "orders"
                ).addCallback(OrderDatabaseCallback(scope)).build()

                INSTANCE = instance

                instance
            }
        }
    }

    private class OrderDatabaseCallback(private val scope: CoroutineScope) :
        RoomDatabase.Callback() {

        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            INSTANCE?.let { database ->
                scope.launch {
                    populateDatabase(database.orderDao())
                }
            }
        }

        @RequiresApi(Build.VERSION_CODES.O)
        private suspend fun populateDatabase(orderDao: OrderDao) {
            orderDao.deleteAll()

            orderDao.insert(Order(0, OffsetDateTime.now(), 16, 8))
            orderDao.insert(Order(1, OffsetDateTime.now(), 32, 8))
        }
    }
}