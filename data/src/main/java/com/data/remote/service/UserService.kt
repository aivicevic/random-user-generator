package com.data.remote.service

import com.domain.model.user.User
import com.domain.model.user.UsersResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface UserService {
    @GET("./")
    fun getUser(@Query("nat") nationalities: String = "us"): Single<User>

    @GET("./")
    fun getUsers(@Query("results") results: Int,
                 @Query("nat") nationalities: String = "us"): Single<UsersResponse>
}
