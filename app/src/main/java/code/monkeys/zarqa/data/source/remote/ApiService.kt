package code.monkeys.zarqa.data.source.remote

import code.monkeys.zarqa.data.source.remote.request.product.Product
import code.monkeys.zarqa.data.source.remote.response.LoginResponse
import code.monkeys.zarqa.data.source.remote.response.RegisterResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.Header
import retrofit2.http.POST


interface ApiService {
    @FormUrlEncoded
    @POST("users/login")
    suspend fun postLogin(
        @Field("email") email: String,
        @Field("password") password: String
    ): Response<LoginResponse>

    @FormUrlEncoded
    @POST("users/register")
    suspend fun postRegister(
        @Field("name") name: String,
        @Field("outlet_name") outlateName: String,
        @Field("phone") phone: String,
        @Field("email") email: String,
        @Field("password") password: String,
        @Field("role") role: String
    ): Response<RegisterResponse>

//    @FormUrlEncoded
//    @POST("product")
//    suspend fun addProduct(
//        @Header("Authorization") token: String,
//        @Field("name") name: String,
//        @Field("images") images: String,  // Converted to String
//        @Field("color") color: String,
//        @Field("productType") productType: String  // Converted to String
//    ): Response<Any>

    @POST("product")
    suspend fun addProduct(
        @Header ("Authorization") token: String,
        @Body product: Product
    ): Response<Unit>
}