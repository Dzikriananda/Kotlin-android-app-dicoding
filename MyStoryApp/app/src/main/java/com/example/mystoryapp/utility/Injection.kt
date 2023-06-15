package com.example.mystoryapp.utility

import android.content.Context
import com.example.mystoryapp.network.ApiConfig

object Injection {
    fun provideRepository(context: Context): Repository {
        val apiService = ApiConfig.getApiService()
        return Repository(apiService)
    }
}