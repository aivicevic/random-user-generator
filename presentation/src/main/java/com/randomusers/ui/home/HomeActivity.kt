package com.randomusers.ui.home

import android.os.Bundle
import com.randomusers.R
import com.randomusers.common.BaseActivity
import com.randomusers.ui.userlist.UserListActivity
import kotlinx.android.synthetic.main.activity_home.*

class HomeActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        initUI()
    }

    override fun initUI() {
        fetchUserButton.setOnClickListener { navigateToUserDetail() }
        fetchUsersButton.setOnClickListener { navigateToUserList(false) }
        favoritesButton.setOnClickListener { navigateToUserList(true) }
    }

    private fun navigateToUserDetail() {
        // TODO: Open UserDetail
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
    }

    private fun navigateToUserList(isOpenFavoritesOnStart: Boolean) {
        startActivity(UserListActivity.getStartingIntent(this, isOpenFavoritesOnStart))
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
    }
}
