package com.CH2PS073.diabetless.data.local.user.di

import android.content.Context
import com.CH2PS073.diabetless.data.local.user.pref.UserPreference
import com.CH2PS073.diabetless.data.local.user.pref.UserRepository
import com.CH2PS073.diabetless.data.local.user.pref.dataStore
import com.CH2PS073.diabetless.data.remote.ApiConfig
import com.CH2PS073.diabetless.data.remote.ApiService

object Injection {
    fun provideRepository(context: Context): UserRepository {
        val pref = UserPreference.getInstance(context.dataStore)
        val apiService = ApiConfig.getApiService()
        return UserRepository.getInstance(pref, apiService)
    }
}