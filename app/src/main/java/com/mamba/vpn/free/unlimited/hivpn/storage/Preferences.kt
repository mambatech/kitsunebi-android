package com.mamba.vpn.free.unlimited.hivpn.storage

import com.mamba.vpn.free.unlimited.hivpn.R
import android.content.Context
import com.mamba.vpn.free.unlimited.hivpn.MApplication

open class Preferences {
    companion object {
        const val KEY_CONNECT_TIME = "connect_time"

        fun putString(k: String, v: String) {
            val sharedPref = MApplication.sIns.getSharedPreferences(
                    MApplication.sIns.getString(R.string.config_preference), Context.MODE_PRIVATE)
            with(sharedPref.edit()) {
                putString(k, v)
                apply()
            }
        }

        fun getString(k: String, default: String?): String {
            val sharedPref = MApplication.sIns.getSharedPreferences(
                    MApplication.sIns.getString(R.string.config_preference), Context.MODE_PRIVATE)
            if (default != null) {
                return sharedPref.getString(k, default!!)
            } else {
                return sharedPref.getString(k, "")
            }
        }

        fun putBool(k: String, v: Boolean) {
            val sharedPref = MApplication.sIns.getSharedPreferences(
                    MApplication.sIns.getString(R.string.config_preference), Context.MODE_PRIVATE)
            with(sharedPref.edit()) {
                putBoolean(k, v)
                apply()
            }
        }

        fun getBool(k: String, default: Boolean?): Boolean {
            val sharedPref = MApplication.sIns.getSharedPreferences(
                    MApplication.sIns.getString(R.string.config_preference), Context.MODE_PRIVATE)
            if (default != null) {
                return sharedPref.getBoolean(k, default!!)
            } else {
                return sharedPref.getBoolean(k, false)
            }
        }

        fun putInt(k: String, v: Int) {
            val sharedPref = MApplication.sIns.getSharedPreferences(MApplication.sIns.getString(R.string.config_preference), Context.MODE_PRIVATE)
            with(sharedPref.edit()) {
                putInt(k, v)
                apply()
            }
        }

        fun getInt(k: String, default: Int?): Int {
            val sharedPref = MApplication.sIns.getSharedPreferences(
                    MApplication.sIns.getString(R.string.config_preference), Context.MODE_PRIVATE)
            if (default != null) {
                return sharedPref.getInt(k, default!!)
            } else {
                return sharedPref.getInt(k, 0)
            }
        }
    }
}
