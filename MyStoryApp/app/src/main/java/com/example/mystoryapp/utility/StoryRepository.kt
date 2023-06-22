package com.example.mystoryapp.utility

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.liveData
import com.example.mystoryapp.data.StoryPagingSource
import com.example.mystoryapp.network.ApiService
import com.example.mystoryapp.response.ListStoryItem

class StoryRepository(private val apiService: ApiService,private val context: Context) {
    private lateinit var preferences: Preferences
    private lateinit var token: String
    private lateinit var Token: String


    fun getStory(): LiveData<PagingData<ListStoryItem>> {
        preferences = Preferences(context)
        token = preferences.Get_Token()!!
        Token = "Bearer $token"

        return Pager(
            config = PagingConfig(
                pageSize = 5
            ),
            pagingSourceFactory = {
                StoryPagingSource(apiService,Token)
            }
        ).liveData
    }
}