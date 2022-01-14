package com.aivicevic.randomusers.presentation.main.userlist.tab

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import com.aivicevic.randomusers.domain.model.user.User
import com.aivicevic.randomusers.presentation.R
import com.aivicevic.randomusers.presentation.databinding.FragmentUserFavoritesTabBinding
import com.aivicevic.randomusers.presentation.main.userlist.UserListViewModel
import com.aivicevic.randomusers.presentation.main.userlist.adapter.UserListAdapter
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class UserFavoritesTabFragment : Fragment() {

    private val userListViewModel by sharedViewModel<UserListViewModel>()

    private lateinit var binding: FragmentUserFavoritesTabBinding
    private lateinit var userListAdapter: UserListAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate<FragmentUserFavoritesTabBinding>(
            inflater,
            R.layout.fragment_user_favorites_tab,
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
        binding.rvFavorites.adapter = null
        super.onDestroyView()
    }

    private fun initViews() {
        userListAdapter = UserListAdapter({
            userListViewModel.onUserClicked(it)
        }, {
            userListViewModel.toggleFavorite(it)
        })
        with(binding) {
            rvFavorites.run {
                adapter = userListAdapter
                addItemDecoration(DividerItemDecoration(activity, DividerItemDecoration.VERTICAL))
            }
        }
    }

    private fun initViewModels() {
        userListViewModel.userFavoritesList.observe(viewLifecycleOwner, { favorites ->
            favorites?.run {
                processFavorites(this)
            }
        }
        )
    }

    private fun processFavorites(users: List<User>) {
        userListAdapter.submitList(users.reversed())
    }

    companion object {

        fun newInstance(): UserFavoritesTabFragment = UserFavoritesTabFragment()
    }
}
