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

    suspend fun login(email: String, password: String): LoginResponse {
        val response = apiService.postLogin(email, password)
        if (response.isSuccessful) {
            return response.body()!!
        } else {
            throw Exception("Login Failed")
        }
    }

    suspend fun register(
        name: String,
        outlet_name: String,
        phone: String,
        email: String,
        password: String,
        role: String
    ): RegisterResponse {
//        return try {
//            val response =
//                apiService.postRegister(name, outlet_name, email, password, role).awaitResponse()
//            if (response.isSuccessful) {
//                response.body()!!
//            } else {
//                throw Exception("Register failed")
//            }
//        } catch (e: Exception) {
//            throw e
//        }

        val response = apiService.postRegister(name, outlet_name, phone, email, password, role)
        if (response.isSuccessful) {
            return response.body()!!
        } else {
            throw Exception("Register Failed")
        }
    }

}