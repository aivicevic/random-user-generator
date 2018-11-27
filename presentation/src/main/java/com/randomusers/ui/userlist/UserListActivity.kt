package com.randomusers.ui.userlist

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.annotation.StringRes
import android.support.design.widget.Snackbar
import android.widget.Toast
import com.randomusers.R
import com.randomusers.common.BaseActivity
import com.randomusers.ui.userlist.adapter.UserListPagerAdapter
import com.randomusers.ui.userlist.tab.UserFavoritesFragment
import com.randomusers.ui.userlist.tab.UserListFragment
import kotlinx.android.synthetic.main.activity_user_list.*

class UserListActivity : BaseActivity(), UserListListener {

    private val userListPagerAdapter = UserListPagerAdapter(supportFragmentManager)
    private var errorSnackbar: Snackbar? = null
    private var isFavoritesList = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_list)

        processIncomingIntent()
    }

    override fun initUI() {
        tabLayout.setupWithViewPager(viewPager.apply {
            swipeEnabled = false
            adapter = userListPagerAdapter.apply {
                addFragment(UserListFragment.newInstance(), getString(R.string.user_list))
                addFragment(UserFavoritesFragment.newInstance(), getString(R.string.favorites))
            }
        })
    }

    override fun openUserDetail() {
        // TODO("not implemented")
    }

    override fun showFavoriteToast(favoriteSaved: Boolean) {
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

    private fun processIncomingIntent() {
        if (null != intent && null != intent.extras) {
            isFavoritesList = intent!!.extras!!.getBoolean(KEY_IS_FAVORITES_LIST, false)
        }
    }

    companion object {

        private const val KEY_IS_FAVORITES_LIST = "KEY_IS_FAVORITES_LIST"

        fun getStartingIntent(context: Context, isFavoritesList: Boolean): Intent {
            val intent = Intent(context, UserListActivity::class.java)
            val extras = Bundle()
            extras.putBoolean(KEY_IS_FAVORITES_LIST, isFavoritesList)
            intent.putExtras(extras)

            return intent
        }
    }
}
