package code.monkeys.zarqa.views.worker.warehouse.product.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import code.monkeys.zarqa.data.source.remote.response.ProductDetailResponse
import code.monkeys.zarqa.repository.Repository
import kotlinx.coroutines.launch
import retrofit2.HttpException

class DetailProductViewModel(private val repository: Repository) : ViewModel() {

    private val _productDetail = MutableLiveData<ProductDetailResponse?>()
    val productDetail: LiveData<ProductDetailResponse?> get() = _productDetail

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> get() = _loading

    private val _error = MutableLiveData<String?>()
    val error: LiveData<String?> get() = _error

    private val _status = MutableLiveData<String>()
    val status: LiveData<String> get() = _status

    private val _code = MutableLiveData<Int>()
    val code: LiveData<Int> get() = _code

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> get() = _errorMessage


    fun fetchProductDetail(token: String, productId: String) {
        viewModelScope.launch {
            try {
                _loading.value = true
                val result = repository.getProductDetail(token, productId)
                _loading.value = false
                result.onSuccess {
                    _productDetail.value = it
                }.onFailure {
                    _error.value = it.message
                }
            } catch (e: HttpException) {
                _status.postValue("Error: ${e.message}")
                _code.postValue(e.code())
                _errorMessage.postValue("HTTP Error: ${e.message}")
            } catch (e: Exception) {
                _status.postValue("Error: ${e.message}")
                _code.postValue(-1)
                _errorMessage.postValue("Unknown Error: ${e.message}")
            }
        }

    }

    private val _deleteProduct = MutableLiveData<Result<Unit>>()
    val deleteProduct: LiveData<Result<Unit>> get() = _deleteProduct

    fun deleteProduct(token: String, productId: String) {
        viewModelScope.launch {
            val result = repository.deleteProduct(token, productId)
            _deleteProduct.value = result
        }
    }

}