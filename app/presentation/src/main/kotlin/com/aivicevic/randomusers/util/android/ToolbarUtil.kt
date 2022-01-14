package com.aivicevic.randomusers.util.android

import android.content.Context
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment

object ToolbarUtil {

    private fun setUpToolbarForActivity(
        toolbar: Toolbar,
        activity: AppCompatActivity,
        translucentStatusBar: Boolean,
        displayHomeAsUpEnabled: Boolean = true
    ) {
        val marginLayoutParams = toolbar.layoutParams as ViewGroup.MarginLayoutParams
        marginLayoutParams.setMargins(
            marginLayoutParams.marginStart,
            marginLayoutParams.topMargin,
            marginLayoutParams.marginEnd,
            marginLayoutParams.bottomMargin

        )

        activity.setSupportActionBar(toolbar)

        val actionBar = activity.supportActionBar
        actionBar?.setDisplayHomeAsUpEnabled(displayHomeAsUpEnabled)
    }

    @JvmOverloads
    fun setUpToolbarForFragment(
        fragment: Fragment,
        toolbar: Toolbar?,
        displayHomeAsUpEnabled: Boolean = false,
        translucentStatusBar: Boolean = false,
        apply: (Toolbar) -> Unit = {}
    ) {
        if (toolbar != null && fragment.activity != null) {
            setUpToolbarForActivity(
                toolbar,
                fragment.activity as AppCompatActivity,
                translucentStatusBar,
                displayHomeAsUpEnabled
            )
        }
        toolbar?.let {
            apply(it)
        }
    }

    /**
     * Returns the height of the status bar in pixels, based on the current device configuration(Orientation, Language, Screen Density, etc... ).
     */
    private fun getStatusBarHeight(context: Context): Int {
        var result = 0
        val resourceId = context.resources.getIdentifier("status_bar_height", "dimen", "android")
        if (resourceId > 0) {
            result = context.resources.getDimensionPixelSize(resourceId)
        }
        return result
    }
}
