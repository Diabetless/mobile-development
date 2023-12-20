package com.ch2Ps073.diabetless.ui.splashscreen

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.ch2Ps073.diabetless.data.local.user.pref.UserModel
import com.ch2Ps073.diabetless.data.local.user.pref.UserRepository

class SplashScreenViewModel(private val repository: UserRepository): ViewModel() {
    fun getSession(): LiveData<UserModel> {
        return repository.getSession().asLiveData()
    }
}