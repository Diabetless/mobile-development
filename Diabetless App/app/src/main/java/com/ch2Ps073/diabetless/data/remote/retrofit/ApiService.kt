<<<<<<< HEAD
package com.ch2Ps073.diabetless.data.remote.retrofit
=======
package com.ch2Ps073.diabetless.data.remote
>>>>>>> chello


import com.ch2Ps073.diabetless.data.remote.response.ArticlesResponse
import com.ch2Ps073.diabetless.data.remote.response.DetailArticleResponse
<<<<<<< HEAD
import com.ch2Ps073.diabetless.data.remote.response.FileUploadResponse
import com.ch2Ps073.diabetless.data.remote.response.HealthUserResponse
=======
import com.ch2Ps073.diabetless.data.remote.response.DetectedMealResponse
>>>>>>> chello
import com.ch2Ps073.diabetless.data.remote.response.ListUser
import com.ch2Ps073.diabetless.data.remote.response.MealDetailResponse
import com.ch2Ps073.diabetless.data.remote.response.MealsResponse
import com.ch2Ps073.diabetless.data.remote.response.RegisterResponse
import okhttp3.MultipartBody
<<<<<<< HEAD
=======
import okhttp3.RequestBody
>>>>>>> chello
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Multipart
import retrofit2.http.POST
<<<<<<< HEAD
import retrofit2.http.PUT
=======
>>>>>>> chello
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

<<<<<<< HEAD
=======
    @FormUrlEncoded
>>>>>>> chello
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

<<<<<<< HEAD
    @FormUrlEncoded
    @PUT("/users/edit-profile")
    suspend fun updateUser(
        @Header("Authorization") token: String,
        @Field("fullName") fullName: String,
        @Field("username") username: String,
        @Field("email") email: String
    ): FileUploadResponse

    @Multipart
    @PUT("/users/profile-picture")
    suspend fun updateUserPhotoP(
        @Header("Authorization") token: String,
        @Part image: MultipartBody.Part,
    ): FileUploadResponse

    @FormUrlEncoded
    @POST("/users/blood-sugar")
    suspend fun setBloodSL(
        @Header("Authorization") token: String,
        @Field("bloodSugarLevel") bloodSugarLevel: Int,
    ): FileUploadResponse

    @FormUrlEncoded
    @POST("/users/bmi")
    suspend fun setBody(
        @Header("Authorization") token: String,
        @Field("height") height: Int,
        @Field("weight") weight: Int,
    ): FileUploadResponse

    @GET("/users/health")
    fun getHealth(
        @Header("Authorization") token: String
    ): Call<HealthUserResponse>
=======
    @Multipart
    @POST("meals/detect-food")
    suspend fun getDetectedMeals(
        @Part image: MultipartBody.Part
    ) : DetectedMealResponse
>>>>>>> chello
}