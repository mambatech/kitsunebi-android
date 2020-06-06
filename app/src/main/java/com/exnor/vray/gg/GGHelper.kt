package com.exnor.vray.gg

import android.content.Context
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import com.exnor.vray.R
import com.exnor.vray.record.RecordGG
import com.google.android.gms.ads.*
import com.google.android.gms.ads.formats.MediaView
import com.google.android.gms.ads.formats.NativeAdOptions
import com.google.android.gms.ads.formats.UnifiedNativeAd
import com.google.android.gms.ads.formats.UnifiedNativeAdView
import com.google.android.gms.ads.reward.RewardItem
import com.google.android.gms.ads.reward.RewardedVideoAd
import com.google.android.gms.ads.reward.RewardedVideoAdListener


/**
created by edison 2020/3/22
 */
object GGHelper {

    val tag = GGHelper::class.java.simpleName
    val GG_ENTER_APP_KEY = "ca-app-pub-8917831695584667/7063694773"
//    val GG_ENTER_APP_KEY = "ca-app-pub-3940256099942544/1033173712" //测试

    val GG_MAIN_PAGE_NATIVE = "ca-app-pub-8917831695584667/1746902493"
//    val GG_MAIN_PAGE_NATIVE = "ca-app-pub-3940256099942544/2247696110"   //测试

    val GG_REWARD = "ca-app-pub-8917831695584667/5012704988"
//    val GG_REWARD = "ca-app-pub-3940256099942544/5224354917" //测试

    private var enterInterstitialAd: InterstitialAd? = null
    private var mainPageAdLoader: AdLoader? = null
    private var currentNativeAd: UnifiedNativeAd? = null
    private var rewardedVideoAd: RewardedVideoAd? = null

    var rewardGGListener: RewardGGListener? = null

    interface RewardGGListener{
        fun onRewarded(reward: RewardItem?)
        fun onGGClosed()
    }

    fun loadRewardVideoGG(context: Context) {
        if (rewardedVideoAd == null) {
            rewardedVideoAd = MobileAds.getRewardedVideoAdInstance(context)

            rewardedVideoAd!!.rewardedVideoAdListener = object : RewardedVideoAdListener{
                override fun onRewardedVideoAdClosed() {
                    rewardGGListener?.onGGClosed()
                }

                override fun onRewardedVideoAdLeftApplication() {}

                override fun onRewardedVideoAdLoaded() {
                    RecordGG.recordGGLoadResult(
                            GG_REWARD,
                            RecordGG.VALUE_REWARD,
                            RecordGG.VALUE_SUCCESS
                    )
                }

                override fun onRewardedVideoAdOpened() {}

                override fun onRewardedVideoCompleted() {}

                override fun onRewarded(p0: RewardItem?) {
                    rewardGGListener?.onRewarded(p0)
                }

                override fun onRewardedVideoStarted() {}

                override fun onRewardedVideoAdFailedToLoad(errorCode: Int) {
                    RecordGG.recordGGLoadResult(
                            GG_REWARD,
                            RecordGG.VALUE_REWARD,
                            RecordGG.VALUE_FAIL,
                            errorCode
                    )
                }
            }
        }

        rewardedVideoAd?.loadAd(GG_REWARD, AdRequest.Builder().build())
        RecordGG.recordGGLoad(
                GG_REWARD,
                RecordGG.VALUE_REWARD
        )
    }

    fun showRewardVideoGG(){
        if (rewardedVideoAd?.isLoaded == true) {
            rewardedVideoAd?.show()
            RecordGG.recordGGShowResult(
                    GG_REWARD,
                    RecordGG.VALUE_REWARD,
                    RecordGG.VALUE_SUCCESS
            )
        } else {
            RecordGG.recordGGShowResult(
                    GG_REWARD,
                    RecordGG.VALUE_REWARD,
                    RecordGG.VALUE_FAIL
            )
        }

        RecordGG.recordGGShow(
                GG_REWARD,
                RecordGG.VALUE_REWARD
        )
    }

