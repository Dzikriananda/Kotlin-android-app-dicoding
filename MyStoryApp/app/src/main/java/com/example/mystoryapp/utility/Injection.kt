package com.example.mystoryapp.utility

import android.content.Context
import com.example.mystoryapp.network.ApiConfig

object Injection {
    fun provide_Repository(context: Context): Repository {
        val apiService = ApiConfig.getApiService()
        return Repository(apiService)
    }

    fun providePagingRepository(context: Context): StoryRepository{
        val apiService = ApiConfig.getApiService()
        return StoryRepository(apiService,context)

    }
}