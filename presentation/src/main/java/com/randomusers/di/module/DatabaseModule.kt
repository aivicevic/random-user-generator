package com.randomusers.di.module

import android.arch.persistence.room.Room
import com.data.local.db.RandomUsersDatabase
import com.randomusers.RandomUsersApplication
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DatabaseModule {
    @Provides
    @Singleton
    fun provideAppDatabase(application: RandomUsersApplication): RandomUsersDatabase =
        Room.databaseBuilder(application, RandomUsersDatabase::class.java, "RandomUsers.db").build()
}