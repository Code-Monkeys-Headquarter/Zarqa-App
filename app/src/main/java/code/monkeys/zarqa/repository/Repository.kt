package code.monkeys.zarqa.repository

import android.app.Application
import code.monkeys.zarqa.data.source.remote.ApiConfig
import code.monkeys.zarqa.data.source.remote.ApiService
import code.monkeys.zarqa.data.source.remote.request.product.Product
import code.monkeys.zarqa.data.source.remote.response.ProductDetailResponse
import code.monkeys.zarqa.data.source.remote.response.LoginResponse
import code.monkeys.zarqa.data.source.remote.response.ProductResponse
import code.monkeys.zarqa.data.source.remote.response.RegisterResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response

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

    suspend fun addProduct(token: String, product: Product): Result<Unit> {
        return withContext(Dispatchers.IO) {
            try {
                val response = apiService.addProduct(token, product)
                if (response.isSuccessful) {
                    Result.success(Unit)
                } else {
                    Result.failure(Throwable(response.message()))
                }
            } catch (e: Exception) {
                Result.failure(e)
            }
        }
    }

    suspend fun getProducts(token: String): Response<ProductResponse> {
        return apiService.fetchProducts(token)
    }

    suspend fun getProductDetail(token: String, productId: String): Result<ProductDetailResponse?> {
        return try {
            val response = apiService.getProductDetail(token, productId)
            if (response.isSuccessful) {
                Result.success(response.body())
            } else {
                Result.failure(Exception(response.message()))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }


//    suspend fun addProduct(): Result<Any> {
//        return try {
//
//            val response = apiService.addProduct(token, name, images, color, productTypeJson)
//            if (response.isSuccessful) {
//                Result.success(response.body()!!)
//            } else {
//                Result.failure(Throwable(response.message()))
//            }
//        } catch (e: Exception) {
//            Result.failure(e)
//        }
//    }

}