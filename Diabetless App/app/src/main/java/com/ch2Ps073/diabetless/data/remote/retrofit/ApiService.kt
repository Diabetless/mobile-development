package com.ch2Ps073.diabetless.data.remote


import com.ch2Ps073.diabetless.data.remote.response.ArticlesResponse
import com.ch2Ps073.diabetless.data.remote.response.DetailArticleResponse
import com.ch2Ps073.diabetless.data.remote.response.DetectedMealResponse
import com.ch2Ps073.diabetless.data.remote.response.ListUser
import com.ch2Ps073.diabetless.data.remote.response.MealDetailResponse
import com.ch2Ps073.diabetless.data.remote.response.MealsResponse
import com.ch2Ps073.diabetless.data.remote.response.RegisterResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Path

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

    @FormUrlEncoded
    @GET("users")
    fun getUser(
        @Header("Authorization") token: String
    ) : Call<ListUser>

    @GET("articles")
    suspend fun getAllArticles(): ArticlesResponse

    @GET("articles/{id}")
    suspend fun getDetailArticle(
        @Path("id") id: String
    ): DetailArticleResponse

    @GET("meals")
    suspend fun getAllMeals(): MealsResponse

    @GET("meals/{id}")
    suspend fun getDetailMeals(
        @Path("id") id: String
    ): MealDetailResponse

    @Multipart
    @POST("meals/detect-food")
    suspend fun getDetectedMeals(
        @Part image: MultipartBody.Part
    ) : DetectedMealResponse
}