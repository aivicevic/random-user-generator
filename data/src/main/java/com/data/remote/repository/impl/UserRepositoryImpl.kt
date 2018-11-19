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

    override fun saveUsersToDb(users: List<User>) {
        ioThread { dbInstance.usersDao().insertAll(users) }
    }

    override fun getUsersFromDb(): List<User> = dbInstance.usersDao().all

    override fun removeUsersFromDb() {
        ioThread { dbInstance.usersDao().deleteAll() }
    }
}