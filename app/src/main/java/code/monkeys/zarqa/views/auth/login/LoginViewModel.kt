package code.monkeys.zarqa.views.auth.login


import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import code.monkeys.zarqa.data.model.User
import code.monkeys.zarqa.repository.Repository
import kotlinx.coroutines.launch
import retrofit2.Callback

class LoginViewModel(private val repository: Repository) : ViewModel() {
    fun login(email: String, password: String, callback: (User?) -> Unit) {
        viewModelScope.launch {
            val user = repository.login(email, password)
            callback(user)
        }
    }
}