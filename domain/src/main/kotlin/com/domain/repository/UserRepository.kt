package com.domain.repository

import androidx.lifecycle.LiveData
import com.domain.model.user.User
import com.domain.model.user.UsersResponse

interface UserRepository {

    suspend fun getUsers(results: Int = 100): UsersResponse

    fun getFavoriteFromDb(user: User): LiveData<User?>

    fun getFavoritesFromDb(): LiveData<List<User>>

    suspend fun saveFavoriteToDb(user: User)

    suspend fun deleteFavoriteFromDb(user: User)

    suspend fun deleteAllFavoritesFromDb()
}
