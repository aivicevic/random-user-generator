package com.randomusers.ui.home

import android.content.Context
import com.randomusers.common.BaseViewModel
import com.randomusers.ui.userlist.UserListActivity
import javax.inject.Inject

class HomeViewModel @Inject constructor() : BaseViewModel() {

    fun fetchUser(context: Context) {
        // Directly to user info activity
    }

    fun fetchUserList(context: Context, openFavoritesList: Boolean) {
        context.startActivity(UserListActivity.getStartingIntent(context, openFavoritesList))
    }
}
