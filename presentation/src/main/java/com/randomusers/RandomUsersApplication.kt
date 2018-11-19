package com.randomusers

import android.app.Activity
import android.app.Application
import com.randomusers.di.module.AppModule
import com.randomusers.di.component.DaggerAppComponent
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import javax.inject.Inject

class RandomUsersApplication : Application(), HasActivityInjector {
    @Inject
    lateinit var activityInjector: DispatchingAndroidInjector<Activity>

    override fun onCreate() {
        super.onCreate()
        initDagger()
    }

    override fun activityInjector(): AndroidInjector<Activity> = activityInjector

    private fun initDagger() {
        DaggerAppComponent.builder().appModule(AppModule(this)).build().inject(this)
    }
}