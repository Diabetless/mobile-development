package com.ch2Ps073.diabetless.ui.main.ui.health

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class HealthViewModel : ViewModel() {
    private val _text = MutableLiveData<String>().apply {
        value = "This is Health Fragment"
    }
    val text: LiveData<String> = _text
}