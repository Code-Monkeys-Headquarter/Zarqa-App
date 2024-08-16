package code.monkeys.zarqa.views.worker.warehouse.product.detail.update

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import code.monkeys.zarqa.data.source.remote.request.product.Product
import code.monkeys.zarqa.repository.Repository
import kotlinx.coroutines.launch

class UpdateProductViewModel(private val repository: Repository) : ViewModel() {
    private val _error = MutableLiveData<String?>()
    val error: LiveData<String?> get() = _error

    private val _status = MutableLiveData<String>()
    val status: LiveData<String> get() = _status

    private val _code = MutableLiveData<Int>()
    val code: LiveData<Int> get() = _code

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> get() = _errorMessage

    private val _updateProduct = MutableLiveData<Result<Unit>>()
    val updateProduct: LiveData<Result<Unit>> get() = _updateProduct

    fun updateProduct(token: String, productId: String, product: Product) {
        viewModelScope.launch {
            val result = repository.updateProduct(token, productId, product)
            _updateProduct.value = result
        }
    }


}