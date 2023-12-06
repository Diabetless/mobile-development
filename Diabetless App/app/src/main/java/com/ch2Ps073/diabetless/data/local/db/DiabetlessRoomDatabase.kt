package com.ch2Ps073.diabetless.data.local.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.ch2Ps073.diabetless.data.remote.response.Meal
import com.ch2Ps073.diabetless.data.remote.response.MealCart

@Database(entities = [Meal::class, MealCart::class], version = 1, exportSchema = true)
abstract class DiabetlessRoomDatabase : RoomDatabase() {
    abstract fun favDao(): FavoriteDao
    abstract fun mealCartDao(): MealsDao

    companion object {
        @Volatile
        private var INSTANCE: DiabetlessRoomDatabase? = null

        @JvmStatic
        fun getInstance(context: Context): DiabetlessRoomDatabase {
            if (INSTANCE == null) {
                synchronized(DiabetlessRoomDatabase::class.java) {
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        DiabetlessRoomDatabase::class.java, "db_diabetless"
                    )
                        .build()
                }
            }
            return INSTANCE as DiabetlessRoomDatabase
        }
    }
}