package com.exnor.vray.bean

import androidx.annotation.DrawableRes
import androidx.annotation.IntDef
import com.exnor.vray.bean.ConnectStatus.Companion.CONNECTED
import com.exnor.vray.bean.ConnectStatus.Companion.CONNECTING
import com.exnor.vray.bean.ConnectStatus.Companion.STOPPED

/**
created by edison 2020/3/16
 //TODO configJsonStr需要改成实体类
 */
data class VpnItemBean(@ConnectStatus var status: Int,
                       @DrawableRes val resCountry: Int,
                       val countryName: String,
                       var isSelected: Boolean,
                       val configJson: String) {

}


@IntDef(STOPPED, CONNECTING, CONNECTED)
@kotlin.annotation.Retention(AnnotationRetention.SOURCE)
annotation class ConnectStatus {
    companion object {
        const val STOPPED = 0
        const val CONNECTING = 1
        const val CONNECTED = 2
    }
}