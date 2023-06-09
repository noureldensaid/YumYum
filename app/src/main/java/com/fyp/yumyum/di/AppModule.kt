package com.fyp.yumyum.di

import android.content.Context
import androidx.room.Room
import com.fyp.yumyum.data.local.MealsDatabase
import com.fyp.yumyum.data.remote.MealsApi
import com.fyp.yumyum.data.repositorey.Repository
import com.fyp.yumyum.utils.Constants
import com.fyp.yumyum.utils.Constants.Companion.DATABASE_NAME
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit =
        Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    @Provides
    @Singleton
    fun provideMealsApi(retrofit: Retrofit): MealsApi =
        retrofit.create(MealsApi::class.java)


    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): MealsDatabase =
        Room.databaseBuilder(
            context,
            MealsDatabase::class.java,
            DATABASE_NAME
        ).build()

    @Singleton
    @Provides
    fun provideDataStoreRepository(
        @ApplicationContext app: Context, mealsApi: MealsApi, mealsDatabase: MealsDatabase
    ): Repository = Repository(
        app, mealsApi, mealsDatabase
    )


}