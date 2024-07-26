package code.monkeys.zarqa.repository

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import code.monkeys.zarqa.data.source.local.dao.ProductDao
import code.monkeys.zarqa.data.source.local.entity.Product

class ProductRepository(private val productDao: ProductDao) {
    val lowStockProducts: LiveData<List<Product>> = productDao.getLowStockProduct()

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insertProduct(product: Product) {
        productDao.insertProduct(product)
    }

    fun getTotalProducts(): LiveData<Int> = productDao.getAllTotalProducts()

    fun getTotalStocks(): LiveData<Int> = productDao.getTotalStock()

    fun getTotalValue(): LiveData<Int> = productDao.getTotalPrice()

    fun getItemsAddedToday(currentDate: String): LiveData<Int> =
        productDao.getItemsAddedToday(currentDate)

    fun getItemsOutToday(currentDate: String): LiveData<Int> =
        productDao.getItemsOutToday(currentDate)

    fun getStockInToday(currentDate: String): LiveData<Int> =
        productDao.getStockInToday(currentDate)

    fun getStockOutToday(currentDate: String): LiveData<Int> =
        productDao.getStockOutToday(currentDate)


}