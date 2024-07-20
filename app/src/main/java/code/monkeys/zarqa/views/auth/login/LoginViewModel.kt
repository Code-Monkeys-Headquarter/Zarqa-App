package code.monkeys.zarqa.views.auth.login


import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import code.monkeys.zarqa.data.model.User
import code.monkeys.zarqa.repository.Repository
import kotlinx.coroutines.launch

class LoginViewModel(private val repository: Repository) : ViewModel() {

    val loginResult: LiveData<Result<User>> = repository.loginResult

    fun login(email: String, password: String, role: String) {
        viewModelScope.launch {
            repository.login(email, password, role)
        }
    }
}