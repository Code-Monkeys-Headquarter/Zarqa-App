package code.monkeys.zarqa.views.worker.warehouse.product.add

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import code.monkeys.zarqa.data.source.remote.request.product.Product
import code.monkeys.zarqa.repository.Repository
import kotlinx.coroutines.launch

class AddProductViewModel(private val repository: Repository) : ViewModel() {
    private val _addProductResult = MutableLiveData<Result<Unit>>()
    val addProductResult: LiveData<Result<Unit>> get() = _addProductResult

    fun addProduct(token: String, product: Product) {
        viewModelScope.launch {
            val result = repository.addProduct(token, product)
            _addProductResult.value = result
        }
    }

}