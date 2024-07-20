package code.monkeys.zarqa.views.auth.register

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import code.monkeys.zarqa.data.model.User
import code.monkeys.zarqa.repository.Repository
import kotlinx.coroutines.launch

class RegisterViewModel(private val repository: Repository) : ViewModel() {
    fun register(fullname: String, email: String, password: String, role: String) {
        val user = User(fullname = fullname,  email = email, password = password, role = role)
        viewModelScope.launch {
            repository.register(user)
        }
    }
}