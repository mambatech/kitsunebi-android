package com.exnor.vray.bean

import com.google.gson.annotations.SerializedName

/**
created by edison 2020/5/31
 */
data class ConfigBean (
        @SerializedName("new_version")
        val newVersion: List<NewVersion>,

        @SerializedName("gp_link")
        val gpLink: String,

        @SerializedName("update_log")
        val updateLog: String,

        @SerializedName("apk_link")
        val apkLink: String
) {

}

data class NewVersion (
        val version: Int,

        @SerializedName("use_gp")
        val useGp: Boolean
)
