package com.exnor.vray.storage

import com.exnor.vray.R
import android.content.Context
import com.exnor.vray.MApplication

open class Preferences {
    companion object {
        const val KEY_CONNECT_TIME = "connect_time"
        private const val KEY_ENTER_TIMES = "enter_times" // APP启动次数

        var enterTimes: Int
            set(value) {
                putInt(KEY_ENTER_TIMES, value)
            }
        get() = getInt(KEY_ENTER_TIMES, 0)

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
            return if (default != null) {
                sharedPref.getString(k, default) ?: default
            } else {
                sharedPref.getString(k, "") ?: ""
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
            return if (default != null) {
                sharedPref.getBoolean(k, default)
            } else {
                sharedPref.getBoolean(k, false)
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
            return if (default != null) {
                sharedPref.getInt(k, default)
            } else {
                sharedPref.getInt(k, 0)
            }
        }
    }
}
