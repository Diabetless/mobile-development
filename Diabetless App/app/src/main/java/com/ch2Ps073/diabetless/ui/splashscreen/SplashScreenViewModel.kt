package com.ch2Ps073.diabetless.ui.splashscreen

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.ch2Ps073.diabetless.data.local.user.pref.UserModel
import com.ch2Ps073.diabetless.data.local.user.pref.UserRepository
import kotlinx.coroutines.launch

class SplashScreenViewModel(private val repository: UserRepository): ViewModel() {
    fun getSession(): LiveData<UserModel> {
        return repository.getSession().asLiveData()
    }
}