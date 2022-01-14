package com.aivicevic.randomusers.presentation.main.userdetails

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.aivicevic.randomusers.presentation.R
import com.aivicevic.randomusers.presentation.databinding.FragmentUserDetailsBinding
import com.aivicevic.randomusers.util.android.setUpToolbar
import org.koin.androidx.viewmodel.ext.android.viewModel

class UserDetailsFragment : Fragment() {

    private val userDetailsViewModel by viewModel<UserDetailsViewModel>()

    private lateinit var binding: FragmentUserDetailsBinding

    private val navArgs by navArgs<UserDetailsFragmentArgs>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate<FragmentUserDetailsBinding>(
            inflater,
            R.layout.fragment_user_details,
            container,
            false
        ).apply {
            lifecycleOwner = viewLifecycleOwner
            viewModel = userDetailsViewModel
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        processNavArgs()
        initViews()
        initViewModels()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_user_details, menu)
        setFavoriteIcon(menu.findItem(R.id.action_favorite))
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                findNavController().navigateUp()
            }
            R.id.action_favorite -> {
                userDetailsViewModel.toggleIsFavorite()
                setFavoriteIcon(item)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun processNavArgs() {
        with(navArgs) {
            userDetailsViewModel.populateUserData(
                userProfilePic = userProfilePic,
                userName = userName,
                userDob = userDob,
                userLocation = userLocation,
                userEmail = userEmail,
                userPhone = userPhone,
                isFavorite = isFavorite
            )
        }
    }

    private fun initViews() {
        setUpToolbar(displayHomeAsUpEnabled = true) {
            setTitle(R.string.app_name)
        }
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
            findNavController().navigateUp()
        }
        setHasOptionsMenu(true)
    }

    private fun initViewModels() {
    }

    private fun setFavoriteIcon(item: MenuItem) {
        val iconResource = if (userDetailsViewModel.isFavorite.value == true) {
            R.drawable.icon_favorite_selected
        } else {
            R.drawable.icon_favorite
        }
        item.setIcon(iconResource)
    }
}
