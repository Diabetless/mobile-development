package com.ch2Ps073.diabetless.data.remote.response

import com.google.gson.annotations.SerializedName

data class UpdateUserResponse(
    @field:SerializedName("message")
    val message: String,

    @field:SerializedName("status")
    val status: String,
)
