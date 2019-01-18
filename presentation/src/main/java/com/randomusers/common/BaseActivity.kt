package com.randomusers.common

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.randomusers.R

abstract class BaseActivity : AppCompatActivity() {

    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right)
    }

    override fun onPostCreate(savedInstanceState: Bundle?) {
        initUI()
        super.onPostCreate(savedInstanceState)
    }

    /*
     * Generally, this can be called in onCreate() to initialize UI elements. However,
     * calling this in onPostCreate() ensures all views are measured and available before
     * implementing further modifications such as click listeners, etc.
     */
    protected abstract fun initUI()
}
