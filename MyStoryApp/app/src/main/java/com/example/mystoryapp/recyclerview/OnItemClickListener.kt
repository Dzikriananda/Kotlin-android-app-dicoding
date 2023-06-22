package com.example.mystoryapp.recyclerview

import com.example.mystoryapp.response.ListStoryItem

interface OnItemClickListener {
    fun itemclick(data: ListStoryItem)
}