package com.randomusers.ui.userlist

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.domain.model.user.User
import com.google.android.material.snackbar.Snackbar
import com.randomusers.R
import com.randomusers.common.BaseActivity
import com.randomusers.ui.userlist.adapter.UserListPagerAdapter
import com.randomusers.ui.userlist.tab.UserFavoritesFragment
import com.randomusers.ui.userlist.tab.UserListFragment
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.activity_user_list.*
import javax.inject.Inject

class UserListActivity : BaseActivity(), UserListListener {

    @Inject lateinit var viewModelFactory: ViewModelProvider.Factory
    @Inject lateinit var userListViewModel: UserListViewModel

    private val userListPagerAdapter = UserListPagerAdapter(supportFragmentManager)
    private var errorSnackbar: Snackbar? = null
    private var shouldOpenFavorites: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_list)

        processIncomingIntent()
        initViewModels()
    }

    private fun processIncomingIntent() {
        if (null != intent && null != intent.extras) {
            shouldOpenFavorites = intent!!.extras!!.getBoolean(KEY_SHOULD_OPEN_FAVORITES, false)
        }
    }

    private fun initViewModels() {
        userListViewModel =
            ViewModelProviders.of(this, viewModelFactory).get(UserListViewModel::class.java)
    }

    override fun initUI() {
        tabLayout.setupWithViewPager(viewPager.apply {
            swipeEnabled = false
            adapter = userListPagerAdapter.apply {
                addFragment(UserListFragment.newInstance(), getString(R.string.user_list))
                addFragment(UserFavoritesFragment.newInstance(), getString(R.string.favorites))
            }
        })

        viewPager.currentItem = if (shouldOpenFavorites) 1 else 0
    }

    override fun openUserDetail() {
        // TODO("not implemented")
    }

    override fun toggleFavorite(user: User) {
        userListViewModel.toggleFavorite(user)
        showFavoriteToast(user.isFavorite)
    }

    private fun showFavoriteToast(favoriteSaved: Boolean) {
        val message = if (favoriteSaved) R.string.favorite_saved else R.string.favorite_removed
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    override fun showError(@StringRes errorMessage: Int) {
        errorSnackbar = Snackbar.make(content, errorMessage, Snackbar.LENGTH_LONG)
        errorSnackbar?.show()
    }

    override fun hideError() {
        errorSnackbar?.dismiss()
    }

    companion object {

        private const val KEY_SHOULD_OPEN_FAVORITES = "KEY_SHOULD_OPEN_FAVORITES"

        fun getStartingIntent(context: Context, shouldOpenFavorites: Boolean): Intent {
            return Intent(context, UserListActivity::class.java).apply {
                putExtras(Bundle().apply {
                    putBoolean(KEY_SHOULD_OPEN_FAVORITES, shouldOpenFavorites)
                })
            }
        }
    }
}
