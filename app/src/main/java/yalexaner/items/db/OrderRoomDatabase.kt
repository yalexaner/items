package yalexaner.items.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

@Database(entities = [Order::class], version = 2, exportSchema = false)
@TypeConverters(Converters::class)
abstract class OrderRoomDatabase : RoomDatabase() {

    abstract fun orderDao(): OrderDao

    companion object {
        @Volatile
        private var INSTANCE: OrderRoomDatabase? = null

        fun getDatabase(context: Context): OrderRoomDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    OrderRoomDatabase::class.java,
                    "orders"
                ).addMigrations(MIGRATION_1_2).build()

                INSTANCE = instance

                instance
            }
        }

        private val MIGRATION_1_2 = object : Migration(1, 2) {
            override fun migrate(database: SupportSQLiteDatabase) {
//                database.execSQL("ALTER TABLE orders RENAME COLUMN items TO items_collected")
//                database.execSQL("ALTER TABLE orders ADD COLUMN items_ordered INTEGER")
                with(database) {
                    execSQL("BEGIN TRANSACTION")
                    execSQL("CREATE TABLE orders_new (id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, items_collected INTEGER NOT NULL, items_ordered INTEGER NOT NULL, date TEXT NOT NULL, coast_per_item INTEGER NOT NULL)")
                    execSQL("INSERT INTO orders_new SELECT id, items, items, date, cpi FROM orders")
                    execSQL("DROP TABLE orders")
                    execSQL("ALTER TABLE orders_new RENAME TO orders")
                    execSQL("COMMIT")
                }
            }
        }
    }
}