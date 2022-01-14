package com.aivicevic.randomusers.presentation.main.userlist.tab

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import com.aivicevic.randomusers.presentation.R
import com.aivicevic.randomusers.presentation.databinding.FragmentUserListTabBinding
import com.aivicevic.randomusers.presentation.main.userlist.UserListViewModel
import com.aivicevic.randomusers.presentation.main.userlist.adapter.UserListAdapter
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class UserListTabFragment : Fragment() {

    private val userListViewModel by sharedViewModel<UserListViewModel>()

    private lateinit var binding: FragmentUserListTabBinding
    private lateinit var userListAdapter: UserListAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate<FragmentUserListTabBinding>(
            inflater,
            R.layout.fragment_user_list_tab,
            container,
            false
        ).apply {
            lifecycleOwner = viewLifecycleOwner
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        initViewModels()
    }

    override fun onDestroyView() {
        binding.rvUsers.adapter = null
        super.onDestroyView()
    }

    private fun initViews() {
        userListAdapter = UserListAdapter({
            userListViewModel.onUserClicked(it)
        }, {
            userListViewModel.toggleFavorite(it)
        })
        with(binding) {
            rvUsers.run {
                adapter = userListAdapter
                addItemDecoration(DividerItemDecoration(activity, DividerItemDecoration.VERTICAL))
            }

            swipeRefresh.setOnRefreshListener { userListViewModel.updateUserList() }
        }
    }

    private fun initViewModels() {
        with(userListViewModel) {
            isLoading.observe(viewLifecycleOwner, { isLoading ->
                binding.swipeRefresh.isRefreshing = isLoading
            })

            userList.observe(viewLifecycleOwner, { users ->
                userListAdapter.submitList(users)
            })
        }
    }

    companion object {

        fun newInstance(): UserListTabFragment = UserListTabFragment()
    }
}
