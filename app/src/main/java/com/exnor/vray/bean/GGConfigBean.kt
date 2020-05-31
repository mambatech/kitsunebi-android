package com.exnor.vray.bean

import com.google.gson.annotations.SerializedName

/**
created by edison 2020/5/31
 */
data class GGConfigBean(
        @SerializedName("need_show_ad")
        val needShowAd: Boolean,

        @SerializedName("ad_ids")
        val adIDS: AdIDS
) {
}

data class AdIDS(
        @SerializedName("main_page_native_id")
        val mainPageNativeID: String,

        @SerializedName("connected_reward_id")
        val connectedRewardID: String,

        @SerializedName("resume_full_id")
        val resumeFullID: String
)
