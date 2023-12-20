package com.ch2Ps073.diabetless.ui.main.ui.health.bloodSugar

import android.annotation.SuppressLint
import android.content.Context
import androidx.lifecycle.LifecycleCoroutineScope
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.ch2Ps073.diabetless.ui.ViewModelFactory

class BloodSugarViewModelFactory(private val context: Context,
                              private val lifecycleScope: LifecycleCoroutineScope
) :
    ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(BloodSugarViewModel::class.java)) {
            return BloodSugarViewModel(context, lifecycleScope) as T
        } else
            throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
    }

    companion object {
        @SuppressLint("StaticFieldLeak")
        @Volatile
        private var INSTANCE: BloodSugarViewModelFactory? = null

        @JvmStatic
        fun getInstance(context: Context, lifecycleScope: LifecycleCoroutineScope): BloodSugarViewModelFactory {
            if (INSTANCE == null) {
                synchronized(ViewModelFactory::class.java) {
                    INSTANCE = BloodSugarViewModelFactory(context, lifecycleScope)
                }
            }
            return INSTANCE as BloodSugarViewModelFactory
        }
    }
}