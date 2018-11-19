package com.randomusers.di.module

import com.data.local.db.RandomUsersDatabase
import com.data.remote.repository.impl.UserRepositoryImpl
import com.data.remote.service.UserService
import com.domain.repository.UserRepository
import dagger.Module
import dagger.Provides

@Module
class RepositoryModule {
    @Provides
    fun providesUserRepository(service: UserService, db: RandomUsersDatabase): UserRepository =
        UserRepositoryImpl(service, db)
}