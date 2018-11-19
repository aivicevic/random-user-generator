package com.randomusers.common

import android.support.v7.app.AppCompatActivity
import com.randomusers.R

abstract class BaseActivity : AppCompatActivity() {

    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right)
    }
}