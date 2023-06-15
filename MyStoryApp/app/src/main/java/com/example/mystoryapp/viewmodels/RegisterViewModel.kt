package com.example.mystoryapp.viewmodels

import androidx.lifecycle.ViewModel
import com.example.mystoryapp.utility.Repository

class RegisterViewModel(private val repository: Repository): ViewModel() {


    fun register(name: String,email: String,password: String) = repository.register(name,email,password)



}