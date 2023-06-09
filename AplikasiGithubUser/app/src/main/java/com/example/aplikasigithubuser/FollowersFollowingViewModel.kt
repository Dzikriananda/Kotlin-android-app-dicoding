package com.example.aplikasigithubuser

import android.content.ContentValues
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import retrofit2.Callback
import retrofit2.Call
import retrofit2.Response

class FollowersFollowingViewModel: ViewModel() {


    private  val _listusers = MutableLiveData<List<ResponseFollowersFollowingItem>>()
    val listusers: LiveData<List<ResponseFollowersFollowingItem>> = _listusers

    private  var _searchquery: String  = ""
    lateinit  var condition_search : String


    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading


    private fun getUsers_Followers() {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getUsersFollowers(_searchquery)
        client.enqueue(object : Callback<List<ResponseFollowersFollowingItem>> {
            override fun onResponse(
                call: Call<List<ResponseFollowersFollowingItem>>,
                response: Response<List<ResponseFollowersFollowingItem>>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    val responseBody = response.body()

                    _listusers.value = responseBody!!

                }
            }
            override fun onFailure(call: Call<List<ResponseFollowersFollowingItem>>, t: Throwable) {
                _isLoading.value = false
                Log.e(ContentValues.TAG, "onFailure: ${t.message}")
            }
        })
    }

    private fun getUsers_Following() {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getUsersFollowing(_searchquery)
        client.enqueue(object : Callback<List<ResponseFollowersFollowingItem>> {
            override fun onResponse(
                call: Call<List<ResponseFollowersFollowingItem>>,
                response: Response<List<ResponseFollowersFollowingItem>>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    val responseBody = response.body()

                    _listusers.value = responseBody!!

                }
            }
            override fun onFailure(call: Call<List<ResponseFollowersFollowingItem>>, t: Throwable) {
                _isLoading.value = false
                Log.e(ContentValues.TAG, "onFailure: ${t.message}")
            }
        })
    }

    fun Start_search(query1: String,query2: String){
        condition_search = query1
        _searchquery = query2
        mulai()


    }

    fun mulai(){
        if (condition_search == "followers") {
            getUsers_Followers()
        }
        else {
            getUsers_Following()
        }
    }



}