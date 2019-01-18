package com.data.local.db.dao

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.Query
import android.arch.persistence.room.Transaction
import com.domain.model.user.User

/*
 * TODO: Add documentation
 */
@Dao
interface UsersDao {

    @Query("SELECT * FROM favorites WHERE favorites.value = :userId")
    @Transaction
    fun getFavorite(userId:String): LiveData<User?>

    @Query("SELECT * FROM favorites")
    @Transaction
    fun getFavorites(): LiveData<List<User>>

    @Insert
    @Transaction
    fun insertFavorite(user: User)

    @Query("DELETE FROM favorites WHERE favorites.value = :userId")
    @Transaction
    fun deleteFavorite(userId: String)

    @Query("DELETE FROM favorites")
    @Transaction
    fun deleteAll()
}
