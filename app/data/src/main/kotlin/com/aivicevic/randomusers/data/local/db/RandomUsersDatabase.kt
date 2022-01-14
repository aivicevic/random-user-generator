package com.aivicevic.randomusers.data.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.aivicevic.randomusers.data.local.db.dao.UsersDao
import com.aivicevic.randomusers.domain.model.user.User

@Database(entities = [User::class], version = 1)
abstract class RandomUsersDatabase : RoomDatabase() {

    abstract fun usersDao(): UsersDao
}
