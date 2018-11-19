package com.data.local.db.dao

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.Query
import com.domain.model.user.User

// TODO: Determine how to structure Dao since Users object is retarded
@Dao
interface UsersDao {
    @get:Query("SELECT * FROM user")
    val all: List<User>

    @Insert
    fun insertAll(users: List<User>)

    @Query("DELETE FROM user")
    fun deleteAll()
}