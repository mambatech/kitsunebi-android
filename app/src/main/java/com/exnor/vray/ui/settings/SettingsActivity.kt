package com.exnor.vray.ui.settings

import com.exnor.vray.R
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