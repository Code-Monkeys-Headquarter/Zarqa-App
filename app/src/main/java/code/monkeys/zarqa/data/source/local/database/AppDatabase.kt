package code.monkeys.zarqa.data.source.local.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import code.monkeys.zarqa.data.source.local.dao.ProductDao
import code.monkeys.zarqa.data.source.local.dao.TransactionDao
import code.monkeys.zarqa.data.source.local.entity.Product
import code.monkeys.zarqa.data.source.local.entity.Transaction

@Database(entities = [Product::class, Transaction::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun productDao(): ProductDao
    abstract fun transactionDao(): TransactionDao


    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        private const val DATABASE_NAME = "zarqa_db"

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    DATABASE_NAME
                ).build()
                INSTANCE = instance
                instance
            }
        }

        fun resetDatabase(context: Context) {
            context.deleteDatabase(DATABASE_NAME)
            INSTANCE = null
        }
    }
}