package com.ch2Ps073.diabetless.data.remote.response

import com.google.gson.annotations.SerializedName

data class GetUserResponse(

    @field:SerializedName("message")
    val message: String,

    @field:SerializedName("status")
    val status: String,

    @field:SerializedName("user")
    val user: List<DetailUser>
)

data class DetailUser(

    @field:SerializedName("fullName")
    val fullName: String? = null,

    @field:SerializedName("email")
    val email: String? = null,

    @field:SerializedName("username")
    val username: String? = null,

    @field:SerializedName("profilePicture")
    val profilePicture: String? = null

)
