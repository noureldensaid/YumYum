package com.fyp.yumyum.data.repositorey

import com.fyp.yumyum.data.local.MealsDatabase
import com.fyp.yumyum.data.remote.MealsApi
import com.fyp.yumyum.models.Meal
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class Repository @Inject constructor(
    private val mealsApi: MealsApi,
    private val mealsDatabase: MealsDatabase
) {


    suspend fun getCategories() = mealsApi.getCategories()

    suspend fun getMeals(category: String) = mealsApi.getMeals(category)

    suspend fun searchForMeal(meal: String) = mealsApi.searchForMeal(meal)

    suspend fun addFav(meal: Meal) = mealsDatabase.mealsDao().insert(meal)

    suspend fun removeFav(meal: Meal) = mealsDatabase.mealsDao().delete(meal)

    fun getFav() = mealsDatabase.mealsDao().getFavMeals()

}