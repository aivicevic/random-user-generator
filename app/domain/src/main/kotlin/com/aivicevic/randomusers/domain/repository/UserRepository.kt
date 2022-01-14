package com.aivicevic.randomusers.domain.repository

import androidx.lifecycle.LiveData
import com.aivicevic.randomusers.domain.model.user.User
import com.aivicevic.randomusers.domain.model.user.UsersResponse

interface UserRepository {
    // TODO: Use specific exceptions instead of generic throwable...

    @Throws(Throwable::class)
    suspend fun getUsers(results: Int = 50): UsersResponse

    fun getFavoriteFromDb(user: User): LiveData<User?>

    fun getFavoritesFromDb(): LiveData<List<User>>

    @Throws(Throwable::class)
    suspend fun saveFavoriteToDb(user: User)

    @Throws(Throwable::class)
    suspend fun deleteFavoriteFromDb(user: User)

    @Throws(Throwable::class)
    suspend fun deleteAllFavoritesFromDb()
}
