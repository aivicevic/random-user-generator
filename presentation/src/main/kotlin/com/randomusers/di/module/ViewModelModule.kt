package com.randomusers.di.module

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.randomusers.common.ViewModelFactory
import com.randomusers.common.ViewModelKey
import com.randomusers.ui.userlist.UserListViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule {

    @Binds
    internal abstract fun bindViewModelFactory(
        viewModelFactory: ViewModelFactory
    ): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(UserListViewModel::class)
    internal abstract fun userListViewModel(viewModel: UserListViewModel): ViewModel
}
