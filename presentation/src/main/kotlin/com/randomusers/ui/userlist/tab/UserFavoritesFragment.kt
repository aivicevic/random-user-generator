package com.randomusers.ui.userlist.tab

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
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

    override fun onAttach(context: Context) {
        super.onAttach(context)

        if (context is UserListListener) {
            userListListener = context
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        initViewModels()
    }

    private fun initViewModels() {
        activity?.run {
            userListViewModel = ViewModelProvider(this).get(UserListViewModel::class.java)
        }

        userListViewModel?.favoriteUsersLiveData?.observe(
            viewLifecycleOwner ?: this@UserFavoritesFragment, Observer<List<User>> { favorites ->
                favorites?.run {
                    processFavorites(this)
                }
            }
        )
    }

    private fun processFavorites(users: List<User>) {
        userListListener?.hideError()
        userListAdapter.submitList(users.reversed())
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_user_list, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUI()
    }

    private fun initUI() {
        userListAdapter = UserListAdapter(userListListener)
        userList.run {
            adapter = userListAdapter
            layoutManager = LinearLayoutManager(activity, RecyclerView.VERTICAL, false)
            addItemDecoration(DividerItemDecoration(activity, DividerItemDecoration.VERTICAL))
        }

        swipeRefresh.isEnabled = false
    }

    override fun onDestroyView() {
        userList.adapter = null
        super.onDestroyView()
    }

    companion object {

        fun newInstance(): UserFavoritesFragment = UserFavoritesFragment()
    }
}
