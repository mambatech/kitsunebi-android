package com.exnor.vray.gg

import android.app.Activity
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.bytedance.sdk.openadsdk.*
import com.exnor.vray.MApplication
import com.exnor.vray.R

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
    val CODE_MAIN_PAGE_GG = "945191135"
    private var context: Context? = null

    fun initDanceGG(context: Context) {
        this.context = context
        val ttAdManager: TTAdManager = TTAdManagerHolder.get()
        mTTAdNative = ttAdManager.createAdNative(MApplication.sIns)
    }

    fun loadFullScreenAdAndShow(codeId: String,act: Activity){
        var adSlot = AdSlot.Builder()
                .setCodeId(codeId)
                .setSupportDeepLink(true)
                .setOrientation(TTAdConstant.VERTICAL)
                .build()

        mTTAdNative?.loadFullScreenVideoAd(adSlot,object : TTAdNative.FullScreenVideoAdListener{
            override fun onFullScreenVideoAdLoad(p0: TTFullScreenVideoAd?) {
                Log.e(tag,"onFullScreenVideoAdLoad")
                mttFullVideoAd = p0
                mttFullVideoAd?.showFullScreenVideoAd(act, TTAdConstant.RitScenes.GAME_FINISH_REWARDS, null)
            }

            override fun onFullScreenVideoCached() {
                Log.e(tag,"onFullScreenVideoCached")
            }

            override fun onError(p0: Int, p1: String?) {
                Log.e(tag,"onFullAdError: code="+p0 + "_" +"msg:"+p1)

            }

        })
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

    fun loadMainPageAdAndShow(adContainer: ViewGroup){
        val adSlot = AdSlot.Builder()
                .setCodeId(CODE_MAIN_PAGE_GG)
                .setSupportDeepLink(true)
                .setImageAcceptedSize(640, 320)
                .setAdCount(1) //请求广告数量为1到3条
                .build()

        mTTAdNative?.loadFeedAd(adSlot,object : TTAdNative.FeedAdListener{
            override fun onFeedAdLoad(p0: MutableList<TTFeedAd>?) {
                if (p0?.isNotEmpty() == true){
                    fillMainPageSmallPicAd(p0[0],adContainer)
                }
            }

            override fun onError(p0: Int, p1: String?) {
                Log.e(tag,"errorCode:$p0 _ errorMsg:$p1")
            }

        })
    }

    private fun fillMainPageSmallPicAd(nativeAd: TTFeedAd,adContainer: ViewGroup){
        adContainer.removeAllViews()
        val adView = LayoutInflater.from(context).inflate(R.layout.listitem_ad_small_pic,null)
        adContainer.addView(adView)

        val tvTitle = adView.findViewById<TextView>(R.id.tv_listitem_ad_title)
        val tvDesc = adView.findViewById<TextView>(R.id.tv_listitem_ad_desc)
        val tvSource = adView.findViewById<TextView>(R.id.tv_listitem_ad_source)
        val ivImage = adView.findViewById<ImageView>(R.id.iv_listitem_image)
        val ivIcon = adView.findViewById<ImageView>(R.id.iv_listitem_icon)
        val flVideo = adView.findViewById<FrameLayout>(R.id.iv_listitem_video)

        tvTitle.text = nativeAd.title
        tvDesc.text = nativeAd.description
        tvSource.text = if (nativeAd.source == null) "广告来源" else nativeAd.source
        val icon: TTImage? = nativeAd.icon
        if (icon?.isValid == true && context != null) {
            Glide.with(context!!)
                    .load(icon.imageUrl)
                    .into(ivIcon)
        }

        if (nativeAd.imageMode == TTAdConstant.IMAGE_MODE_VIDEO) {
            val video: View? = nativeAd.adView
            if (video != null) {
                flVideo.visibility = View.VISIBLE
                flVideo.removeAllViews()
                flVideo.addView(video)
            }
        } else {
            if (nativeAd.imageList != null && nativeAd.imageList.isNotEmpty()) {
                val image: TTImage = nativeAd.imageList[0]
                if (context != null && image.isValid) {
                    ivImage.visibility = View.VISIBLE
                    Glide.with(context!!).load(image.imageUrl).into(ivImage)
                }
            }
        }

        nativeAd.registerViewForInteraction(adContainer,adContainer,object : TTNativeAd.AdInteractionListener{
            override fun onAdClicked(p0: View?, p1: TTNativeAd?) {

            }

            override fun onAdShow(p0: TTNativeAd?) {

            }

            override fun onAdCreativeClick(p0: View?, p1: TTNativeAd?) {

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