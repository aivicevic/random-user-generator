package com.randomusers.ui.userlist.tab

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.randomusers.R
import com.randomusers.ui.userlist.UserListListener
import com.randomusers.ui.userlist.UserListStateType
import com.randomusers.ui.userlist.UserListViewModel
import com.randomusers.ui.userlist.adapter.UserListAdapter
import dagger.android.support.AndroidSupportInjection
import kotlinx.android.synthetic.main.fragment_user_list.*
import javax.inject.Inject

class UserFavoritesFragment : Fragment() {

    @Inject lateinit var viewModelFactory: ViewModelProvider.Factory
    @Inject lateinit var userListViewModel: UserListViewModel

    private var userListListener: UserListListener? = null
    private var userListAdapter: UserListAdapter? = null

    override fun onAttach(context: Context?) {
        super.onAttach(context)

        if (context is UserListListener) {
            userListListener = context
            userListAdapter = UserListAdapter(userListListener!!)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidSupportInjection.inject(this)
        super.onCreate(savedInstanceState)

        initViewModel()

        savedInstanceState?.let {
            userListViewModel.restoreUserList()
        } ?: userListViewModel.updateUserList()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? =
        inflater.inflate(R.layout.fragment_user_list, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUI()
    }

    override fun onDestroy() {
        userList.adapter = null
        super.onDestroy()
    }

    private fun initViewModel() {
        userListViewModel =
                ViewModelProviders.of(this, viewModelFactory)
                    .get(UserListViewModel::class.java)
                    .also { it.isFavoritesList = true }
        userListViewModel.stateLiveData.observe(this, Observer { state ->
            state?.let {
                when {
                    state.stateType == UserListStateType.DEFAULT -> {
                        swipeRefresh.isRefreshing = false
                        userListListener?.hideError()
                        userListAdapter?.updateUserList(it.data)
                    }
                    state.stateType == UserListStateType.LOADING -> {
                        swipeRefresh.isRefreshing = true
                        userListListener?.hideError()
                    }
                    state.stateType == UserListStateType.ERROR -> {
                        swipeRefresh.isRefreshing = false
                        userListListener?.showError(state.errorMessage!!)
                    }
                    else -> {
                        swipeRefresh.isRefreshing = false
                        userListListener?.showError(R.string.load_users_error)
                    }
                }
            }
        })
    }

    private fun initUI() {
        userList.apply {
            adapter = userListAdapter
            layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
            addItemDecoration(DividerItemDecoration(activity, DividerItemDecoration.VERTICAL))
        }
    }

    companion object {
        fun newInstance(): UserFavoritesFragment =
            UserFavoritesFragment()
    }
}
