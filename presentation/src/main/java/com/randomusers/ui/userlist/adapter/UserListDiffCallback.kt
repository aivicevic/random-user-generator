package com.randomusers.ui.userlist.adapter

import androidx.recyclerview.widget.DiffUtil
import com.domain.model.user.User

class UserListDiffCallback : DiffUtil.ItemCallback<User>() {

    override fun areItemsTheSame(oldItem: User, newItem: User): Boolean =
        (oldItem.id.value == newItem.id.value)

    override fun areContentsTheSame(oldItem: User, newItem: User): Boolean = (oldItem == newItem)
}