    fun loadEnterGGAndShow(context: Context){
        enterInterstitialAd = InterstitialAd(context)
        enterInterstitialAd?.adUnitId = GG_ENTER_APP_KEY

        enterInterstitialAd?.adListener = object : AdListener(){
            override fun onAdLoaded() {
                Log.e(tag,"onExitAdLoaded")
                showEnterGG()
                RecordGG.recordGGLoadResult(
                        GG_ENTER_APP_KEY,
                        RecordGG.VALUE_FULL_SCREEN,
                        RecordGG.VALUE_SUCCESS
                )
            }

            override fun onAdFailedToLoad(errorCode: Int) {
                Log.e(tag,"onExitAdLoadedFailed:$errorCode")
                RecordGG.recordGGLoadResult(
                        GG_ENTER_APP_KEY,
                        RecordGG.VALUE_FULL_SCREEN,
                        RecordGG.VALUE_FAIL,
                        errorCode
                )
            }

            override fun onAdOpened() {
                // Code to be executed when the ad is displayed.
            }

            override fun onAdClicked() {
                RecordGG.recordGGClick(
                        GG_ENTER_APP_KEY,
                        RecordGG.VALUE_FULL_SCREEN
                )
            }

            override fun onAdLeftApplication() {
                // Code to be executed when the user has left the app.
            }

            override fun onAdClosed() {
                // Code to be executed when the interstitial ad is closed.
            }

        }
        enterInterstitialAd?.loadAd(AdRequest.Builder().build())
        RecordGG.recordGGLoad(
                GG_ENTER_APP_KEY,
                RecordGG.VALUE_FULL_SCREEN
        )
    }

    private fun showEnterGG(){
        if (enterInterstitialAd?.isLoaded == true) {
            enterInterstitialAd?.show()
            RecordGG.recordGGShowResult(
                    GG_ENTER_APP_KEY,
                    RecordGG.VALUE_FULL_SCREEN,
                    RecordGG.VALUE_SUCCESS
            )
        } else {
            RecordGG.recordGGShowResult(
                    GG_ENTER_APP_KEY,
                    RecordGG.VALUE_FULL_SCREEN,
                    RecordGG.VALUE_FAIL
            )
        }

        RecordGG.recordGGShow(
                GG_ENTER_APP_KEY,
                RecordGG.VALUE_FULL_SCREEN
        )
    }

    fun loadAndShowMainPageAd(context: Context,adView: UnifiedNativeAdView,adContainer: ViewGroup){
        mainPageAdLoader = AdLoader.Builder(context, GG_MAIN_PAGE_NATIVE)
                .forUnifiedNativeAd { ad : UnifiedNativeAd ->
                    populateUnifiedNativeAdView(ad, adView)
                    currentNativeAd = ad
                    adContainer.removeAllViews()
                    adContainer.addView(adView)

                    RecordGG.recordGGShowResult(
                            GG_MAIN_PAGE_NATIVE,
                            RecordGG.VALUE_NATIVE,
                            RecordGG.VALUE_SUCCESS
                    )
                }
                .withAdListener(object : AdListener() {
                    override fun onAdFailedToLoad(errorCode: Int) {
                        Log.e(tag,"onNativeAdLoadedFailed:$errorCode")
                        RecordGG.recordGGLoadResult(
                                GG_MAIN_PAGE_NATIVE,
                                RecordGG.VALUE_NATIVE,
                                RecordGG.VALUE_FAIL,
                                errorCode
                        )
                    }

                    override fun onAdLoaded() {
                        super.onAdLoaded()
                        RecordGG.recordGGLoadResult(
                                GG_MAIN_PAGE_NATIVE,
                                RecordGG.VALUE_NATIVE,
                                RecordGG.VALUE_SUCCESS
                        )
                    }
                })
                .withNativeAdOptions(NativeAdOptions.Builder()
                        // used here to specify individual options settings.
                        .build())
                .build()

        mainPageAdLoader?.loadAd(AdRequest.Builder().build())

        RecordGG.recordGGLoad(
                GG_MAIN_PAGE_NATIVE,
                RecordGG.VALUE_NATIVE
        )
        RecordGG.recordGGShow(
                GG_MAIN_PAGE_NATIVE,
                RecordGG.VALUE_NATIVE
        )
    }

