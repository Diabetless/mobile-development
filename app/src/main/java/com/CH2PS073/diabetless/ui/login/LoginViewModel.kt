package com.CH2PS073.diabetless.ui.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.CH2PS073.diabetless.data.local.user.pref.UserModel
import com.CH2PS073.diabetless.data.local.user.pref.UserRepository
import kotlinx.coroutines.launch
class LoginViewModel(private val repository: UserRepository) : ViewModel() {
    fun saveSession(user: UserModel) {
        viewModelScope.launch {
            repository.saveSession(user)
        }
    }
}