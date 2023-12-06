package com.ch2Ps073.diabetless.ui.main.ui.mealPlanner.mealCart

import androidx.lifecycle.ViewModel
import com.ch2Ps073.diabetless.data.local.db.RoomRepository

class MealCartViewModel(private val roomRepository: RoomRepository) : ViewModel() {
    fun getAllMealCart() = roomRepository.getMealCart()

    fun deleteMealCart(id: String) {
        roomRepository.deleteMealCart(id)
    }
}