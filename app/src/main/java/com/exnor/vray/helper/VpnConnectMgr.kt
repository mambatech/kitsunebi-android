package com.exnor.vray.helper

import com.exnor.vray.bean.ConnectStatus
import com.exnor.vray.common.Constants

/**
created by edison 2020/3/16
 */
object VpnConnectMgr {

    var curStatus = ConnectStatus.STOPPED

    //todo 需要优化。。
    var currentSelectedPosition = 1

    var curVpnConfig = Constants.SINGAPORE_CONFIG_1
        set(value) {
            field = value
            when(value){
                Constants.JAPAN_CONFIG_1 -> currentSelectedPosition = 0
                Constants.SINGAPORE_CONFIG_1 -> currentSelectedPosition = 1
                Constants.JAPAN_CONFIG_2 -> currentSelectedPosition = 2
                Constants.SINGAPORE_CONFIG_2 -> currentSelectedPosition = 3
            }
        }


}