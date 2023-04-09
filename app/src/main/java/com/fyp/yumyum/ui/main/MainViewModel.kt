package com.fyp.yumyum.ui.main

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fyp.yumyum.data.repositorey.Repository
import com.fyp.yumyum.models.Category
import com.fyp.yumyum.models.Meal
import com.fyp.yumyum.models.MealDetails
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val app: Application,
    private val repository: Repository
) : ViewModel() {

    var allFavorites: LiveData<List<Meal>>

    init {
        getCategories()
        allFavorites = repository.getFav()
    }

    private val _searchData: MutableLiveData<List<Meal>> = MutableLiveData()
    val searchData: LiveData<List<Meal>> = _searchData

    private val _mealDetails: MutableLiveData<MealDetails?> = MutableLiveData()
    val mealDetails: LiveData<MealDetails?> = _mealDetails


    private val _data: MutableLiveData<List<Category>> = MutableLiveData()
    val data: LiveData<List<Category>> = _data


    private val _mealData: MutableLiveData<List<Meal>> = MutableLiveData()
    val mealData: LiveData<List<Meal>> = _mealData

    val mealId: MutableLiveData<String> = MutableLiveData()


    private fun getCategories() = viewModelScope.launch {
        try {
            val response = repository.getCategories()
            if (response.isSuccessful) {
                _data.postValue(response.body()?.categories)
                Log.e("Great request", "getData: Great")
            } else Log.e("Failed request", "getData: Failed")
        } catch (ex: Exception) {
            Log.e("TAG", ex.message.toString())
        }
    }

    fun getMeals(category: String) = viewModelScope.launch {
        try {
            val response = repository.getMeals(category)
            if (response.isSuccessful) {
                _mealData.postValue(response.body()?.meals)
                Log.e("Great request", "getData: Great")
            } else Log.e("Failed request", response.errorBody().toString())
        } catch (ex: Exception) {
            Log.e("TAG", ex.message.toString())
        }


    }

    fun search(query: String) = viewModelScope.launch {
        if (query.isNotEmpty()) {
            val response = repository.searchForMeal(query.trim())
            _searchData.postValue(response.body()?.meals)
        } else {
            _searchData.postValue(emptyList())
        }
    }

    fun getMealDetails(id: String) = viewModelScope.launch {
        val response = repository.getMealDetails(id)
        _mealDetails.postValue(response)

    }

    fun addFav(meal: Meal) = viewModelScope.launch { repository.addFav(meal) }

    fun removeFav(meal: Meal) = viewModelScope.launch { repository.removeFav(meal) }


    fun update(meal: Meal) =
        viewModelScope.launch {
            repository.update(meal)
        }

    fun clearSearchResults() {
        _searchData.value = emptyList()
    }


    fun saveUserName(value: String) {
        viewModelScope.launch {
            repository.saveUserName("userName", value)
        }
    }

    fun getUserName(): String? = runBlocking {
        repository.getUserName("userName")
    }


    fun isInternetConnected(): Boolean {
        val connectivityManager =
            app.applicationContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val network = connectivityManager.activeNetwork ?: return false
            val networkCapabilities =
                connectivityManager.getNetworkCapabilities(network) ?: return false

            return networkCapabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
        } else {
            val networkInfo = connectivityManager.activeNetworkInfo ?: return false

            return networkInfo.isConnected
        }
    }


    override fun onCleared() {
        super.onCleared()
        clearSearchResults()
    }
}