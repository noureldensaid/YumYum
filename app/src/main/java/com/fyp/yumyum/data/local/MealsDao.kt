package com.fyp.yumyum.data.local

import androidx.lifecycle.LiveData
import androidx.room.*
import com.fyp.yumyum.models.Meal

@Dao
interface MealsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(meal: Meal): Long

    @Query("SELECT * FROM meals_table ")
    fun getFavMeals(): LiveData<List<Meal>>


    @Update
    suspend fun updateItem(meal: Meal)

    @Delete
    suspend fun delete(meal: Meal)


}