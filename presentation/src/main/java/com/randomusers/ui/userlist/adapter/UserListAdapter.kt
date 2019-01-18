package com.randomusers.ui.userlist.adapter

import android.os.Handler
import android.support.v7.util.DiffUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.domain.model.user.User
import com.randomusers.R
import com.randomusers.ui.userlist.UserListListener
import com.randomusers.ui.util.StringUtil
import kotlinx.android.synthetic.main.item_user.view.*

class UserListAdapter(private val userListListener: UserListListener?) :
    RecyclerView.Adapter<UserListAdapter.ViewHolder>() {

    private lateinit var userList: List<User>

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_user, parent, false))

    override fun getItemCount(): Int = if (::userList.isInitialized) userList.size else 0

    override fun onBindViewHolder(vh: ViewHolder, position: Int) =
        vh.bind(userList[position], userListListener)

    fun updateUserList(newUserList: List<User>) {
        if (!::userList.isInitialized) {
            userList = newUserList
            notifyItemRangeInserted(0, userList.size)
        } else {
            val handler = Handler()
            Thread {
                val result = DiffUtil.calculateDiff(object: DiffUtil.Callback() {
                    override fun getOldListSize() = userList.size

                    override fun getNewListSize() = newUserList.size

                    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int) =
                        userList[oldItemPosition].id.value == newUserList[newItemPosition].id.value

                    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int) =
                        userList[oldItemPosition] == newUserList[newItemPosition]
                })

                handler.post {
                    userList = newUserList
                    result.dispatchUpdatesTo(this)
                }
            }.start()
        }
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
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
