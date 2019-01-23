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
        fetchUserButton.setOnClickListener {
            // TODO: Open UserDetail
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
        }

        fetchUsersButton.setOnClickListener {
            startActivity(UserListActivity.getStartingIntent(this, false))
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
        }

        favoritesButton.setOnClickListener {
            startActivity(UserListActivity.getStartingIntent(this, true))
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
        }
    }
}
