package com.mamba.vpn.free.unlimited.hivpn.ui.perapp

import com.mamba.vpn.free.unlimited.hivpn.R
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class PerAppActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_per_app)
        supportFragmentManager
                .beginTransaction()
                .add(R.id.content_per_app, PerAppFragment())
                .commit()
    }
}