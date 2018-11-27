package com.randomusers.common

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.randomusers.R

abstract class BaseActivity : AppCompatActivity() {

    // TODO: Call finish() here?
    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right)
    }

    override fun onPostCreate(savedInstanceState: Bundle?) {
        initUI()
        super.onPostCreate(savedInstanceState)
    }

    // TODO: Needed?
    protected abstract fun initUI()
}
