package com.randomusers.di.module

import com.randomusers.ui.home.HomeActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBindingModule {

    @ContributesAndroidInjector
    abstract fun contributesHomeActivity(): HomeActivity
}
