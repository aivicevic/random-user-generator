package com.data.remote.repository.impl

import com.data.local.db.RandomUsersDatabase
import com.data.local.db.ioThread
import com.data.remote.service.UserService
import com.domain.model.user.User
import com.domain.repository.UserRepository
import io.reactivex.Single
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserRepositoryImpl @Inject constructor(
    private val userService: UserService,
    private val dbInstance: RandomUsersDatabase
) : UserRepository {

    override fun getUser(): Single<User> = userService.getUser()

    override fun getUsers(results: Int): Single<List<User>> =
        userService.getUsers(results).map { users -> users.results }

    override fun saveUserToDB(user: User) {
        ioThread { dbInstance.usersDao().insertUser(user) }
    }

    override fun saveUsersToDb(users: List<User>) {
        ioThread { dbInstance.usersDao().insertUsers(users) }
    }

    override fun updateFavoriteInDb(user: User) {
        ioThread { dbInstance.usersDao().updateFavoriteUser(user) }
    }

    override fun getUserFromDb(user: User) {
        dbInstance.usersDao().getUserById(user.id.value)
    }

    override fun getFavoritesFromDb(): Single<List<User>> = dbInstance.usersDao().getFavorites()

    override fun getAllUsersFromDb(): List<User> = dbInstance.usersDao().getAllUsers()

    override fun deleteUserFromDb(user: User) {
        ioThread { dbInstance.usersDao().deleteUserById(user.id.value) }
    }

    override fun deleteNonFavoritesFromDb() {
        ioThread { dbInstance.usersDao().deleteNonFavorites() }
    }

    override fun deleteAllUsersFromDb() {
        ioThread { dbInstance.usersDao().deleteAll() }
    }
}