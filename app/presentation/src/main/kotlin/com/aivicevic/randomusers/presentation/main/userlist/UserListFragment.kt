package com.aivicevic.randomusers.presentation.main.userlist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.aivicevic.randomusers.domain.model.user.User
import com.aivicevic.randomusers.presentation.R
import com.aivicevic.randomusers.presentation.databinding.FragmentUserListBinding
import com.aivicevic.randomusers.presentation.main.userlist.adapter.UserListPagerAdapter
import com.aivicevic.randomusers.presentation.main.userlist.tab.UserFavoritesTabFragment
import com.aivicevic.randomusers.presentation.main.userlist.tab.UserListTabFragment
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.tabs.TabLayoutMediator
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class UserListFragment : Fragment() {

    private val userListViewModel by sharedViewModel<UserListViewModel>()

    private var errorSnackbar: Snackbar? = null
    private var isToggleFavoriteEnabled: Boolean = true

    private lateinit var binding: FragmentUserListBinding
    private lateinit var userListPagerAdapter: UserListPagerAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate<FragmentUserListBinding>(
            inflater,
            R.layout.fragment_user_list,
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

    private fun initViews() {
        userListPagerAdapter = UserListPagerAdapter(childFragmentManager, lifecycle)
        with(binding) {
            viewPager.apply {
                isUserInputEnabled = false
                adapter = userListPagerAdapter.apply {
                    addFragment(
                        UserListTabFragment.newInstance(),
                        getString(R.string.user_list_tab_list)
                    )
                    addFragment(
                        UserFavoritesTabFragment.newInstance(),
                        getString(R.string.user_list_tab_favorites)
                    )
                }
            }

            TabLayoutMediator(tlUserLists, viewPager) { tab, position ->
                tab.text = userListPagerAdapter.getPageTitle(position)
                viewPager.setCurrentItem(tab.position, true)
            }.attach()
        }
    }

    private fun initViewModels() {
        with(userListViewModel) {
            if (userList.value.isNullOrEmpty()) {
                updateUserList()
            }

            toggleFavoriteStatusLiveData.observe(viewLifecycleOwner, { status ->
                status?.run {
                    processToggleFavoriteStatus(this)
                }
            })

            onUserClickedEvent.observe(viewLifecycleOwner, { event ->
                event.getContentIfNotHandled()?.let {
                    navigateToUserDetail(it)
                }
            })

            errorEvent.observe(viewLifecycleOwner, { event ->
                event.getContentIfNotHandled()?.let {
                    showError(it)
                }
            })
        }
    }

    private fun processToggleFavoriteStatus(status: ToggleFavoriteStatus) {
        if (status.hasError) {
            showError(R.string.default_error)
        }
        isToggleFavoriteEnabled = status.isEnabled
    }

    // TODO("Implement more abstracted way of populating/passing this data")
    private fun navigateToUserDetail(user: User) {
        val navDirections = UserListFragmentDirections.actionToFragmentUserDetails(
            userProfilePic = user.picture.large,
            userName = "${user.name.first} ${user.name.last}",
            userDob = user.dob.date,
            userLocation = "${user.location.street.number} ${user.location.street.streetName}, " +
                "${user.location.city}, ${user.location.state} ${user.location.postcode}",
            userEmail = user.email,
            userPhone = user.cell,
            isFavorite = user.isFavorite
        )
        findNavController().navigate(navDirections)
    }

    private fun showFavoriteToast(favoriteSaved: Boolean) {
        val message = if (favoriteSaved) {
            R.string.user_list_favorite_saved
        } else {
            R.string.user_list_favorite_removed
        }
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }

    private fun showError(@StringRes errorMessage: Int) {
        errorSnackbar = Snackbar.make(binding.root, errorMessage, Snackbar.LENGTH_LONG).also {
            it.show()
        }
    }

    fun hideError() {
        errorSnackbar?.dismiss()
    }
}
