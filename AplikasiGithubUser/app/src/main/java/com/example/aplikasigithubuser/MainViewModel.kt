package com.example.aplikasigithubuser

import android.content.ContentValues
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import retrofit2.Callback
import retrofit2.Call
import retrofit2.Response

class MainViewModel: ViewModel() {


    private  val _listusers = MutableLiveData<List<ItemsItem>>()
    val listusers: LiveData<List<ItemsItem>> = _listusers

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private  var _searchquery: String = "arif" // default value adalah arif, kalau saya nullkan akan jelek krn saat startup akan kosong recyclerviewnya

    init{
        findUsers()
    }

    private fun findUsers() {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getUsers(_searchquery)
        client.enqueue(object : Callback<UserResponse> {
            override fun onResponse(
                call: Call<UserResponse>,
                response: Response<UserResponse>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        _listusers.value = responseBody.items
                    }
                } else {
                    Log.e(ContentValues.TAG, "onFailure: ${response.message()}")
                }
            }
            override fun onFailure(call: Call<UserResponse>, t: Throwable) {
                _isLoading.value = false
                Log.e(ContentValues.TAG, "onFailure: ${t.message}")
            }
        })
    }

    fun setQuery(query: String){
        _searchquery = query
        findUsers()
    }



}