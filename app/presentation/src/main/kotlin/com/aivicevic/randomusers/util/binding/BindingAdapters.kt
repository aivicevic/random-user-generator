package com.aivicevic.randomusers.util.binding

import android.view.View
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide

object BindingAdapters {

    /**
     * Sets visibility between [View.VISIBLE] and [View.GONE] based on flag
     */
    @JvmStatic
    @BindingAdapter("visibleGone")
    fun visibleGone(view: View, isVisible: Boolean) {
        view.visibility = if (isVisible) View.VISIBLE else View.GONE
    }

    @JvmStatic
    @BindingAdapter("imageUrl")
    fun imageViewUrl(view: ImageView, imageUrl: String) {
        Glide.with(view.context).load(imageUrl).into(view)
    }
}
