package code.monkeys.zarqa.views.auth.register

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import code.monkeys.zarqa.data.source.remote.response.RegisterResponse
import code.monkeys.zarqa.repository.Repository
import kotlinx.coroutines.launch

class RegisterViewModel(private val repository: Repository) : ViewModel() {

    private val _registerResponse = MutableLiveData<RegisterResponse>()
    val registerResponse: LiveData<RegisterResponse> get() = _registerResponse

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> get() = _error

    fun register(name: String, outlet_name: String, phone: String, email: String, password: String, role: String) {
        viewModelScope.launch {
            try {
                val response = repository.register(name, outlet_name, phone, email, password, role)
                _registerResponse.postValue(response)
            } catch (e: Exception) {
                _error.postValue(e.message)
            }
        }
    }

}