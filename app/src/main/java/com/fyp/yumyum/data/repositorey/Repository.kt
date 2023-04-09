package com.fyp.yumyum.data.repositorey

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.fyp.yumyum.data.local.MealsDatabase
import com.fyp.yumyum.data.remote.MealsApi
import com.fyp.yumyum.models.Meal
import com.fyp.yumyum.models.MealDetails
import com.fyp.yumyum.utils.Constants.Companion.USER_DATA
import kotlinx.coroutines.flow.first
import javax.inject.Inject
import javax.inject.Singleton

private val Context.dataStore: DataStore<Preferences>
        by preferencesDataStore(name = USER_DATA)

@Singleton
class Repository @Inject constructor(
    private val context: Context,
    private val mealsApi: MealsApi,
    private val mealsDatabase: MealsDatabase
) {

    suspend fun saveUserName(key: String, value: String) {
        val preferencesKey = stringPreferencesKey(key)
        context.dataStore.edit { userDataPreferences ->
            userDataPreferences[preferencesKey] = value
        }
    }

    suspend fun getUserName(key: String): String? {
        return try {
            val preferencesKey = stringPreferencesKey(key)
            val userDataPreferences = context.dataStore.data.first()
            userDataPreferences[preferencesKey]
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }


    suspend fun getMealDetails(id: String): MealDetails? {
        val response = mealsApi.getMealDetails(id)
        return response.body()?.meals?.get(0)

    }

    suspend fun getCategories() = mealsApi.getCategories()

    suspend fun getMeals(category: String) = mealsApi.getMeals(category)

    suspend fun searchForMeal(meal: String) = mealsApi.searchForMeal(meal)

    suspend fun addFav(meal: Meal) = mealsDatabase.mealsDao().insert(meal)

    suspend fun removeFav(meal: Meal) = mealsDatabase.mealsDao().delete(meal)

     fun getFav() = mealsDatabase.mealsDao().getFavMeals()

}