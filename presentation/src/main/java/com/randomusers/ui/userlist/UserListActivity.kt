package com.randomusers.ui.userlist

import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.annotation.StringRes
import android.support.design.widget.Snackbar
import android.widget.Toast
import com.domain.model.user.User
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
    private var openFavoritesList: Boolean = false
    private var errorSnackbar: Snackbar? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_list)

        processIncomingIntent()
        initViewModel()
    }

    override fun initUI() {
        tabLayout.setupWithViewPager(viewPager.apply {
            swipeEnabled = false
            adapter = userListPagerAdapter.apply {
                addFragment(UserListFragment.newInstance(), getString(R.string.user_list))
                addFragment(UserFavoritesFragment.newInstance(), getString(R.string.favorites))
            }
        })

        viewPager.currentItem = if (openFavoritesList) 1 else 0
    }

    override fun openUserDetail() {
        // TODO("not implemented")
    }

    override fun toggleFavorite(user: User) {
        userListViewModel.toggleFavorite(user)
        showFavoriteToast(user.isFavorite)
    }

    override fun showError(@StringRes errorMessage: Int) {
        errorSnackbar = Snackbar.make(content, errorMessage, Snackbar.LENGTH_LONG)
        errorSnackbar?.show()
    }

    override fun hideError() {
        errorSnackbar?.dismiss()
    }

    private fun processIncomingIntent() {
        if (null != intent && null != intent.extras) {
            openFavoritesList = intent!!.extras!!.getBoolean(KEY_OPEN_FAVORITES_LIST, false)
        }
    }

    private fun initViewModel() {
        userListViewModel =
                ViewModelProviders.of(this, viewModelFactory).get(UserListViewModel::class.java)
    }

    private fun showFavoriteToast(favoriteSaved: Boolean) {
        val message = if (favoriteSaved) R.string.favorite_saved else R.string.favorite_removed
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    companion object {
        private const val KEY_OPEN_FAVORITES_LIST = "KEY_OPEN_FAVORITES_LIST"

        fun getStartingIntent(context: Context, openFavoritesList: Boolean): Intent {
            return Intent(context, UserListActivity::class.java).apply {
                putExtras(Bundle().apply { putBoolean(KEY_OPEN_FAVORITES_LIST, openFavoritesList) })
            }
        }
    }
}
