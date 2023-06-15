package com.example.mystoryapp.utility

data class StoryDataModel(
    val imageUrl: String,
    val createdAt: String,
    val name: String,
    val description: String,
    val lon: Double?,
    val id: String,
    val lat: Double
    )

