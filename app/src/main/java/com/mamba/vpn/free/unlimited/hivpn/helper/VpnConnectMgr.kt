package com.mamba.vpn.free.unlimited.hivpn.helper

import com.mamba.vpn.free.unlimited.hivpn.bean.ConnectStatus
import com.mamba.vpn.free.unlimited.hivpn.common.Constants

/**
created by edison 2020/3/16
 */
object VpnConnectMgr {

    var curStatus = ConnectStatus.STOPPED
    var curConnectedIndex = -1

    var curVpnConfig = Constants.JAPAN_CONFIG
        set(value) {
            field = value
            curConnectedIndex = when (value) {
                Constants.JAPAN_CONFIG -> {
                    0
                }

                Constants.SINGAPORE_CONFIG -> {
                    1
                }

                else -> {
                    -1
                }
            }
        }


}