package com.ch2Ps073.diabetless.ui.main.ui.glycemic

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class GlycemicViewModelFactory() : ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(GlycemicViewModel::class.java)) {
            return GlycemicViewModel() as T
        } else
            throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
    }
}