package com.CH2PS073.diabetless.data.remote


import com.CH2PS073.diabetless.data.remote.response.RegisterResponse
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface ApiService {
    @FormUrlEncoded
    @POST("users/register")
    suspend fun register(
        @Field("fullName") name: String,
        @Field("email") email: String,
        @Field("username") username : String,
        @Field("password") password: String
    ): RegisterResponse

    @FormUrlEncoded
    @POST("users/login")
    suspend fun login(
        @Field("email") email: String,
        @Field("password") password: String
    ): RegisterResponse
}