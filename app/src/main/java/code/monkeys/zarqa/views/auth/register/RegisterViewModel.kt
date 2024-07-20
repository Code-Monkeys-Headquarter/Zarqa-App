package code.monkeys.zarqa.views.auth.register

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import code.monkeys.zarqa.data.model.User
import code.monkeys.zarqa.repository.Repository
import kotlinx.coroutines.launch

class RegisterViewModel(private val repository: Repository) : ViewModel() {
    val registerResult: LiveData<Result<User>> = repository.registerResult
    fun registerUser(fullname: String, email: String, password: String, role: String) {
        viewModelScope.launch {
            repository.register(fullname, email, password, role)
        }
    }
}