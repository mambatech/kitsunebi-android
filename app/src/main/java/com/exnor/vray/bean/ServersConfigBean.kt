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
        val IP: String,
        val Port: Int,
        val UUID: String,
        val country_name: String,
        val icon_url: String,
        @ConnectStatus var status: Int,
        var isSelected: Boolean
)