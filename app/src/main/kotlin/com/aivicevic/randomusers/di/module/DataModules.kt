package com.aivicevic.randomusers.di.module

import com.aivicevic.randomusers.data.remote.repository.impl.UserRepositoryImpl
import com.aivicevic.randomusers.data.remote.service.UserService
import com.aivicevic.randomusers.di.provideAppDatabase
import com.aivicevic.randomusers.di.provideRetrofit
import com.aivicevic.randomusers.di.provideRetrofitService
import com.aivicevic.randomusers.domain.repository.UserRepository
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

/**
 * Dependency definitions for data layer (remote/local)
 */
private val DatabaseModule = module {

    single { provideAppDatabase(androidContext()) }
}

private val NetworkServiceModule = module {

    single { provideRetrofit() }

    single { provideRetrofitService<UserService>(get()) }
}

private val RepositoryModule = module {

    single<UserRepository> { UserRepositoryImpl(get(), get()) }
}

val DataModules = listOf(DatabaseModule, NetworkServiceModule, RepositoryModule)
