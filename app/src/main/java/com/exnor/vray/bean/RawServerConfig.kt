package com.exnor.vray.bean

import com.google.gson.annotations.SerializedName

/**
created by edison 2020/6/7
 */
data class RawServerConfig(
        @SerializedName("Code")
        var code: Int,
        @SerializedName("servers_config")
        var rawConfig: String
) {
}