package com.randomusers.ui.userlist

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.domain.model.user.User
import com.randomusers.R
import kotlinx.android.synthetic.main.item_user.view.*

class UserListAdapter : RecyclerView.Adapter<UserListAdapter.ViewHolder>() {

    private lateinit var userList: List<User>

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_user, parent, false))

    override fun getItemCount(): Int = if (::userList.isInitialized) userList.size else 0

    override fun onBindViewHolder(vh: ViewHolder, position: Int) = vh.bind(userList[position])

    fun updateUserList(userList: List<User>) {
        this.userList = userList
        notifyDataSetChanged()
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val context = view.context
        private val thumbnail = view.userPictureThumbnail
        private val name = view.userFullName
        private val address = view.userAddress
        private val email = view.userEmail
        private val mobilePhone = view.userMobilePhone
        private val favoriteIcon = view.favoriteIcon

        fun bind(user: User) {
            Glide.with(context).load(user.picture.thumbnail).into(thumbnail)
            name.text = context.getString(R.string.user_full_name,
                user.name.first.capitalize(), user.name.last.capitalize())
            address.text = context.getString(R.string.user_city_state,
                user.location.city.capitalize(), user.location.state.capitalize())
            email.text = context.getString(R.string.user_email, user.email)
            mobilePhone.text = context.getString(R.string.user_mobile_phone, user.cell)
            favoriteIcon.setOnClickListener { saveFavorite() }
        }

        private fun saveFavorite() {

        }
    }
}