package com.ch2Ps073.diabetless.data.local.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.ch2Ps073.diabetless.data.remote.response.Meal

@Dao
interface FavoriteDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(meal: Meal)

    @Query("DELETE FROM favorited_meal WHERE id =:id")
    fun delete(id: String)

    @Query("SELECT * FROM favorited_meal")
    fun getAllFavorites(): LiveData<List<Meal>>

    @Query("SELECT * FROM favorited_meal WHERE id =:id")
    fun checkFavorite(id: String): LiveData<List<Meal>>
}