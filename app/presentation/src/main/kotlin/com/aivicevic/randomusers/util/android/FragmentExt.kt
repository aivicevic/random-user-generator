package com.aivicevic.randomusers.util.android

import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import com.aivicevic.randomusers.presentation.R

fun Fragment.setUpToolbar(
    toolbar: Toolbar? = this.view?.findViewById(R.id.toolbar),
    displayHomeAsUpEnabled: Boolean = false,
    isTranslucent: Boolean = false,
    apply: Toolbar.() -> Unit = {}
) = ToolbarUtil.setUpToolbarForFragment(
    this,
    toolbar,
    displayHomeAsUpEnabled,
    isTranslucent,
    apply
)
