package com.ch2Ps073.diabetless.ui.main.ui.mealPlanner.mealDetail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asFlow
import androidx.lifecycle.viewModelScope
import com.ch2Ps073.diabetless.data.local.db.RoomRepository
import com.ch2Ps073.diabetless.data.local.user.pref.UserRepository
import com.ch2Ps073.diabetless.data.remote.response.Meal
import com.ch2Ps073.diabetless.data.remote.response.MealCart
import com.ch2Ps073.diabetless.utils.Event
import kotlinx.coroutines.launch
import com.ch2Ps073.diabetless.utils.Result

class MealDetailViewModel(
    private val id: String,
    private val repository: UserRepository,
    private val roomRepository: RoomRepository
) : ViewModel() {
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _detailMeal = MutableLiveData<Meal>()
    val detailMeal: LiveData<Meal> = _detailMeal

    private val _toastMessage = MutableLiveData<Event<String>>()
    val toastMessage: LiveData<Event<String>> = _toastMessage

    val isFavorited = MutableLiveData<Boolean>()
    val isInCart = MutableLiveData<Boolean>()

    init {
        getDetailMeal(id)
        checkFavorite()
        checkMealCart()
    }

    private fun getDetailMeal(id: String) {
        viewModelScope.launch {
            repository.getDetailMeal(id).asFlow().collect { result ->
                when (result) {
                    is Result.Loading -> {
                        _isLoading.postValue(true)
                    }

                    is Result.Success -> {
                        _isLoading.postValue(false)
                        _detailMeal.postValue(result.data.meal!!)
                    }

                    is Result.Error -> {
                        _isLoading.postValue(false)
                        _toastMessage.postValue(Event(result.error))
                    }
                }
            }
        }
    }

    fun insertFavorite(meal: Meal) {
        roomRepository.insert(meal)
    }

    fun deleteFavorite() {
        roomRepository.delete(id)
    }

    fun checkFavorite() {
        viewModelScope.launch {
            roomRepository.checkFavorite(id).asFlow().collect {
                isFavorited.postValue(it.isNotEmpty())
            }
        }
    }

    fun insertMealCart(meal: MealCart) {
        roomRepository.insertMealCart(meal)
    }

    fun deleteMealCart() {
        roomRepository.deleteMealCart(id)
    }

    fun checkMealCart() {
        viewModelScope.launch {
            roomRepository.checkMealCart(id).asFlow().collect {
                isInCart.postValue(it.isNotEmpty())
            }
        }
    }
}