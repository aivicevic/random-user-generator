package com.aivicevic.randomusers.di.module

import com.aivicevic.randomusers.presentation.main.userdetails.UserDetailsViewModel
import com.aivicevic.randomusers.presentation.main.userlist.UserListViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

/**
 * Dependency definitions for application layer
 */

// TODO("Implement scopes")
private val MainModule = module {

    viewModel<UserListViewModel>()

    viewModel<UserDetailsViewModel>()
}

val AppModules = listOf(MainModule)
