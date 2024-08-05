package code.monkeys.zarqa.repository

import android.app.Application
import code.monkeys.zarqa.data.source.remote.ApiConfig
import code.monkeys.zarqa.data.source.remote.ApiService
import code.monkeys.zarqa.data.source.remote.response.LoginResponse
import code.monkeys.zarqa.data.source.remote.response.RegisterResponse
import retrofit2.awaitResponse
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class Repository(application: Application) {

    private val apiService: ApiService = ApiConfig.getApiService()
    private val executorService: ExecutorService = Executors.newSingleThreadExecutor()

    suspend fun login(email: String, password: String): LoginResponse {
        return try {
            val response = apiService.postLogin(email, password).awaitResponse()
            if (response.isSuccessful) {
                response.body()!!
            } else {
                throw Exception("Login failed")
            }

        } catch (e: Exception) {
            throw e
        }
    }

    suspend fun register(
        name: String,
        outlet_name: String,
        email: String,
        password: String,
        role: String
    ): RegisterResponse {
        return try {
            val response = apiService.postRegister(name, outlet_name, email, password, role).awaitResponse()
            if (response.isSuccessful) {
                response.body()!!
            } else {
                throw Exception("Register failed")
            }
        } catch (e: Exception) {
            throw e
        }
    }

}