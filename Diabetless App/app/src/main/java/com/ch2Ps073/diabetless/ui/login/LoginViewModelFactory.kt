package com.ch2Ps073.diabetless.ui.login

import android.annotation.SuppressLint
import android.content.Context
import androidx.lifecycle.LifecycleCoroutineScope
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.ch2Ps073.diabetless.data.local.user.di.Injection
import com.ch2Ps073.diabetless.data.local.user.pref.UserRepository
import com.ch2Ps073.diabetless.ui.ViewModelFactory
import com.ch2Ps073.diabetless.ui.main.ui.mealPlanner.mealDetail.MealDetailViewModel

class LoginViewModelFactory(private val context: Context,
                            private val lifecycleScope: LifecycleCoroutineScope,
                            private val repository: UserRepository) :
    ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(LoginViewModel::class.java)) {
            return LoginViewModel(context, lifecycleScope, repository) as T
        } else
            throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
    }

    companion object {
        @SuppressLint("StaticFieldLeak")
        @Volatile
        private var INSTANCE: LoginViewModelFactory? = null

        @JvmStatic
        fun getInstance(context: Context, lifecycleScope: LifecycleCoroutineScope): LoginViewModelFactory {
            if (INSTANCE == null) {
                synchronized(ViewModelFactory::class.java) {
                    INSTANCE = LoginViewModelFactory(context, lifecycleScope, Injection.provideRepository(context))
                }
            }
            return INSTANCE as LoginViewModelFactory
        }
    }
}