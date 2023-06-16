package com.example.mystoryapp.viewmodels

import androidx.lifecycle.ViewModel
import com.example.mystoryapp.utility.Repository
import okhttp3.MultipartBody
import okhttp3.RequestBody

class AddStoryViewModel(private val repository: Repository): ViewModel() {

    fun addStory(
        token: String,
        description: RequestBody,
        image: MultipartBody.Part,
    ) = repository.addStory(token,description, image)

    fun addStoryWithLatLon(
        token: String,
        description: RequestBody,
        image: MultipartBody.Part,
        lat: RequestBody,
        lon: RequestBody
    ) = repository.addStorywithLatLon(token,description, image,lat,lon)

}