package com.randomusers.di.component

import com.randomusers.RandomUsersApplication
import com.randomusers.di.module.*
import dagger.Component
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

// TODO: Clean up DI components (more modular)
@Singleton
@Component(
    modules = [
        AndroidSupportInjectionModule::class,
        ActivityBindingModule::class,
        FragmentBindingModule::class,
        AppModule::class,
        DatabaseModule::class,
        NetworkModule::class,
        RepositoryModule::class,
        ServiceModule::class,
        ViewModelModule::class]
)
interface AppComponent {

    fun inject(application: RandomUsersApplication)
}
