package com.ch2Ps073.diabetless.ui.main.ui.mealPlanner

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MealPlanIndexViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is Meal Planner Fragment"
    }
    val text: LiveData<String> = _text
}