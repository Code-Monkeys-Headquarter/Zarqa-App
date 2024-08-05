package code.monkeys.zarqa.views.auth.login


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import code.monkeys.zarqa.data.source.remote.response.LoginResponse
import code.monkeys.zarqa.repository.Repository
import kotlinx.coroutines.launch

class LoginViewModel(private val repository: Repository) : ViewModel() {

    private val _loginResponse = MutableLiveData<LoginResponse>()
    val loginResponse: LiveData<LoginResponse> get() = _loginResponse

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> get() = _error

    fun login(email: String, password: String) {
        viewModelScope.launch {
            try {
                val response = repository.login(email, password)
                _loginResponse.postValue(response)
            } catch (e: Exception) {
                _error.postValue(e.message)
            }
        }
    }

}