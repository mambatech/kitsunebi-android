package com.exnor.vray.ui

import androidx.appcompat.app.AppCompatActivity
import com.umeng.analytics.MobclickAgent

/**
 * Created by cyclone on 2020/6/2.
 */

abstract class BaseActivity : AppCompatActivity() {
    override fun onResume() {
        super.onResume()
        MobclickAgent.onResume(this)
    }

    override fun onPause() {
        super.onPause()
        MobclickAgent.onPause(this)
    }
}