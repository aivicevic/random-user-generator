package com.data.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.data.local.db.dao.UsersDao
import com.domain.model.user.User

@Database(entities = [User::class], version = 1)
abstract class RandomUsersDatabase : RoomDatabase() {

    abstract fun usersDao(): UsersDao
}
