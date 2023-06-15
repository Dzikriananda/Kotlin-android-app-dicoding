package com.example.mystoryapp.network

import com.example.mystoryapp.response.GetStoriesResponse
import com.example.mystoryapp.response.LoginResponse
import com.example.mystoryapp.response.RegisterResponse
import retrofit2.http.*

interface ApiService {
    @FormUrlEncoded
    @POST("register")
    suspend fun register(
        @Field("name") name : String,
        @Field("email") email: String,
        @Field("password") password: String
    ): RegisterResponse

    @FormUrlEncoded
    @POST("login")
    suspend fun login(
        @Field("email") email: String,
        @Field("password") password: String
    ): LoginResponse

    @GET("stories")
    suspend fun get_profile(
        @Header("Authorization") token: String
    ): GetStoriesResponse




}