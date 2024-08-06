package code.monkeys.zarqa.views.worker.warehouse.product.restock

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import code.monkeys.zarqa.data.source.remote.request.product.Product
import code.monkeys.zarqa.repository.Repository
import kotlinx.coroutines.launch
import retrofit2.Response

class TakeProductViewModel(private val repository: Repository) : ViewModel() {

}