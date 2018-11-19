package com.randomusers.ui.userlist

import android.support.annotation.StringRes
import com.domain.model.user.User

enum class UserListStateType { DEFAULT, LOADING, ERROR }

class UserListState(val stateType: UserListStateType, val data: List<User>) {

    @StringRes var errorMessage: Int? = null

    constructor(stateType: UserListStateType, data: List<User>, @StringRes errorMessage: Int) : this(stateType, data) {
        this.errorMessage = errorMessage
    }
}