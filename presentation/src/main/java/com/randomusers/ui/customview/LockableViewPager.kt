package com.randomusers.ui.customview

import android.annotation.SuppressLint
import android.content.Context
import android.support.v4.view.ViewPager
import android.util.AttributeSet
import android.view.MotionEvent

class LockableViewPager(
    context: Context,
    attributeSet: AttributeSet?
) : ViewPager(context, attributeSet) {

    var swipeEnabled = true

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(event: MotionEvent): Boolean =
        if (this.swipeEnabled) super.onTouchEvent(event) else false

    override fun onInterceptTouchEvent(event: MotionEvent): Boolean =
        if (this.swipeEnabled) super.onInterceptTouchEvent(event) else false
}
