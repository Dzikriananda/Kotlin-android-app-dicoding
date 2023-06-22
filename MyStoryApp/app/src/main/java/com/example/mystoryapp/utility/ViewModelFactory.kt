package com.example.mystoryapp.utility

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.mystoryapp.viewmodels.AddStoryViewModel
import com.example.mystoryapp.viewmodels.LoginViewModel
import com.example.mystoryapp.viewmodels.MainViewModel
import com.example.mystoryapp.viewmodels.RegisterViewModel

class ViewModelFactory(private val repository: Repository): ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(RegisterViewModel::class.java)) {
            return RegisterViewModel(repository) as T
        }  else if (modelClass.isAssignableFrom(LoginViewModel::class.java)) {
            return LoginViewModel(repository) as T
        } else if (modelClass.isAssignableFrom(AddStoryViewModel::class.java)) {
            return AddStoryViewModel(repository) as T
        }



        throw IllegalArgumentException("Unknown ViewModel Class: ${modelClass.name}")
    }

    companion object{
        @Volatile
        private var instance: ViewModelFactory? = null
        fun getInstance(context: Context): ViewModelFactory =
            instance ?: synchronized(this) {
                instance ?: ViewModelFactory(Injection.provide_Repository(context))
            }.also {
                instance = it
            }
    }
}