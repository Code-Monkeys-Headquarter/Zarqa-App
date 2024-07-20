package code.monkeys.zarqa.repository

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import code.monkeys.zarqa.data.model.User
import code.monkeys.zarqa.data.source.local.dao.UserDao
import code.monkeys.zarqa.data.source.local.database.AppDatabase
import code.monkeys.zarqa.data.source.remote.ApiConfig

class Repository(context: Context) {
    private val apiService = ApiConfig.getApiService()
    private val userDao: UserDao = AppDatabase.getDatabase(context).userDao()

    //    Login
    private val _loginResult = MutableLiveData<Result<User>>()
    val loginResult: LiveData<Result<User>> = _loginResult

    //    Register
    private val _registerResult = MutableLiveData<Result<User>>()
    val registerResult: LiveData<Result<User>> = _registerResult

    suspend fun login(email: String, password: String, role: String) {
        val user = userDao.getUser(email, password, role)
        if (user != null) {
            _loginResult.value = Result.success(user)
        } else {
            _loginResult.postValue(Result.failure(Exception("Login Failed")))
        }
    }

    suspend fun register(fullname: String, email: String, password: String, role: String){
        val existingUser = userDao.getUserByEmail(email)
        if (existingUser != null) {
            val newUser = User(fullname = fullname, email = email, password = password, role = role)
            userDao.insertUser(newUser)
            _registerResult.postValue(Result.success(newUser))
        } else {
            _registerResult.postValue(Result.failure(Exception("Pengguna sudah terdaftar")))
        }
    }
}