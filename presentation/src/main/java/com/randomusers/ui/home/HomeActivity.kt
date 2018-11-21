package com.randomusers.ui.home

import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import com.randomusers.R
import com.randomusers.common.BaseActivity
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.activity_home.*
import javax.inject.Inject

class HomeActivity : BaseActivity() {

    @Inject lateinit var viewModelFactory: ViewModelProvider.Factory
    @Inject lateinit var homeViewModel: HomeViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this) // TODO: Revisit this
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        initViewModel()
        initUI()
    }

    private fun initViewModel() {
        homeViewModel = ViewModelProviders.of(this, viewModelFactory).get(HomeViewModel::class.java)
    }

    private fun initUI() {
        fetchUserButton.setOnClickListener {
            homeViewModel.fetchUser(this)
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
        }

        fetchUsersButton.setOnClickListener {
            homeViewModel.fetchUserList(this, false)
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
        }

        favoritesButton.setOnClickListener {
            homeViewModel.fetchUserList(this, true)
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
        }
    }
}
