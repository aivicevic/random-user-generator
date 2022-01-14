package com.aivicevic.randomusers.data.remote.repository.impl

import androidx.lifecycle.LiveData
import com.aivicevic.randomusers.data.local.db.RandomUsersDatabase
import com.aivicevic.randomusers.data.remote.service.UserService
import com.aivicevic.randomusers.domain.model.user.User
import com.aivicevic.randomusers.domain.model.user.UsersResponse
import com.aivicevic.randomusers.domain.repository.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class UserRepositoryImpl(
    private val userService: UserService,
    private val dbInstance: RandomUsersDatabase
) : UserRepository {

    override suspend fun getUsers(results: Int): UsersResponse {
        return userService.getUsers(results)
    }

    override fun getFavoriteFromDb(user: User): LiveData<User?> {
        return dbInstance.usersDao().getFavorite(user.id.value)
    }

    override fun getFavoritesFromDb(): LiveData<List<User>> {
        return dbInstance.usersDao().getFavorites()
    }

    override suspend fun saveFavoriteToDb(user: User) {
        withContext(Dispatchers.IO) {
            dbInstance.usersDao().insertFavorite(user)
        }
    }

    override suspend fun deleteFavoriteFromDb(user: User) {
        withContext(Dispatchers.IO) {
            dbInstance.usersDao().deleteFavorite(user.id.value)
        }
    }

    override suspend fun deleteAllFavoritesFromDb() {
        withContext(Dispatchers.IO) {
            dbInstance.usersDao().deleteAll()
        }
    }
}
