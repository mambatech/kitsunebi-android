package com.mamba.vpn.free.unlimited.hivpn.helper

import com.mamba.vpn.free.unlimited.hivpn.bean.ConnectStatus
import com.mamba.vpn.free.unlimited.hivpn.common.Constants

/**
created by edison 2020/3/16
 */
object VpnConnectMgr {

    var curStatus = ConnectStatus.STOPPED
    var curConnectedIndex = -1

    var curVpnConfig = Constants.JAPAN_CONFIG_1
        set(value) {
            field = value
            curConnectedIndex = when (value) {
                Constants.JAPAN_CONFIG_1 -> {
                    0
                }

                Constants.SINGAPORE_CONFIG_1 -> {
                    1
                }

                Constants.JAPAN_CONFIG_2 -> {
                    2
                }

                Constants.SINGAPORE_CONFIG_2 -> {
                    3
                }

                Constants.JAPAN_CONFIG_3 -> {
                    4
                }

                Constants.SINGAPORE_CONFIG_3 -> {
                    5
                }

                Constants.JAPAN_CONFIG_4 -> {
                    6
                }

                else -> {
                    -1
                }
            }
        }


}