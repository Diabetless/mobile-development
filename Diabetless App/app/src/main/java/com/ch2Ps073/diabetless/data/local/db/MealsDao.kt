package com.ch2Ps073.diabetless.data.local.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.ch2Ps073.diabetless.data.remote.response.Meal
import com.ch2Ps073.diabetless.data.remote.response.MealCart

@Dao
interface MealsDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertMealCart(meal: MealCart)

    @Query("DELETE FROM meal_cart WHERE id =:id")
    fun deleteMealCart(id: String)

    @Query("SELECT * FROM meal_cart")
    fun getAllMealCart(): LiveData<List<MealCart>>

    @Query("SELECT * FROM meal_cart WHERE id =:id")
    fun checkMealCart(id: String): LiveData<List<MealCart>>
}