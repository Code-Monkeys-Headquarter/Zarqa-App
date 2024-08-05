package code.monkeys.zarqa.views.worker.warehouse.product.add

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import code.monkeys.zarqa.data.source.local.entity.Product
import code.monkeys.zarqa.repository.Repository
import kotlinx.coroutines.launch

class AddProductViewModel(private val repository: Repository) : ViewModel() {
//    val lowStockProducts: LiveData<List<Product>> = productRepository.lowStockProducts
//
//    fun insertProduct(product: Product) = viewModelScope.launch {
//        productRepository.insertProduct(product)
//    }


}