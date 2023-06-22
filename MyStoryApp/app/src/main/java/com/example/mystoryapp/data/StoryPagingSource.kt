package com.example.mystoryapp.data

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.mystoryapp.network.ApiService
import com.example.mystoryapp.response.ListStoryItem
import android.util.Log


class StoryPagingSource(private val apiService: ApiService) : PagingSource<Int, ListStoryItem>() {


    val token = "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VySWQiOiJ1c2VyLWNpTHZabml1TDAxUTZyLS0iLCJpYXQiOjE2ODc0MTkwNzB9.44uLqUQ9OwFEQ21UEFGJawqcFRzkgr_Qb4Abvsx26uE"

    private companion object {
        const val INITIAL_PAGE_INDEX = 1
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ListStoryItem> {
        return try {
            val position = params.key ?: INITIAL_PAGE_INDEX
            val responseData = apiService.get_profile(token,position,params.loadSize).listStory
            LoadResult.Page(
                data = responseData,
                prevKey = if (position == INITIAL_PAGE_INDEX) null else position - 1,
                nextKey = if (responseData.isNullOrEmpty()) null else position + 1
            )

        } catch (exception: Exception) {
            return LoadResult.Error(exception)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, ListStoryItem>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

}