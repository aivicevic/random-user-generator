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
import com.domain.Response
import com.domain.Status
import com.domain.model.user.UsersResponse
import com.randomusers.R
import com.randomusers.common.ViewLifecycleFragment
import com.randomusers.ui.userlist.UserListListener
import com.randomusers.ui.userlist.UserListViewModel
import com.randomusers.ui.userlist.adapter.UserListAdapter
import kotlinx.android.synthetic.main.fragment_user_list.*

class UserListFragment : ViewLifecycleFragment() {

    private lateinit var userListAdapter: UserListAdapter
    private var userListListener: UserListListener? = null
    private var userListViewModel: UserListViewModel? = null


    override fun onAttach(context: Context?) {
        super.onAttach(context)

        if (context is UserListListener) {
            userListListener = context
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        initViewModel()

        savedInstanceState?.let {
            userListViewModel?.restoreUserList()
        } ?: userListViewModel?.updateUserList()
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

    private fun initViewModel() {
        activity?.let {
            userListViewModel = ViewModelProviders.of(it).get(UserListViewModel::class.java)
        }

        userListViewModel?.usersResponseLiveData?.observe(
            viewLifecycleOwner ?: this, Observer { response ->
                response?.let { processUsersResponse(it) }
            })
    }

    private fun initUI() {
        userListAdapter = UserListAdapter(userListListener)
        userList.apply {
            adapter = userListAdapter
            layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
            addItemDecoration(DividerItemDecoration(activity, DividerItemDecoration.VERTICAL))
        }

        swipeRefresh.setOnRefreshListener { userListViewModel?.updateUserList() }
    }

    private fun processUsersResponse(response: Response<UsersResponse>) {
        when (response.status) {
            Status.LOADING -> {
                swipeRefresh.isRefreshing = true
                userListListener?.hideError()
            }
            Status.SUCCESS -> {
                swipeRefresh.isRefreshing = false
                userListListener?.hideError()
                userListAdapter.updateUserList(response.data?.results!!)
            }
            Status.ERROR -> {
                swipeRefresh.isRefreshing = false
                userListListener?.showError(R.string.load_users_error)
            }
        }
    }

    companion object {
        fun newInstance(): UserListFragment = UserListFragment()
    }
}
