package com.ch2Ps073.diabetless.ui.register

import android.annotation.SuppressLint
import android.content.Context
import androidx.lifecycle.LifecycleCoroutineScope
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.ch2Ps073.diabetless.ui.ViewModelFactory

class SignupViewModelFactory(private val context: Context,
                             private val lifecycleScope: LifecycleCoroutineScope
) :
    ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SignupViewModel::class.java)) {
            return SignupViewModel(context, lifecycleScope) as T
        } else
            throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
    }

    companion object {
        @SuppressLint("StaticFieldLeak")
        @Volatile
        private var INSTANCE: SignupViewModelFactory? = null

        @JvmStatic
        fun getInstance(context: Context, lifecycleScope: LifecycleCoroutineScope): SignupViewModelFactory {
            if (INSTANCE == null) {
                synchronized(ViewModelFactory::class.java) {
                    INSTANCE = SignupViewModelFactory(context, lifecycleScope)
                }
            }
            return INSTANCE as SignupViewModelFactory
        }
    }
}