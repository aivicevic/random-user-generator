package com.randomusers.di.module

import com.randomusers.ui.home.HomeActivity
import com.randomusers.ui.userlist.UserListActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBindingModule {

    @ContributesAndroidInjector
    abstract fun contributesHomeActivity(): HomeActivity

    @ContributesAndroidInjector
    abstract fun contributesUserListActivity(): UserListActivity
}
