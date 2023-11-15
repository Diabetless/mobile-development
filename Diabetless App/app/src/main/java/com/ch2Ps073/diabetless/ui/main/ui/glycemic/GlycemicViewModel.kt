package com.ch2Ps073.diabetless.ui.main.ui.glycemic

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class GlycemicViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is Glycemic Index Camera Fragment"
    }
    val text: LiveData<String> = _text
}