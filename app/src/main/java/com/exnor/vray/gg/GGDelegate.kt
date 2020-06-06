package com.exnor.vray.gg

import android.app.Activity
import android.view.ViewGroup
import com.exnor.vray.MApplication
import java.util.*

/**
 * 广告中间代理
 * Created by cyclone on 2020/6/6.
 */

object GGDelegate {
    private val isCn : Boolean
        get() {
            return Locale.CHINA.country == MApplication.sIns.resources.configuration.locale.country
        }

    fun loadMainPageGGAndShow(container: ViewGroup) {
//        if (isCn) {
//            GGDanceHelper.loadMainPageAdAndShow(container)
//        } else {
//            GGHelper.loadAndShowMainPageAd(
//                    container.context,
//                    UnifiedNativeAdView(container.context),
//                    container
//            )
//        }
        GGDanceHelper.loadMainPageAdAndShow(container)
    }

    fun loadEnterFullScreenGGAndShow(activity: Activity) {
        if (isCn) {
            GGDanceHelper.loadFullScreenAdAndShow(GGDanceHelper.CODE_FULL_SCREEN_GG, activity)
        } else {
            GGHelper.loadEnterGGAndShow(activity)
        }
    }

    fun loadRewardGG() {
//        if (isCn) {
//            GGDanceHelper.loadRewardAd(GGDanceHelper.CODE_REWARD_SCREEN_GG)
//        } else {
//            GGHelper.loadRewardVideoGG(MApplication.sIns)
//        }
        GGDanceHelper.loadRewardAd(GGDanceHelper.CODE_REWARD_SCREEN_GG)
    }

    fun showRewardGG(activity: Activity) {
//        if (isCn) {
//            GGDanceHelper.showRewardAd(activity, GGDanceHelper.CODE_REWARD_SCREEN_GG)
//        } else {
//            GGHelper.showRewardVideoGG()
//        }
        GGDanceHelper.showRewardAd(activity, GGDanceHelper.CODE_REWARD_SCREEN_GG)
    }
}