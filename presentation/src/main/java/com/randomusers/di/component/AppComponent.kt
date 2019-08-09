package com.randomusers.di.component

import com.randomusers.RandomUsersApplication
import com.randomusers.di.module.*
import dagger.Component
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AndroidSupportInjectionModule::class,
        ActivityBindingModule::class,
        AppModule::class,
        DatabaseModule::class,
        NetworkModule::class,
        RepositoryModule::class,
        ServiceModule::class,
        ViewModelModule::class
    ]
)
interface AppComponent {

    fun inject(application: RandomUsersApplication)
}
