package com.ch2Ps073.diabetless.data.local.user.di

import android.content.Context
import com.ch2Ps073.diabetless.data.local.user.pref.UserPreference
import com.ch2Ps073.diabetless.data.local.user.pref.UserRepository
import com.ch2Ps073.diabetless.data.local.user.pref.dataStore
import com.ch2Ps073.diabetless.data.remote.retrofit.ApiConfig

object Injection {
    fun provideRepository(context: Context): UserRepository {
        val pref = UserPreference.getInstance(context.dataStore)
        val apiService = ApiConfig.getApiService()
        return UserRepository.getInstance(pref, apiService)
    }
}