package com.ch2Ps073.diabetless.ui.main.ui.health.bodyComposition

import android.annotation.SuppressLint
import android.content.Context
import androidx.lifecycle.LifecycleCoroutineScope
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.ch2Ps073.diabetless.ui.ViewModelFactory

class BodyCompositionViewModelFactory(private val context: Context,
                                      private val lifecycleScope: LifecycleCoroutineScope
) :
    ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(BodyCompositionViewModel::class.java)) {
            return BodyCompositionViewModel(context, lifecycleScope) as T
        } else
            throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
    }

    companion object {
        @SuppressLint("StaticFieldLeak")
        @Volatile
        private var INSTANCE: BodyCompositionViewModelFactory? = null

        @JvmStatic
        fun getInstance(context: Context, lifecycleScope: LifecycleCoroutineScope): BodyCompositionViewModelFactory {
            if (INSTANCE == null) {
                synchronized(ViewModelFactory::class.java) {
                    INSTANCE = BodyCompositionViewModelFactory(context, lifecycleScope)
                }
            }
            return INSTANCE as BodyCompositionViewModelFactory
        }
    }
}