    /**
     * Populates a [UnifiedNativeAdView] object with data from a given
     * [UnifiedNativeAd].
     *
     * @param nativeAd the object containing the ad's assets
     * @param adView the view to be populated
     */
    private fun populateUnifiedNativeAdView(nativeAd: UnifiedNativeAd, adView: UnifiedNativeAdView) {
        // You must call destroy on old ads when you are done with them,
        // otherwise you will have a memory leak.
        currentNativeAd?.destroy()
        currentNativeAd = nativeAd
        // Set the media view.
        adView.mediaView = adView.findViewById<MediaView>(R.id.ad_media)

        // Set other ad assets.
        adView.headlineView = adView.findViewById(R.id.ad_headline)
        adView.bodyView = adView.findViewById(R.id.ad_body)
        adView.callToActionView = adView.findViewById(R.id.ad_call_to_action)
        adView.iconView = adView.findViewById(R.id.ad_app_icon)
        adView.priceView = adView.findViewById(R.id.ad_price)
        adView.starRatingView = adView.findViewById(R.id.ad_stars)
        adView.storeView = adView.findViewById(R.id.ad_store)
        adView.advertiserView = adView.findViewById(R.id.ad_advertiser)

        // The headline and media content are guaranteed to be in every UnifiedNativeAd.
        (adView.headlineView as TextView).text = nativeAd.headline
        adView.mediaView.setMediaContent(nativeAd.mediaContent)

        // These assets aren't guaranteed to be in every UnifiedNativeAd, so it's important to
        // check before trying to display them.
        if (nativeAd.body == null) {
            adView.bodyView.visibility = View.INVISIBLE
        } else {
            adView.bodyView.visibility = View.VISIBLE
            (adView.bodyView as TextView).text = nativeAd.body
        }

        if (nativeAd.callToAction == null) {
            adView.callToActionView.visibility = View.INVISIBLE
        } else {
            adView.callToActionView.visibility = View.VISIBLE
            (adView.callToActionView as Button).text = nativeAd.callToAction
        }

        if (nativeAd.icon == null) {
            adView.iconView.visibility = View.GONE
        } else {
            (adView.iconView as ImageView).setImageDrawable(
                    nativeAd.icon.drawable)
            adView.iconView.visibility = View.VISIBLE
        }

        if (nativeAd.price == null) {
            adView.priceView.visibility = View.INVISIBLE
        } else {
            adView.priceView.visibility = View.VISIBLE
            (adView.priceView as TextView).text = nativeAd.price
        }

        if (nativeAd.store == null) {
            adView.storeView.visibility = View.INVISIBLE
        } else {
            adView.storeView.visibility = View.VISIBLE
            (adView.storeView as TextView).text = nativeAd.store
        }

        if (nativeAd.starRating == null) {
            adView.starRatingView.visibility = View.INVISIBLE
        } else {
            (adView.starRatingView as RatingBar).rating = nativeAd.starRating!!.toFloat()
            adView.starRatingView.visibility = View.VISIBLE
        }

        if (nativeAd.advertiser == null) {
            adView.advertiserView.visibility = View.INVISIBLE
        } else {
            (adView.advertiserView as TextView).text = nativeAd.advertiser
            adView.advertiserView.visibility = View.VISIBLE
        }

        // This method tells the Google Mobile Ads SDK that you have finished populating your
        // native ad view with this native ad.
        adView.setNativeAd(nativeAd)
    }



}