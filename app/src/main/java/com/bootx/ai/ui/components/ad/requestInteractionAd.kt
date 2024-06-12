package com.bootx.ai.ui.components.ad

import android.app.Activity
import android.content.Context
import android.util.Log
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
 * 插屏广告：13902
 */
fun requestInteractionAd(context: Context, callback:(status:String)->Unit) {

    val adType = "插屏广告"
    val gson = Gson()
    val adConfig = gson.fromJson(SharedPreferencesUtils(context).get("adConfig"), AdConfigEntity::class.java)
    val adId = adConfig.interAdId
    val adData = CommonUtils.getSystemParams(context)
    adData["adId"] = adId
    adData["adType"] = adType
    adData["mediaId"] = adConfig.mediaId
    val adClient = AdClient(context as Activity)
    if(!CommonUtils.getAdErrorStatus(context,"requestInteractionAd")){
        callback("onError")
        return
    }
    adClient.requestInteractionAd(adId, object : AdLoadAdapter() {
        override fun onAdShow(p0: SSPAd?) {
            callback("onAdShow")
            super.onAdShow(p0)
        }

        override fun onError(p0: Int, p1: String?) {
            super.onError(p0, p1)
            callback("onError")
            Log.e("loadAd $adType $adId", "onError: $p1")
            SharedPreferencesUtils(context).set("adError_requestInteractionAd","${Date().time}")
        }

        override fun onAdClick(p0: SSPAd?) {
            callback("onAdClick")
            super.onAdClick(p0)
        }

        override fun onAdDismiss(p0: SSPAd?) {
            callback("onAdDismiss")
            super.onAdDismiss(p0)
        }

        override fun onStatus(p0: Int, p1: Int, p2: Int, p3: String?) {
            super.onStatus(p0, p1, p2, p3)
            Log.e("loadAd $adType $adId", "onStatus: $p0,$p1,$p2,$p3")
        }

        override fun onNext(p0: NextAdInfo?) {
            super.onNext(p0)
        }

        override fun onAdLoad(p0: SSPAd?) {
            super.onAdLoad(p0)
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
}
