package com.exnor.vray.bean

import com.google.gson.annotations.SerializedName

/**
created by edison 2020/6/7
 */
data class ServersConfig (
        @SerializedName("need_update")
        val needUpdate: Boolean,

        @SerializedName("servers_config")
        val serversConfig: List<ServersConfigItem>
) {
}

data class ServersConfigItem (
        val ip: String,
        val port: Int,
        val uuid: String
)