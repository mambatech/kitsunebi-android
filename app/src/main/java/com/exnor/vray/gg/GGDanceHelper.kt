package com.exnor.vray.gg

import android.app.Activity
import android.content.Context
import android.util.Log
import com.bytedance.sdk.openadsdk.*
import com.exnor.vray.MApplication

/**
created by edison 2020/5/1
 */
object GGDanceHelper {

    private val tag = "GGDanceHelper"
    private var mTTAdNative: TTAdNative? = null
    private var mttFullVideoAd: TTFullScreenVideoAd? = null
    private var mttRewardVideoAd: TTRewardVideoAd? = null
    val CODE_FULL_SCREEN_GG = "945153718"
    val CODE_REWARD_SCREEN_GG = "945161784"

    fun initDanceGG(context: Context) {
        val ttAdManager: TTAdManager = TTAdManagerHolder.get()
        mTTAdNative = ttAdManager.createAdNative(MApplication.sIns)
    }

    fun loadFullScreenAd(codeId: String){
        var adSlot = AdSlot.Builder()
                .setCodeId(codeId)
                .setSupportDeepLink(true)
                .setOrientation(TTAdConstant.VERTICAL)
                .build()

        mTTAdNative?.loadFullScreenVideoAd(adSlot,object : TTAdNative.FullScreenVideoAdListener{
            override fun onFullScreenVideoAdLoad(p0: TTFullScreenVideoAd?) {
                Log.e(tag,"onFullScreenVideoAdLoad")
                mttFullVideoAd = p0
            }

            override fun onFullScreenVideoCached() {
                Log.e(tag,"onFullScreenVideoCached")
            }

            override fun onError(p0: Int, p1: String?) {
                Log.e(tag,"onFullAdError: code="+p0 + "_" +"msg:"+p1)

            }

        })
    }

    fun showFullScreenAd(act: Activity){
        if (mttFullVideoAd != null) {
            mttFullVideoAd?.showFullScreenVideoAd(act, TTAdConstant.RitScenes.GAME_FINISH_REWARDS, null)
        }

        mttFullVideoAd = null
    }

    fun loadRewardAd(codeId: String){
        //模板广告需要设置期望个性化模板广告的大小,单位dp,代码位是否属于个性化模板广告，请在穿山甲平台查看
        val adSlot = AdSlot.Builder()
                .setCodeId(codeId)
                .setSupportDeepLink(true)
                .setRewardName("金币") //奖励的名称
                .setRewardAmount(1) //奖励的数量
                .setUserID("user123") //用户id,必传参数
                .setMediaExtra("media_extra") //附加参数，可选
                .setOrientation(TTAdConstant.VERTICAL)
                .build()

        mTTAdNative?.loadRewardVideoAd(adSlot,object : TTAdNative.RewardVideoAdListener{
            override fun onRewardVideoAdLoad(p0: TTRewardVideoAd?) {
                Log.e(tag,"onRewardVideoAdLoad")
                mttRewardVideoAd = p0
            }

            override fun onRewardVideoCached() {
                Log.e(tag,"onRewardVideoCached")
            }

            override fun onError(p0: Int, p1: String?) {
                Log.e(tag,"onRewardAdError: code="+p0 + "_" +"msg:"+p1)
            }

        })
    }

    fun showRewardAd(act: Activity){
        if (mttRewardVideoAd != null){
            mttRewardVideoAd?.showRewardVideoAd(act)
        }

        mttRewardVideoAd = null
    }

}