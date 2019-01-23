package com.randomusers

import android.app.Activity
import android.app.Application
import android.support.v4.app.Fragment
import com.randomusers.di.component.DaggerAppComponent
import com.randomusers.di.module.AppModule
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import dagger.android.support.HasSupportFragmentInjector
import javax.inject.Inject

class RandomUsersApplication : Application(), HasActivityInjector {

    @Inject lateinit var activityInjector: DispatchingAndroidInjector<Activity>

    override fun onCreate() {
        super.onCreate()
        initDagger()
    }

    override fun activityInjector(): AndroidInjector<Activity> = activityInjector

    private fun initDagger() {
        DaggerAppComponent.builder().appModule(AppModule(this)).build().inject(this)
    }
}
