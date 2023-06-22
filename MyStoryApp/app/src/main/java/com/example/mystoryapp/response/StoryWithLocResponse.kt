package com.example.mystoryapp.response

import com.google.gson.annotations.SerializedName

data class StoryWithLocResponse(

	@field:SerializedName("listStory")
	val listStory: List<ListStoryLocItem>,

	@field:SerializedName("error")
	val error: Boolean,

	@field:SerializedName("message")
	val message: String
)

data class ListStoryLocItem(

	@field:SerializedName("photoUrl")
	val photoUrl: String,

	@field:SerializedName("createdAt")
	val createdAt: String,

	@field:SerializedName("name")
	val name: String,

	@field:SerializedName("description")
	val description: String,

	@field:SerializedName("lon")
	val lon: Float,

	@field:SerializedName("id")
	val id: String,

	@field:SerializedName("lat")
	val lat: Float
)
