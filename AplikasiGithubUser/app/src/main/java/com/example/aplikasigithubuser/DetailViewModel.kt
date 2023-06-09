package com.example.aplikasigithubuser

import android.app.Application
import android.content.ContentValues
import android.util.Log
import androidx.lifecycle.*
import androidx.room.Room
import com.example.aplikasigithubuser.Room.Users
import com.example.aplikasigithubuser.Room.UsersDao
import com.example.aplikasigithubuser.Room.UsersRoomDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import retrofit2.Callback
import retrofit2.Call
import retrofit2.Response

class DetailViewModel(application: Application) : AndroidViewModel(application)  {
    private val _usersdata = MutableLiveData<DetailResponse>()
    val usersdata: LiveData<DetailResponse>   = _usersdata

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    var  isAvailable : String? = null


    private lateinit var _searchquery: String

        private val userDao: UsersDao
        private val db: UsersRoomDatabase = Room.databaseBuilder(application, UsersRoomDatabase::class.java, "user_database")
            .build()

        init {
            userDao = db.usersDao()
        }

        fun insert(user: Users) = viewModelScope.launch(Dispatchers.IO) {
            userDao.insert(user)
        }


    fun findifexist(username: String): String? {
        var isAvailable: String? = null
        runBlocking {
            withContext(Dispatchers.IO) {
                val user = userDao.getFavoriteUserByUsername(username)
                isAvailable = user
            }
        }
        return isAvailable
    }
        fun deleteUsers(users: String) = viewModelScope.launch(Dispatchers.IO) {
            userDao.delete(users)
        }



    private fun findUsersDetail() {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getUsersDetail(_searchquery)
        client.enqueue(object : Callback<DetailResponse> {
            override fun onResponse(
                call: Call<DetailResponse>,
                response: Response<DetailResponse>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        _usersdata.value = responseBody!!
                    }
                } else {
                    Log.e(ContentValues.TAG, "onFailure: ${response.message()}")
                }
            }
            override fun onFailure(call: Call<DetailResponse>, t: Throwable) {
                _isLoading.value = false
                Log.e(ContentValues.TAG, "onFailure: ${t.message}")
            }
        })
    }

    fun setUsername (data: String){
        _searchquery = data
        findUsersDetail()
    }








}