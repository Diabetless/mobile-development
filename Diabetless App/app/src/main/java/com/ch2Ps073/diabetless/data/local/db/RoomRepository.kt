package com.ch2Ps073.diabetless.data.local.db

import android.app.Application
import com.ch2Ps073.diabetless.data.remote.response.Meal
import com.ch2Ps073.diabetless.data.remote.response.MealCart
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class RoomRepository(application: Application) {
    private val favoriteDao: FavoriteDao
    private val mealCartDao: MealsDao
    private val executorService: ExecutorService = Executors.newSingleThreadExecutor()

    init {
        val db = DiabetlessRoomDatabase.getInstance(application)
        favoriteDao = db.favDao()
        mealCartDao = db.mealCartDao()
    }

    fun insert(meal: Meal) {
        executorService.execute { favoriteDao.insert(meal) }
    }

    fun delete(id: String) {
        executorService.execute { favoriteDao.delete(id) }
    }

    fun getFavorites() = favoriteDao.getAllFavorites()

    fun checkFavorite(id: String) = favoriteDao.checkFavorite(id)

    fun insertMealCart(meal: MealCart) {
        executorService.execute { mealCartDao.insertMealCart(meal) }
    }

    fun deleteMealCart(id: String) {
        executorService.execute { mealCartDao.deleteMealCart(id) }
    }

    fun getMealCart() = mealCartDao.getAllMealCart()

    fun checkMealCart(id: String) = mealCartDao.checkMealCart(id)
}