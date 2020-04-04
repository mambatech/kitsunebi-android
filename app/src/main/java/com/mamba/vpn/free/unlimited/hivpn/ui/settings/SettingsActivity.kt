package com.mamba.vpn.free.unlimited.hivpn.ui.settings

import com.mamba.vpn.free.unlimited.hivpn.R
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class SettingsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)
        supportFragmentManager
                .beginTransaction()
                .add(R.id.content_settings, SettingsFragment())
                .commit()
    }
}