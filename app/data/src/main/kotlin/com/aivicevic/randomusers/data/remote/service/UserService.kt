package com.aivicevic.randomusers.data.remote.service

import com.aivicevic.randomusers.domain.model.user.User
import com.aivicevic.randomusers.domain.model.user.UsersResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface UserService {

    @GET("./")
    suspend fun getUser(@Query("nat") nationalities: String = "us"): User

    @GET("./")
    suspend fun getUsers(
        @Query("results") results: Int,
        @Query("nat") nationalities: String = "us"
    ): UsersResponse
}
