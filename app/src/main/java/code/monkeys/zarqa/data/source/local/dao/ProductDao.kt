package code.monkeys.zarqa.data.source.local.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import code.monkeys.zarqa.data.source.local.entity.Product

@Dao
interface ProductDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertProduct(product: Product)

    @Query("SELECT * FROM product_table WHERE productTotalStock <=  productLowStockAlert")
    fun getLowStockProduct(): LiveData<List<Product>>
}