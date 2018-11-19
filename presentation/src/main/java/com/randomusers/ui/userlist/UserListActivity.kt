package com.randomusers.ui.userlist

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.annotation.StringRes
import android.support.design.widget.Snackbar
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.DividerItemDecoration.VERTICAL
import android.support.v7.widget.LinearLayoutManager
import com.randomusers.R
import com.randomusers.common.BaseActivity
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.activity_user_list.*
import javax.inject.Inject

class UserListActivity : BaseActivity() {

    @Inject lateinit var viewModelFactory: ViewModelProvider.Factory
    @Inject lateinit var userListViewModel: UserListViewModel

    private val userListAdapter = UserListAdapter()
    private var errorSnackbar: Snackbar? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_list)
        AndroidInjection.inject(this)

        initViewModel()
        initUI()

        savedInstanceState?.let {
            userListViewModel.restoreUserList()
        } ?: userListViewModel.updateUserList()
    }

    override fun onDestroy() {
        super.onDestroy()
        userList.adapter = null
    }

    private fun initViewModel() {
        userListViewModel = ViewModelProviders.of(this, viewModelFactory).get(UserListViewModel::class.java)
        userListViewModel.stateLiveData.observe(this, Observer { state ->
            state?.let {
                when {
                    state.stateType == UserListStateType.DEFAULT -> {
                        swipeRefresh.isRefreshing = false
                        hideError()
                        userListAdapter.updateUserList(it.data)
                    }
                    state.stateType == UserListStateType.LOADING -> {
                        swipeRefresh.isRefreshing = true
                        hideError()
                    }
                    state.stateType == UserListStateType.ERROR -> {
                        swipeRefresh.isRefreshing = false
                        showError(state.errorMessage!!)
                    }
                }
            }
        })
    }

    private fun initUI() {
        val linearLayoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        val itemDecoration = DividerItemDecoration(this, VERTICAL)
        userList.apply {
            adapter = userListAdapter
            layoutManager = linearLayoutManager
            addItemDecoration(itemDecoration)
        }

        swipeRefresh.setOnRefreshListener { userListViewModel.resetUserList() }
    }

    private fun showError(@StringRes errorMessage: Int) {
        errorSnackbar = Snackbar.make(content, errorMessage, Snackbar.LENGTH_LONG)
        errorSnackbar?.show()
    }

    private fun hideError() {
        errorSnackbar?.dismiss()
    }
}
