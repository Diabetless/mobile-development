package com.ch2Ps073.diabetless.ui

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.ch2Ps073.diabetless.data.local.user.di.Injection
import com.ch2Ps073.diabetless.data.local.user.pref.UserRepository
import com.ch2Ps073.diabetless.ui.articles.ArticlesViewModel
import com.ch2Ps073.diabetless.ui.login.LoginViewModel
import com.ch2Ps073.diabetless.ui.main.MainViewModel
import com.ch2Ps073.diabetless.ui.main.bottomSheetMenu.profile.ProfileSettingViewModel
import com.ch2Ps073.diabetless.ui.main.ui.mealPlanner.MealPlanIndexViewModel
import com.ch2Ps073.diabetless.ui.splashscreen.SplashScreenViewModel

class ViewModelFactory(private val repository: UserRepository) :
    ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(LoginViewModel::class.java) -> {
                LoginViewModel(repository) as T
            }

            modelClass.isAssignableFrom(MainViewModel::class.java) -> {
                MainViewModel(repository) as T
            }

            modelClass.isAssignableFrom(ArticlesViewModel::class.java) -> {
                ArticlesViewModel(repository) as T
            }

            modelClass.isAssignableFrom(ProfileSettingViewModel::class.java) -> {
                ProfileSettingViewModel() as T
            }

            modelClass.isAssignableFrom(MealPlanIndexViewModel::class.java) -> {
                MealPlanIndexViewModel(repository) as T
            }

            modelClass.isAssignableFrom(SplashScreenViewModel::class.java) -> {
                SplashScreenViewModel(repository) as T
            }

            else -> throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
        }
    }

    companion object {
        @Volatile
        private var INSTANCE: ViewModelFactory? = null

        @JvmStatic
        fun getInstance(context: Context): ViewModelFactory {
            if (INSTANCE == null) {
                synchronized(ViewModelFactory::class.java) {
                    INSTANCE = ViewModelFactory(Injection.provideRepository(context))
                }
            }
            return INSTANCE as ViewModelFactory
        }
    }
}