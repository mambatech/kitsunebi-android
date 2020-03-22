package com.exnor.vray.common

import android.content.Context
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import com.exnor.vray.BuildConfig
import com.exnor.vray.R
import com.google.android.gms.ads.*
import com.google.android.gms.ads.formats.MediaView
import com.google.android.gms.ads.formats.NativeAdOptions
import com.google.android.gms.ads.formats.UnifiedNativeAd
import com.google.android.gms.ads.formats.UnifiedNativeAdView
import java.util.*


/**
created by edison 2020/3/22
 */
object GGHelper {

    val tag = GGHelper::class.java.simpleName
    val APP_ADMOB_KEY = "ca-app-pub-7094078041880308~1863765640"
    val GG_EXIT_APP_KEY = if (BuildConfig.DEBUG) {
        "ca-app-pub-3940256099942544/1033173712"
    } else {
        "ca-app-pub-8917831695584667/6069290889"
    }

    val GG_MAIN_PAGE_NATIVE = if (BuildConfig.DEBUG){
        "ca-app-pub-3940256099942544/2247696110"
    }else{
        "ca-app-pub-8917831695584667/1746902493"
    }

    private var exitInterstitialAd: InterstitialAd? = null
    private var mainPageAdLoader: AdLoader? = null
    private var currentNativeAd: UnifiedNativeAd? = null

    fun loadExitGG(context: Context){
        exitInterstitialAd = InterstitialAd(context)
        exitInterstitialAd?.adUnitId = GG_EXIT_APP_KEY

        exitInterstitialAd?.adListener = object : AdListener(){
            override fun onAdLoaded() {
                Log.e(tag,"onExitAdLoaded")
            }

            override fun onAdFailedToLoad(errorCode: Int) {
                Log.e(tag,"onExitAdLoadedFailed:$errorCode")
            }

            override fun onAdOpened() {
                // Code to be executed when the ad is displayed.
            }

            override fun onAdClicked() {
                // Code to be executed when the user clicks on an ad.
            }

            override fun onAdLeftApplication() {
                // Code to be executed when the user has left the app.
            }

            override fun onAdClosed() {
                // Code to be executed when the interstitial ad is closed.
            }

        }
        exitInterstitialAd?.loadAd(AdRequest.Builder().build())
    }

    fun showExitGG(){
        if (exitInterstitialAd?.isLoaded == true){
            exitInterstitialAd?.show()
        }
    }

    fun loadAndShowMainPageAd(context: Context,adView: UnifiedNativeAdView,adContainer: ViewGroup){
        mainPageAdLoader = AdLoader.Builder(context, GG_MAIN_PAGE_NATIVE)
                .forUnifiedNativeAd { ad : UnifiedNativeAd ->
                    populateUnifiedNativeAdView(ad,adView)
                    currentNativeAd = ad
                    adContainer.removeAllViews()
                    adContainer.addView(adView)
                }
                .withAdListener(object : AdListener() {
                    override fun onAdFailedToLoad(errorCode: Int) {

                    }

                    override fun onAdLoaded() {
                        super.onAdLoaded()

                    }
                })
                .withNativeAdOptions(NativeAdOptions.Builder()
                        // used here to specify individual options settings.
                        .build())
                .build()

        mainPageAdLoader?.loadAd(AdRequest.Builder().build())
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