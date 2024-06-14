package com.bootx.ai.ui.components.ad

import android.app.Activity
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.widget.FrameLayout
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import com.bootx.ai.R
import com.bootx.ai.entity.AdConfigEntity
import com.bootx.ai.util.CommonUtils
import com.bootx.ai.util.HttpUtils
import com.bootx.ai.util.SharedPreferencesUtils
import com.google.gson.Gson
import com.youxiao.ssp.ad.bean.NextAdInfo
import com.youxiao.ssp.ad.bean.SSPAd
import com.youxiao.ssp.ad.core.AdClient
import com.youxiao.ssp.ad.listener.AdLoadAdapter
import java.util.Date

/**
 *横幅广告
 */
@Composable
fun RequestBannerAd(context: Context,callback:(status: String)->Unit) {
    val adType = "横幅广告"
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
    if(!CommonUtils.getAdErrorStatus(context,"RequestBannerAd")){
        return
    }
    AndroidView(
        modifier = Modifier.fillMaxWidth(),factory = {
        val view = LayoutInflater.from(it).inflate(R.layout.activity_banner, null)
        val findViewById = view.findViewById<FrameLayout>(R.id.ad_layout)
        adClient.requestBannerAd(findViewById, adId, object : AdLoadAdapter(){
            override fun onStatus(p0: Int, p1: Int, p2: Int, p3: String?) {
                Log.e("loadAd $adType $adId", "onStatus: $p3")
                super.onStatus(p0, p1, p2, p3)
            }

            override fun onNext(p0: NextAdInfo?) {
                super.onNext(p0)
            }

            override fun onAdLoad(p0: SSPAd?) {
                super.onAdLoad(p0)
                callback("onAdLoad")
            }

            override fun onAdClick(p0: SSPAd?) {
                super.onAdClick(p0)
                callback("onAdClick")
                adData["status"] = "onAdClick"
                HttpUtils.adRequest(adData)
            }

            override fun onAdShow(p0: SSPAd?) {
                super.onAdShow(p0)
                callback("onAdShow")
                adData["status"] = "onAdShow"
                HttpUtils.adRequest(adData)
            }

            override fun onAdDismiss(p0: SSPAd?) {
                super.onAdDismiss(p0)
                callback("onAdDismiss")
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

            override fun onError(p0: Int, p1: String?) {
                Log.e("onError $adType $adId", "onError: $p1")
                super.onError(p0, p1)
                adData["status"] = "onError"
                HttpUtils.adRequest(adData)
                CommonUtils.toast(context,"onError $adType $adId $p0,$p1")
                // 写入缓存。只要有一次广告加载失败。那就最近半个小时不显示广告
                SharedPreferencesUtils(context).set("adError_RequestBannerAd","${Date().time}")
            }
        })
        view
    })
}
