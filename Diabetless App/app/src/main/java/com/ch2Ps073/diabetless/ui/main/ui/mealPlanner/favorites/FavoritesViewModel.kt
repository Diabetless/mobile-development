package com.ch2Ps073.diabetless.ui.main.ui.mealPlanner.favorites

import androidx.lifecycle.ViewModel
import com.ch2Ps073.diabetless.data.local.db.RoomRepository

class FavoritesViewModel(private val roomRepository: RoomRepository) : ViewModel() {
    fun getAllFavorites() = roomRepository.getFavorites()
}