package com.data.remote.repository.impl

import androidx.lifecycle.LiveData
import com.data.local.db.RandomUsersDatabase
import com.data.local.db.ioThread
import com.data.remote.service.UserService
import com.domain.model.user.User
import com.domain.model.user.UsersResponse
import com.domain.repository.UserRepository
import io.reactivex.Single
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserRepositoryImpl @Inject constructor(
    private val userService: UserService,
    private val dbInstance: RandomUsersDatabase
) : UserRepository {

    override fun getUsers(results: Int): Single<UsersResponse> = userService.getUsers(results)

    override fun getFavoriteFromDb(user: User): LiveData<User?> =
        dbInstance.usersDao().getFavorite(user.id.value)

    override fun getFavoritesFromDb(): LiveData<List<User>> =
        dbInstance.usersDao().getFavorites()

    override fun saveFavoriteToDb(user: User) {
        ioThread { dbInstance.usersDao().insertFavorite(user) }
    }

    override fun deleteFavoriteFromDb(user: User) {
        ioThread { dbInstance.usersDao().deleteFavorite(user.id.value) }
    }

    override fun deleteAllFavoritesFromDb() {
        ioThread { dbInstance.usersDao().deleteAll() }
    }
}
