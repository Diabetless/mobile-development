package com.CH2PS073.diabetless.data.local.user.di

import android.content.Context
import com.CH2PS073.diabetless.data.local.user.pref.UserPreference
import com.CH2PS073.diabetless.data.local.user.pref.UserRepository
import com.CH2PS073.diabetless.data.local.user.pref.dataStore

object Injection {
    fun provideRepository(context: Context): UserRepository {
        val pref = UserPreference.getInstance(context.dataStore)
        return UserRepository.getInstance(pref)
    }
}