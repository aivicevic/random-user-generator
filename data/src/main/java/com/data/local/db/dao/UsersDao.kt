package com.data.local.db.dao

import android.arch.persistence.room.*
import com.domain.model.user.User
import io.reactivex.Single

/*
 * TODO: Add documentation
 */
@Dao
interface UsersDao {

    @Insert
    @Transaction
    fun insertUser(user: User)

    @Insert
    @Transaction
    fun insertUsers(users: List<User>)

    @Update
    @Transaction
    fun updateFavoriteUser(user: User)

    @Query("SELECT * FROM user WHERE user.value = :userId")
    @Transaction
    fun getUserById(userId: String): User

    @Query("SELECT * FROM user WHERE user.isFavorite = 1")
    @Transaction
    fun getFavorites(): Single<List<User>>

    @Query("SELECT * FROM user")
    @Transaction
    fun getAllUsers(): List<User>

    @Query("DELETE FROM user WHERE user.value = :userId")
    @Transaction
    fun deleteUserById(userId: String)

    @Query("DELETE FROM user WHERE user.isFavorite = 0")
    @Transaction
    fun deleteNonFavorites()

    @Query("DELETE FROM user")
    @Transaction
    fun deleteAll()
}