package com.randomusers.ui.userlist.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.domain.model.user.User
import com.randomusers.R
import com.randomusers.ui.userlist.UserListListener
import com.randomusers.util.StringUtil
import kotlinx.android.synthetic.main.item_user.view.*

class UserListAdapter(private val userListListener: UserListListener?) :
    ListAdapter<User, UserListAdapter.UserViewHolder>(UserListDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder =
        UserViewHolder(LayoutInflater.from(parent.context)
            .inflate(R.layout.item_user, parent, false))

    override fun onBindViewHolder(vh: UserViewHolder, position: Int) =
        vh.bind(getItem(position), userListListener)

    inner class UserViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        private val context = view.context
        private val thumbnail = view.userPictureThumbnail
        private val name = view.userFullName
        private val address = view.userAddress
        private val email = view.userEmail
        private val mobilePhone = view.userMobilePhone
        private val favoriteIcon = view.favoriteIcon

        fun bind(user: User, userListListener: UserListListener?) {
            Glide.with(context).load(user.picture.thumbnail).into(thumbnail)
            name.text = context.getString(
                R.string.user_full_name,
                StringUtil.capitalizeWords(user.name.first),
                StringUtil.capitalizeWords(user.name.last)
            )
            address.text = context.getString(
                R.string.user_city_state,
                StringUtil.capitalizeWords(user.location.city),
                StringUtil.capitalizeWords(user.location.state)
            )
            email.text = context.getString(R.string.user_email, user.email)
            mobilePhone.text = context.getString(R.string.user_mobile_phone, user.cell)
            favoriteIcon.setOnClickListener {
                userListListener?.toggleFavorite(user)
                toggleFavoriteIcon(user.isFavorite)
            }
            toggleFavoriteIcon(user.isFavorite)
        }

        private fun toggleFavoriteIcon(isFavorite: Boolean) {
            if (isFavorite) {
                favoriteIcon.setImageResource(R.drawable.icon_favorite_selected)
            } else {
                favoriteIcon.setImageResource(R.drawable.icon_favorite)
            }
        }
    }
}
