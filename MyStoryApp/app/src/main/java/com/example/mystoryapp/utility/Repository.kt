package com.example.mystoryapp.utility

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.example.mystoryapp.network.ApiService
import com.example.mystoryapp.response.GetStoriesResponse
import com.example.mystoryapp.response.LoginResponse
import com.example.mystoryapp.response.RegisterResponse

class Repository(private val apiService: ApiService) {
    fun register(name: String, email: String, password: String): LiveData<Result<RegisterResponse>> = liveData{
        try {
            val response = apiService.register(name, email, password)
            emit(Result.Success(response))
        }
        catch(e: Exception){
            emit(Result.Error(e.toString()))
        }
    }

    fun login(email: String, password: String): LiveData<Result<LoginResponse>> = liveData{
        try {

            val response = apiService.login(email, password)
            emit(Result.Success(response))
        }
        catch(e: Exception){
            emit(Result.Error(e.toString()))
        }
    }

    fun getStories(token:String): LiveData<Result<GetStoriesResponse>> = liveData {
        try {
            Log.i("result","mencoba")
            val response = apiService.get_profile("Bearer $token")
            emit(Result.Success(response))
        }
        catch(e: Exception){
            Log.i("result","gagal")
            emit(Result.Error(e.toString()))
        }
    }
}