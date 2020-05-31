package com.exnor.vray.utils

import android.content.Context
import android.content.pm.PackageManager

/**
created by edison 2020/5/31
 */
object Utils {


    fun getVersionCode(context: Context): Int {
        val manager = context.packageManager
        var code = 0
        try {
            val info = manager.getPackageInfo(context.packageName, 0)
            code = info.versionCode
        } catch (e: PackageManager.NameNotFoundException) {
            e.printStackTrace()
        }
        return code
    }

}