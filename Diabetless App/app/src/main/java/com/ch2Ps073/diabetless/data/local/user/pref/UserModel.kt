package com.ch2Ps073.diabetless.data.local.user.pref

data class UserModel(
    val email: String,
    val name: String,
    val token: String,
    val isLogin: Boolean = false
)