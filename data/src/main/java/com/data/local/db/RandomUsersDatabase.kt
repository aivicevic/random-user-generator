package com.data.local.db

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import com.data.local.db.dao.UsersDao
import com.domain.model.user.User

@Database(entities = [User::class], version = 1)
abstract class RandomUsersDatabase : RoomDatabase() {
    abstract fun usersDao(): UsersDao
}
