package code.monkeys.zarqa.utils

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import code.monkeys.zarqa.repository.ProductRepository
import code.monkeys.zarqa.views.worker.home.WorkerHomeViewModel
import code.monkeys.zarqa.views.worker.warehouse.product.add.AddProductViewModel

class ViewModelFactoryProduct(private val productRepository: ProductRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AddProductViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return AddProductViewModel(productRepository) as T
        } else if (modelClass.isAssignableFrom(WorkerHomeViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return WorkerHomeViewModel(productRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}