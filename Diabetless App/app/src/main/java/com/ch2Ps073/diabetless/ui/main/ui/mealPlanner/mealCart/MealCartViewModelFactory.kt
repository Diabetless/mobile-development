package com.ch2Ps073.diabetless.ui.main.ui.mealPlanner.mealCart

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.ch2Ps073.diabetless.data.local.db.RoomRepository
import com.ch2Ps073.diabetless.data.local.user.pref.UserRepository

class MealCartViewModelFactory(
    private val roomRepository: RoomRepository
) :
    ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MealCartViewModel::class.java)) {
            return MealCartViewModel(roomRepository) as T
        } else
            throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
    }
}