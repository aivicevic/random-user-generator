package com.domain.repository

import com.domain.model.user.User
import io.reactivex.Single

interface UserRepository {

    fun getUser(): Single<User>

    fun getUsers(results: Int): Single<List<User>>

    fun saveUserToDB(user: User)

    fun saveUsersToDb(users: List<User>)

    fun updateFavoriteInDb(user: User)

    fun getUserFromDb(user: User)

    fun getFavoritesFromDb(): Single<List<User>>

    fun getAllUsersFromDb(): List<User>

    fun deleteUserFromDb(user: User)

    fun deleteNonFavoritesFromDb()

    fun deleteAllUsersFromDb()
}