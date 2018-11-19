package com.randomusers.di.module

import com.data.remote.service.UserService
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit

@Module
class ServiceModule {
    @Provides
    internal fun provideUserService(retrofit: Retrofit) = retrofit.create(UserService::class.java)
}