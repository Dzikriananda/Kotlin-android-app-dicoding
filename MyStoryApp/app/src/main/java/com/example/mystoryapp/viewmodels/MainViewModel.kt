package com.example.mystoryapp.viewmodels

import androidx.lifecycle.ViewModel
import com.example.mystoryapp.utility.Repository

class MainViewModel(private val repository: Repository): ViewModel() {

    fun getStories(token: String) = repository.getStories(token)

}