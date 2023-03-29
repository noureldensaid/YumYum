package com.fyp.yumyum.models

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.fyp.yumyum.utils.Constants.Companion.TABLE_NAME
import kotlinx.parcelize.Parcelize

@Entity(tableName = TABLE_NAME)
@Parcelize
data class Meal(
    @PrimaryKey(autoGenerate = false)
    val idMeal: String,
    val strMeal: String,
    val strMealThumb: String,
    var isFavorite: Boolean = false
) : Parcelable