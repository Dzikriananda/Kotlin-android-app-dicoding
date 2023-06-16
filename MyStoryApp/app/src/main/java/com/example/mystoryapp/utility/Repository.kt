package com.example.mystoryapp.utility

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.example.mystoryapp.network.ApiService
import com.example.mystoryapp.response.AddStoryResponse
import com.example.mystoryapp.response.GetStoriesResponse
import com.example.mystoryapp.response.LoginResponse
import com.example.mystoryapp.response.RegisterResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody

class Repository(private val apiService: ApiService) {
    fun register(name: String, email: String, password: String): LiveData<Result<RegisterResponse>> = liveData{
        emit(Result.Loading)
        try {
            val response = apiService.register(name, email, password)
            emit(Result.Success(response))
        }
        catch(e: Exception){
            emit(Result.Error(e.toString()))
        }
    }

    fun login(email: String, password: String): LiveData<Result<LoginResponse>> = liveData{
        emit(Result.Loading)
        try {

            val response = apiService.login(email, password)
            emit(Result.Success(response))
        }
        catch(e: Exception){
            emit(Result.Error(e.toString()))
        }
    }

    fun getStories(token:String): LiveData<Result<GetStoriesResponse>> = liveData {
        emit(Result.Loading)
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

    fun addStory(token:String, description: RequestBody,image: MultipartBody.Part): LiveData<Result<AddStoryResponse>> = liveData {
        emit(Result.Loading)
        try {
            Log.i("result","mencoba")
            val response = apiService.uploadStory("Bearer $token",description,image)
            emit(Result.Success(response))
        }
        catch(e: Exception){
            Log.i("result","gagal")
            emit(Result.Error(e.toString()))
        }
    }

    fun addStorywithLatLon(token:String, description: RequestBody,image: MultipartBody.Part,lat: RequestBody,lon: RequestBody): LiveData<Result<AddStoryResponse>> = liveData {
        emit(Result.Loading)
        try {
            Log.i("result","mencoba")
            val response = apiService.uploadStoryWithLatLon("Bearer $token",description,image,lat,lon)
            emit(Result.Success(response))
        }
        catch(e: Exception){
            Log.i("result","gagal")
            emit(Result.Error(e.toString()))
        }
    }


}