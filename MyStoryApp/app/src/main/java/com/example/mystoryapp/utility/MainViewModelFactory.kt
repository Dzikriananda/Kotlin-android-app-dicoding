package com.example.mystoryapp.utility

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.mystoryapp.viewmodels.MainViewModel


class MainViewModelFactory(private val storyRepository: StoryRepository): ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            return MainViewModel(storyRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel Class: ${modelClass.name}")
    }

    companion object{
        @Volatile
        private var instance: MainViewModelFactory? = null
        fun getInstance(context: Context): MainViewModelFactory =
            instance ?: synchronized(this) {
                instance ?: MainViewModelFactory(Injection.providePagingRepository(context))
            }.also {
                instance = it
            }
    }
}