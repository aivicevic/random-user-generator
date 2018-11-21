package com.randomusers.ui.userlist

import com.domain.model.user.User

interface UserListListener {

    fun openUserDetail()

    fun toggleFavorite(user: User)
}