package com.randomusers.ui.userlist

import android.support.annotation.StringRes
import com.domain.model.user.User

interface UserListListener {

    fun openUserDetail()

    fun toggleFavorite(user: User)

    fun showError(@StringRes errorMessage: Int)

    fun hideError()
}
