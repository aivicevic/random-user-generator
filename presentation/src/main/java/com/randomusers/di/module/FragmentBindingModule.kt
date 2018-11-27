package com.randomusers.di.module

import com.randomusers.ui.userlist.tab.UserFavoritesFragment
import com.randomusers.ui.userlist.tab.UserListFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class FragmentBindingModule {

    @ContributesAndroidInjector
    abstract fun contributesUserListFragment(): UserListFragment

    @ContributesAndroidInjector
    abstract fun contributesUserFavoritesFragment(): UserFavoritesFragment
}
