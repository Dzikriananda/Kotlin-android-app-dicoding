package com.example.mystoryapp.utility

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.liveData
import com.example.mystoryapp.data.StoryPagingSource
import com.example.mystoryapp.network.ApiService
import com.example.mystoryapp.response.ListStoryItem
import com.example.mystoryapp.response.StoryWithLocResponse

class StoryRepository(private val apiService: ApiService,private val context: Context) {
    private var preferences: Preferences = Preferences(context)
    private var token: String = preferences.Get_Token()!!
    private var Token: String = "Bearer $token"


    fun getStory(): LiveData<PagingData<ListStoryItem>> {

        return Pager(
            config = PagingConfig(
                pageSize = 5
            ),
            pagingSourceFactory = {
                StoryPagingSource(apiService,Token)
            }
        ).liveData
    }

    fun getStorywithLoc(): LiveData<Result<StoryWithLocResponse>> = liveData {
        emit(Result.Loading)
        try {
            Log.i("result","mencoba")
            val response = apiService.getStoriesWithLoc(Token)
            emit(Result.Success(response))
        }
        catch(e: Exception){
            Log.i("result","gagal")
            emit(Result.Error(e.toString()))
        }
    }
}