package com.ch2Ps073.diabetless.ui.main.ui.mealPlanner

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asFlow
import androidx.lifecycle.viewModelScope
import com.ch2Ps073.diabetless.data.local.user.pref.UserRepository
import com.ch2Ps073.diabetless.data.remote.response.ArticleItem
import com.ch2Ps073.diabetless.data.remote.response.MealItem
import com.ch2Ps073.diabetless.utils.Event
import com.ch2Ps073.diabetless.utils.Result
import kotlinx.coroutines.launch

class MealPlanIndexViewModel(private val repository: UserRepository) : ViewModel() {

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _allMeals = MutableLiveData<ArrayList<MealItem>>()
    val allMeals: LiveData<ArrayList<MealItem>> = _allMeals

    private val _toastMessage = MutableLiveData<Event<String>>()
    val toastMessage: LiveData<Event<String>> = _toastMessage

    init {
        getAllMeals()
    }

    private fun getAllMeals() {
        viewModelScope.launch {
            repository.getMeals().asFlow().collect { result ->
                when (result) {
                    is Result.Loading -> {
                        _isLoading.postValue(true)
                    }

                    is Result.Success -> {
                        _isLoading.postValue(false)
                        _allMeals.postValue(result.data.meal as ArrayList<MealItem>?)
                    }

                    is Result.Error -> {
                        _isLoading.postValue(false)
                        Log.e("MealPlanIndex", "getAllMeals: ${result.error}", )
                        _toastMessage.postValue(Event(result.error))
                    }
                }
            }
        }
    }
}