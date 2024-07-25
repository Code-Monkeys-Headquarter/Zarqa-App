package code.monkeys.zarqa.data.source.local.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query

@Dao
interface TransactionDao {

    //    Mengambil data item atau produk yang ditambah hari ini
    @Query("SELECT COUNT(*) FROM product_table WHERE dateAdded = :currentDate")
    fun getItemsAddedToday(currentDate: String): LiveData<Int>

    //    Mengambil data Item atau produk yang di ambil hari ini
    @Query("SELECT COUNT(*) FROM transaction_table WHERE type = 'OUT' AND date = :currentDate")
    fun getItemsOutToday(currentDate: String): LiveData<Int>

    //    Mengambil data stock produk yang ditambahkan hari ini
    @Query("SELECT SUM(quantity) FROM transaction_table WHERE type = 'IN' AND date = :currentDate")
    fun getStockInToday(currentDate: String): LiveData<Int>

    // Mengambil data stock produk yang di ambil hari ini
    @Query("SELECT SUM(quantity) FROM transaction_table WHERE type = 'OUT' AND date = :currentDate")
    fun getStockOutToday(currentDate: String): LiveData<Int>

}