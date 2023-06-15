package com.example.mystoryapp.viewmodels

import androidx.lifecycle.ViewModel
import com.example.mystoryapp.utility.Repository

class LoginViewModel(private val repository: Repository): ViewModel() {

    fun login(email: String, password: String) = repository.login(email,password)

}