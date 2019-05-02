package com.randomusers.ui.userlist.tab

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.os.Bundle
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.domain.model.user.User
import com.randomusers.R
import com.randomusers.common.ViewLifecycleFragment
import com.randomusers.ui.userlist.UserListListener
import com.randomusers.ui.userlist.UserListViewModel
import com.randomusers.ui.userlist.adapter.UserListAdapter
import kotlinx.android.synthetic.main.fragment_user_list.*

class UserFavoritesFragment : ViewLifecycleFragment() {

    private lateinit var userListAdapter: UserListAdapter
    private var userListListener: UserListListener? = null
    private var userListViewModel: UserListViewModel? = null

    private val favoriteUsersObserver = Observer<List<User>> { favorites ->
        favorites?.run {
            processFavorites(this)
        }
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)

        if (context is UserListListener) {
            userListListener = context
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        initViewModels()
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

    override fun onDestroyView() {
        userList.adapter = null
        super.onDestroyView()
    }

    private fun initViewModels() {
        activity?.run {
            userListViewModel = ViewModelProviders.of(this).get(UserListViewModel::class.java)
        }

        userListViewModel?.favoriteUsersLiveData?.observe(
            viewLifecycleOwner ?: this, favoriteUsersObserver
        )
    }

    private fun initUI() {
        userListAdapter = UserListAdapter(userListListener)
        userList.run {
            adapter = userListAdapter
            layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
            addItemDecoration(DividerItemDecoration(activity, DividerItemDecoration.VERTICAL))
        }

        swipeRefresh.isEnabled = false
    }

    private fun processFavorites(users: List<User>) {
        userListListener?.hideError()
        userListAdapter.updateUserList(users)
    }

    companion object {

        fun newInstance(): UserFavoritesFragment = UserFavoritesFragment()
    }
}
