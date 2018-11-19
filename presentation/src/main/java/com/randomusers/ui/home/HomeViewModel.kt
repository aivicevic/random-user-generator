package com.randomusers.ui.home

import android.content.Context
import android.content.Intent
import com.randomusers.common.BaseViewModel
import com.randomusers.ui.userlist.UserListActivity
import javax.inject.Inject

class HomeViewModel @Inject constructor() : BaseViewModel() {

    fun fetchUser(context: Context) {
        // Directly to user info activity
    }

    fun fetchUsers(context: Context) {
        context.startActivity(Intent(context, UserListActivity::class.java))
    }
}