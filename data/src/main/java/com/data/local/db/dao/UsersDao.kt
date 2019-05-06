package com.data.local.db.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
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
