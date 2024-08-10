package code.monkeys.zarqa.views.worker.warehouse.tab.allproduct

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import code.monkeys.zarqa.data.source.remote.response.DataItem
import code.monkeys.zarqa.repository.Repository
import kotlinx.coroutines.launch
import retrofit2.HttpException

class AllProductViewModel(private val repository: Repository) : ViewModel() {
//    private val _products = MutableLiveData<List<DataItem>>()
//    val products: LiveData<List<DataItem>> get() = _products
//
//    private val _status = MutableLiveData<String>()
//    val status: LiveData<String> get() = _status
//
//    private val _code = MutableLiveData<Int>()
//    val code: LiveData<Int> get() = _code
//
//    fun fetchProducts(token: String) {
//        viewModelScope.launch {
//            try {
//                val response = repository.getProducts(token)
//                if (response.isSuccessful) {
//                    _products.postValue(response.body()?.data?.filterNotNull() ?: emptyList())
//                    _status.postValue(response.body()?.status ?: "Unknown status")
//                    _code.postValue(response.body()?.code ?: 0)
//                } else {
//                    _status.postValue("Failed")
//                    _code.postValue(response.code())
//                }
//            } catch (e: Exception) {
//                _status.postValue("Error: ${e.message}")
//                _code.postValue(-1)
//            }
//        }
//    }

    private val _products = MutableLiveData<List<DataItem>>()
    val products: LiveData<List<DataItem>> get() = _products

    private val _status = MutableLiveData<String>()
    val status: LiveData<String> get() = _status

    private val _code = MutableLiveData<Int>()
    val code: LiveData<Int> get() = _code

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> get() = _errorMessage

    fun fetchProducts(token: String) {
        viewModelScope.launch {
            try {
                val response = repository.getProducts(token)
                if (response.isSuccessful) {
                    _products.postValue(response.body()?.data?.filterNotNull() ?: emptyList())
                    _status.postValue(response.body()?.status ?: "Unknown status")
                    _code.postValue(response.body()?.code ?: 0)
                } else {
                    _status.postValue("Failed")
                    _code.postValue(response.code())
                    _errorMessage.postValue(
                        "Error: ${response.message()} - ${
                            response.errorBody()?.string()
                        }"
                    )
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
}