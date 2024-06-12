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
import com.bootx.ai.entity.AdConfigEntity
import com.bootx.ai.util.CommonUtils
import com.bootx.ai.util.SharedPreferencesUtils
import com.bootx.ai.R
import com.google.gson.Gson
import com.youxiao.ssp.ad.bean.NextAdInfo
import com.youxiao.ssp.ad.bean.SSPAd
import com.youxiao.ssp.ad.core.AdClient
import com.youxiao.ssp.ad.listener.AdLoadAdapter
import java.util.Date

/**
 *信息流广告：13905
 */
@Composable
fun RequestExpressAd(context: Context,callback:(status: String)->Unit) {
    val adType = "信息流广告"
    val gson = Gson()
    val adConfig = gson.fromJson(SharedPreferencesUtils(context).get("adConfig"), AdConfigEntity::class.java)
    val adId = adConfig.feedAdId
    val adData = CommonUtils.getSystemParams(context)
    adData["adId"] = adId
    adData["adType"] = adType
    adData["mediaId"] = adConfig.mediaId
    val adClient = AdClient(context as Activity)
    if(!CommonUtils.getAdErrorStatus(context,"RequestExpressAd")){
        callback("onError")
        return
    }
    AndroidView(
        modifier = Modifier.fillMaxWidth(),
        factory = {
            val view = LayoutInflater.from(context).inflate(R.layout.activity_template, null)
            val findViewById = view.findViewById<FrameLayout>(R.id.ad_layout)
            adClient.requestExpressAd(
                adId, object : AdLoadAdapter(){
                    override fun onStatus(p0: Int, p1: Int, p2: Int, p3: String?) {
                        Log.e("loadAd $adType $adId", "onStatus: $p3")
                        super.onStatus(p0, p1, p2, p3)
                    }

                    override fun onNext(p0: NextAdInfo?) {
                        super.onNext(p0)
                    }

                    override fun onAdLoad(ad: SSPAd) {
                        super.onAdLoad(ad)
                        findViewById.removeAllViews()
                        findViewById.addView(ad.view)
                        callback("onAdLoad")
                    }

                    override fun onAdClick(p0: SSPAd?) {
                        super.onAdClick(p0)
                        callback("onAdClick")
                    }

                    override fun onAdShow(p0: SSPAd?) {
                        super.onAdShow(p0)
                        callback("onAdShow")
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
                        Log.e("loadAd $adType $adId", "onError: $p1")
                        super.onError(p0, p1)
                        callback("onError")
                        SharedPreferencesUtils(context).set("adError_RequestExpressAd","${Date().time}")
                    }
                })
            view
        })
}
