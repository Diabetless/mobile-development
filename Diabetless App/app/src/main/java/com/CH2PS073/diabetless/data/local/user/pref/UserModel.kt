package com.CH2PS073.diabetless.data.local.user.pref

data class UserModel(
    val email: String,
    val name: String,
    val token: String,
    val isLogin: Boolean = false
)