package com.fyp.yumyum.ui.main

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fyp.yumyum.data.repositorey.Repository
import com.fyp.yumyum.models.Category
import com.fyp.yumyum.models.Meal
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: Repository
) : ViewModel() {

    private val _searchData: MutableLiveData<List<Meal>> = MutableLiveData()
    val searchData: LiveData<List<Meal>> = _searchData



    private val _data: MutableLiveData<List<Category>> = MutableLiveData()
    val data: LiveData<List<Category>> = _data


    private val _mealData: MutableLiveData<List<Meal>> = MutableLiveData()
    val mealData: LiveData<List<Meal>> = _mealData


    init {
        getCategories()
        getFav()
    }


    private fun getCategories() = viewModelScope.launch {
        try {
            val response = repository.getCategories()
            if (response.isSuccessful) {
                _data.postValue(response.body()?.categories)
                Log.e("Great request", "getData: Great")
            } else Log.e("Failed request", "getData: Failed")
        } catch (ex: Exception) {
            Log.e("TAG", ex.message.toString());
        }
    }

    fun getMeals(category: String) = viewModelScope.launch {
        try {
            val response = repository.getMeals(category)
            if (response.isSuccessful) {
                _mealData.postValue(response.body()?.meals)
                Log.e("Great request", "getData: Great")
            } else Log.e("Failed request", "getData: Failed")
        } catch (ex: Exception) {
            Log.e("TAG", ex.message.toString());
        }


    }

    fun search(query: String) = viewModelScope.launch {

        val response = repository.searchForMeal(query)
        _searchData.postValue(response.body()?.meals)

    }


    fun addFav(meal: Meal) = viewModelScope.launch { repository.addFav(meal) }

    fun removeFav(meal: Meal) = viewModelScope.launch { repository.removeFav(meal) }

    fun getFav() = repository.getFav()


}