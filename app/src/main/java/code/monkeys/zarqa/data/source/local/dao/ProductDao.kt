package code.monkeys.zarqa.data.source.local.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import code.monkeys.zarqa.data.source.local.entity.Product

@Dao
interface ProductDao {

    //    Menambahkan Produk
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertProduct(product: Product)

    //    Mengambil data Total Produk
    @Query("SELECT COUNT(*) FROM product_table")
    fun getAllTotalProducts(): LiveData<Int>

    //    Mengambil data Produk yang Low Stock
    @Query("SELECT * FROM product_table WHERE productTotalStock <=  productLowStockAlert")
    fun getLowStockProduct(): LiveData<List<Product>>

    //    Mengambil data total stock
    @Query("SELECT SUM(productTotalStock) FROM product_table")
    fun getTotalStock(): LiveData<Int>

    //    Mengambil data total harga dari setiap Stock
    @Query("SELECT SUM(productPrice * productTotalStock) FROM product_table")
    fun getTotalPrice(): LiveData<Int>

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