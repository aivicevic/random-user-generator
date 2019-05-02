package com.randomusers.di.module

import com.randomusers.RandomUsersApplication
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule(private val application: RandomUsersApplication) {

    @Provides
    @Singleton
    fun provideApplication() = this.application
}