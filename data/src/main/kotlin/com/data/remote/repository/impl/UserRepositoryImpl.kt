package com.data.remote.repository.impl

import androidx.lifecycle.LiveData
import com.data.local.db.RandomUsersDatabase
import com.data.remote.service.UserService
import com.domain.model.user.User
import com.domain.model.user.UsersResponse
import com.domain.repository.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserRepositoryImpl @Inject constructor(
    private val userService: UserService,
    private val dbInstance: RandomUsersDatabase
) : UserRepository {

    override suspend fun getUsers(results: Int): UsersResponse = withContext(Dispatchers.IO) {
        userService.getUsers(results)
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
