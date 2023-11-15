package com.ch2Ps073.diabetless.data.local.user.di

import android.content.Context
import com.ch2Ps073.diabetless.data.local.user.pref.UserPreference
import com.ch2Ps073.diabetless.data.local.user.pref.UserRepository
import com.ch2Ps073.diabetless.data.local.user.pref.dataStore

object Injection {
    fun provideRepository(context: Context): UserRepository {
        val pref = UserPreference.getInstance(context.dataStore)
        return UserRepository.getInstance(pref)
    }
}