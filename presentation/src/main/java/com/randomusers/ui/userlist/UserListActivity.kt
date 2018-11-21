package com.randomusers.ui.userlist

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.annotation.StringRes
import android.support.design.widget.Snackbar
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.DividerItemDecoration.VERTICAL
import android.support.v7.widget.LinearLayoutManager
import android.widget.Toast
import com.domain.model.user.User
import com.randomusers.R
import com.randomusers.common.BaseActivity
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.activity_user_list.*
import javax.inject.Inject

class UserListActivity : BaseActivity(), UserListListener {

    @Inject lateinit var viewModelFactory: ViewModelProvider.Factory
    @Inject lateinit var userListViewModel: UserListViewModel

    private val userListAdapter = UserListAdapter(this)
    private var errorSnackbar: Snackbar? = null
    private var isFavoritesList = false

    companion object {

        private const val KEY_IS_FAVORITES_LIST = "KEY_IS_FAVORITES_LIST"

        fun getStartingIntent(context: Context, isFavoritesList: Boolean): Intent {
            val intent = Intent(context, UserListActivity::class.java)
            val extras = Bundle()
            extras.putBoolean(KEY_IS_FAVORITES_LIST, isFavoritesList)
            intent.putExtras(extras)

            return intent
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_list)

        processIncomingIntent()
        initViewModel()
        initUI()

        // TODO: Refactor view model methods to show persistable list when network is down...
        savedInstanceState?.let {
            userListViewModel.restoreUserList()
        } ?: userListViewModel.updateUserList()
    }

    override fun onDestroy() {
        super.onDestroy()
        userList.adapter = null
    }

    override fun openUserDetail() {
        // TODO("not implemented")
    }

    override fun toggleFavorite(user: User) {
        // TODO: Handle possible errors in db update?
        val message = if (user.isFavorite) R.string.favorite_removed else R.string.favorite_saved
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
        userListViewModel.toggleFavorite(user)
    }

    private fun processIncomingIntent() {
        if (null != intent && null != intent.extras) {
            isFavoritesList = intent!!.extras!!.getBoolean(KEY_IS_FAVORITES_LIST, false)
        }
    }

    private fun initViewModel() {
        userListViewModel = ViewModelProviders.of(this, viewModelFactory).get(UserListViewModel::class.java).also {
            it.isFavoritesList = isFavoritesList
        }
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

        if (isFavoritesList) {
            swipeRefresh.isEnabled = false
        } else {
            swipeRefresh.setOnRefreshListener { userListViewModel.resetUserList() }
        }
    }

    private fun showError(@StringRes errorMessage: Int) {
        errorSnackbar = Snackbar.make(content, errorMessage, Snackbar.LENGTH_LONG)
        errorSnackbar?.show()
    }

    private fun hideError() {
        errorSnackbar?.dismiss()
    }
}
