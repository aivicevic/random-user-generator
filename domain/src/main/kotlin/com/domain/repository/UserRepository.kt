package com.domain.repository

import androidx.lifecycle.LiveData
import com.domain.model.user.User
import com.domain.model.user.UsersResponse

interface UserRepository {
    // TODO: Use specific exceptions instead of generic throwable...

    @Throws(Throwable::class)
    suspend fun getUsers(results: Int = 100): UsersResponse

    fun getFavoriteFromDb(user: User): LiveData<User?>

    fun getFavoritesFromDb(): LiveData<List<User>>

    @Throws(Throwable::class)
    suspend fun saveFavoriteToDb(user: User)

    @Throws(Throwable::class)
    suspend fun deleteFavoriteFromDb(user: User)

    @Throws(Throwable::class)
    suspend fun deleteAllFavoritesFromDb()
}
