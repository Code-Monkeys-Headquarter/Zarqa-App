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

}