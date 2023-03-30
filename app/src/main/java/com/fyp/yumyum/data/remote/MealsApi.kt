package com.fyp.yumyum.data.remote

import com.fyp.yumyum.models.CategoryResponse
import com.fyp.yumyum.models.MealDetailsResponse
import com.fyp.yumyum.models.MealResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface MealsApi {

    @GET("categories.php")
    suspend fun getCategories(): Response<CategoryResponse>

    @GET("filter.php")
    suspend fun getMeals(
        @Query("c") category: String
    ): Response<MealResponse>


    @GET("search.php")
    suspend fun searchForMeal(
        @Query("s") meal: String
    ): Response<MealResponse>


    @GET("lookup.php")
    suspend fun getMealDetails(
        @Query("i") id: String
    ): Response<MealDetailsResponse>



}