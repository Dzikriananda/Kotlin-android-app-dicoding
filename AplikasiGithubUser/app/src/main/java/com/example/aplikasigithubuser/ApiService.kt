package com.example.aplikasigithubuser
import retrofit2.Call
import retrofit2.http.*

interface ApiService {
    @Headers("Authorization: token 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")
    @GET("search/users")
    fun getUsers(
        @Query("q") query: String
    ): Call<UserResponse>

    @Headers("Authorization: token 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")
    @GET("users/{username}")
    fun getUsersDetail(
        @Path ("username") username: String
    ): Call<DetailResponse>

    @Headers("Authorization: token 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")
    @GET("users/{id}/followers")
    fun getUsersFollowers(
        @Path("id") id: String
    ): Call<List<ResponseFollowersFollowingItem>>

    @Headers("Authorization: token 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")
    @GET("users/{id}/following")
    fun getUsersFollowing(
        @Path("id") id: String
    ): Call<List<ResponseFollowersFollowingItem>>
}