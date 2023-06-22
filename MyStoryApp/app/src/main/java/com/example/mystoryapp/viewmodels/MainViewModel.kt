package com.example.mystoryapp.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.mystoryapp.response.ListStoryItem
import com.example.mystoryapp.utility.Repository
import com.example.mystoryapp.utility.StoryRepository

class MainViewModel(private val storyRepository: StoryRepository): ViewModel() {
    //fun getStories(token: String) = repository.getStories(token)

    val story : LiveData<PagingData<ListStoryItem>> = storyRepository.getStory().cachedIn(viewModelScope)


}