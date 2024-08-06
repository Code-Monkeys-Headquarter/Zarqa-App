package code.monkeys.zarqa.repository

import android.app.Application
import code.monkeys.zarqa.data.source.remote.ApiConfig
import code.monkeys.zarqa.data.source.remote.ApiService
import code.monkeys.zarqa.data.source.remote.request.product.ProductType
import code.monkeys.zarqa.data.source.remote.response.LoginResponse
import code.monkeys.zarqa.data.source.remote.response.RegisterResponse
import com.google.gson.Gson

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

        val response = apiService.postRegister(name, outlet_name, phone, email, password, role)
        if (response.isSuccessful) {
            return response.body()!!
        } else {
            throw Exception("Register Failed")
        }
    }

    suspend fun addProduct(
        token: String,
        name: String,
        images: List<String>,
        color: String,
        productType: ProductType
    ): Result<Any> {
        return try {
            val gson = Gson()
            val imagesJson = gson.toJson(images)
            val productTypeJson = gson.toJson(productType)

            val response = apiService.addProduct(token, name, imagesJson, color, productTypeJson)
            if (response.isSuccessful) {
                Result.success(response.body()!!)
            } else {
                Result.failure(Throwable(response.message()))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

}