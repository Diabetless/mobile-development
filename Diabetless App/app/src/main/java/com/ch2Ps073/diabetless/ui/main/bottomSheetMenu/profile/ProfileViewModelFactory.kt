package com.ch2Ps073.diabetless.ui.main.bottomSheetMenu.profile

import android.annotation.SuppressLint
import android.content.Context
import androidx.lifecycle.LifecycleCoroutineScope
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.ch2Ps073.diabetless.ui.ViewModelFactory

class ProfileViewModelFactory(private val context: Context,
                             private val lifecycleScope: LifecycleCoroutineScope
) :
    ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ProfileSettingViewModel::class.java)) {
            return ProfileSettingViewModel(context, lifecycleScope) as T
        } else
            throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
    }

    companion object {
        @SuppressLint("StaticFieldLeak")
        @Volatile
        private var INSTANCE: ProfileViewModelFactory? = null

        @JvmStatic
        fun getInstance(context: Context, lifecycleScope: LifecycleCoroutineScope): ProfileViewModelFactory {
            if (INSTANCE == null) {
                synchronized(ViewModelFactory::class.java) {
                    INSTANCE = ProfileViewModelFactory(context, lifecycleScope)
                }
            }
            return INSTANCE as ProfileViewModelFactory
        }
    }
}