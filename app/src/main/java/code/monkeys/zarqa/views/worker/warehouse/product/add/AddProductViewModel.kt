package code.monkeys.zarqa.views.worker.warehouse.product.add

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import code.monkeys.zarqa.data.source.remote.request.product.ProductType
import code.monkeys.zarqa.repository.Repository
import kotlinx.coroutines.launch

class AddProductViewModel(private val repository: Repository) : ViewModel() {
    private val _addProductResult = MutableLiveData<Result<Any>>()
    val addProductResult: LiveData<Result<Any>> get() = _addProductResult

    fun addProduct(
        token: String,
        name: String,
        images: List<String>,
        color: String,
        productType: ProductType
    ) {
        viewModelScope.launch {
            val tokenBearer = "Bearer $token"
            val result = repository.addProduct(tokenBearer, name, images, color, productType)
            _addProductResult.value = result
        }
    }

}