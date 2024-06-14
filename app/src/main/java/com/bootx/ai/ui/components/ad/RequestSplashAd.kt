package com.bootx.ai.ui.components.ad

import android.app.Activity
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import com.bootx.ai.R
import com.bootx.ai.entity.AdConfigEntity
import com.bootx.ai.util.CommonUtils
import com.bootx.ai.util.SharedPreferencesUtils
import com.google.gson.Gson
import com.youxiao.ssp.ad.bean.NextAdInfo
import com.youxiao.ssp.ad.bean.SSPAd
import com.youxiao.ssp.ad.core.AdClient
import com.youxiao.ssp.ad.listener.AdLoadAdapter
import java.util.Date

/**
 * 开屏广告: 3561
 */
@Composable
fun RequestSplashAd(context: Context,callback:(status: String)->Unit) {
    val adType = "开屏广告"
    val adData = CommonUtils.getSystemParams(context)
    val adId: String
    try {
        val gson = Gson()
        val adConfig = gson.fromJson(SharedPreferencesUtils(context).get("adConfig"), AdConfigEntity::class.java)
        adId=adConfig.videoFeedAdId
        adData["adId"] = adId
        adData["adType"] = adType
        adData["mediaId"] = adConfig.mediaId
    }catch (e:Exception){
        return
    }
    val adClient = AdClient(context as Activity)
    AndroidView(
        modifier = Modifier.fillMaxSize(),
        factory = {
        val view = LayoutInflater.from(context).inflate(R.layout.activity_splash, null)
        val mAdLayout = view.findViewById<FrameLayout>(R.id.ad_layout)
        mAdLayout.visibility = View.VISIBLE
        adClient.requestSplashAd(mAdLayout, adId, object : AdLoadAdapter() {
            override fun onError(var1: Int, error: String) {
                super.onError(var1, error)
                callback("onError")
                Log.e("loadAd $adType $adId", error)
                SharedPreferencesUtils(context).set("adError","${Date().time}")
            }

            /**
             * 加载成功
             */
            override fun onAdLoad(p0: SSPAd?) {
                callback("onAdLoad")
                super.onAdLoad(p0)
            }

            /**
             * 广告显示
             */
            override fun onAdShow(p0: SSPAd?) {
                callback("onAdShow")
                super.onAdShow(p0)
            }

            /**
             * 广告隐藏
             */
            override fun onAdDismiss(p0: SSPAd?) {
                super.onAdDismiss(p0)
                callback("onAdDismiss")
            }

            override fun onStatus(p0: Int, p1: Int, p2: Int, p3: String?) {
                Log.e("loadAd $adType $adId", "onStatus: $p3")
                super.onStatus(p0, p1, p2, p3)
            }

            override fun onNext(p0: NextAdInfo?) {
                super.onNext(p0)
            }

            override fun onAdClick(p0: SSPAd?) {
                super.onAdClick(p0)
                callback("onAdClick")
            }

            override fun onStartDownload(p0: String?) {
                super.onStartDownload(p0)
            }

            override fun onDownloadCompleted(p0: String?) {
                super.onDownloadCompleted(p0)
            }

            override fun onStartInstall(p0: String?) {
                super.onStartInstall(p0)
            }

            override fun onInstallCompleted(p0: String?) {
                super.onInstallCompleted(p0)
            }


        })
        view
    })
}