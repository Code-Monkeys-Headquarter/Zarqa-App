package code.monkeys.zarqa.data.source.remote

import code.monkeys.zarqa.data.source.remote.response.LoginResponse
import code.monkeys.zarqa.data.source.remote.response.RegisterResponse
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST


interface ApiService {
    @FormUrlEncoded
    @POST("users/login")
    suspend fun postLogin(
        @Field("email") email: String,
        @Field("password") password: String
    ): Call<LoginResponse>

    @FormUrlEncoded
    @POST("users/register")
    suspend fun postRegister(
        @Field("name") name: String,
        @Field("outlet_name") outlateName: String,
        @Field("email") email: String,
        @Field("password") password: String,
        @Field("role") role: String
    ): Call<RegisterResponse>
}