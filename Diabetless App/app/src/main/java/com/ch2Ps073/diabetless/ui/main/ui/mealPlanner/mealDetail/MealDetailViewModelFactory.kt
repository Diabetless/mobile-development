package com.ch2Ps073.diabetless.ui.main.ui.mealPlanner.mealDetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.ch2Ps073.diabetless.data.local.db.RoomRepository
import com.ch2Ps073.diabetless.data.local.user.pref.UserRepository

class MealDetailViewModelFactory(
    private val id: String,
    private val userRepository: UserRepository,
    private val roomRepository: RoomRepository
) :
    ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MealDetailViewModel::class.java)) {
            return MealDetailViewModel(id, userRepository, roomRepository) as T
        } else
            throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
    }
}