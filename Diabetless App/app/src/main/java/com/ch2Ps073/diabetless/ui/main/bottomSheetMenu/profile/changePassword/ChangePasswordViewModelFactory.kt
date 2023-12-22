package com.ch2Ps073.diabetless.ui.main.bottomSheetMenu.profile.changePassword

import android.annotation.SuppressLint
import android.content.Context
import androidx.lifecycle.LifecycleCoroutineScope
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.ch2Ps073.diabetless.ui.ViewModelFactory

class ChangePasswordViewModelFactory(private val context: Context,
                                     private val lifecycleScope: LifecycleCoroutineScope
) :
    ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ChangePasswordViewModel::class.java)) {
            return ChangePasswordViewModel(context, lifecycleScope) as T
        } else
            throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
    }

    companion object {
        @SuppressLint("StaticFieldLeak")
        @Volatile
        private var INSTANCE: ChangePasswordViewModelFactory? = null

        @JvmStatic
        fun getInstance(context: Context, lifecycleScope: LifecycleCoroutineScope): ChangePasswordViewModelFactory {
            if (INSTANCE == null) {
                synchronized(ViewModelFactory::class.java) {
                    INSTANCE = ChangePasswordViewModelFactory(context, lifecycleScope)
                }
            }
            return INSTANCE as ChangePasswordViewModelFactory
        }
    }
}