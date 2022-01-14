package com.aivicevic.randomusers.presentation.main.userlist.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.aivicevic.randomusers.domain.model.user.User
import com.aivicevic.randomusers.presentation.R
import com.aivicevic.randomusers.presentation.databinding.ListItemUserBinding
import com.aivicevic.randomusers.util.StringUtil
import com.bumptech.glide.Glide

class UserListAdapter(
    private val onUserClick: (User) -> Unit,
    private val onFavoriteIconClick: (User) -> Unit
) : ListAdapter<User, UserListAdapter.ViewHolder>(UserListDiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.list_item_user,
                parent,
                false
            )
        ).apply {
            itemView.setOnClickListener {
                onUserClick(getItem(layoutPosition))
            }
        }
    }

    override fun onBindViewHolder(vh: ViewHolder, position: Int) {
        vh.bind(getItem(position), onFavoriteIconClick)
    }

    class ViewHolder(
        private val binding: ListItemUserBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(user: User, onFavoriteIconClick: (User) -> Unit) {
            val context = binding.root.context
            with(binding) {
                Glide.with(context).load(user.picture.thumbnail).into(ivProfile)
                tvName.text = context.getString(
                    R.string.user_full_name,
                    StringUtil.capitalizeWords(user.name.first),
                    StringUtil.capitalizeWords(user.name.last)
                )
                tvAddress.text = context.getString(
                    R.string.user_city_state,
                    StringUtil.capitalizeWords(user.location.city),
                    StringUtil.capitalizeWords(user.location.state)
                )
                tvEmail.text = context.getString(R.string.user_email, user.email)
                tvMobilePhone.text = context.getString(R.string.user_mobile_phone, user.cell)
                ivFavoriteIcon.apply {
                    val iconResource = if (user.isFavorite) {
                        R.drawable.icon_favorite_selected
                    } else {
                        R.drawable.icon_favorite
                    }
                    setImageResource(iconResource)

                    setOnClickListener {
                        // Hacky? Google's dumb logic for diffs makes this difficult
                        onFavoriteIconClick(user)
                    }
                }
            }
        }
    }

    object UserListDiffCallback : DiffUtil.ItemCallback<User>() {

        override fun areItemsTheSame(oldItem: User, newItem: User): Boolean =
            (oldItem.id.value == newItem.id.value) &&
                (oldItem.isFavorite == newItem.isFavorite)

        override fun areContentsTheSame(oldItem: User, newItem: User): Boolean =
            (oldItem == newItem)
    }
}
