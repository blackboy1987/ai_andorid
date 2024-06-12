package com.bootx.ai.ui.components.ad

import android.app.Activity
import android.content.Context
import com.bootx.ai.entity.AdConfigEntity
import com.bootx.ai.util.CommonUtils
import com.bootx.ai.util.HttpUtils
import com.bootx.ai.util.SharedPreferencesUtils
import com.google.gson.Gson
import com.youxiao.ssp.ad.bean.SSPAd
import com.youxiao.ssp.ad.core.AdClient
import com.youxiao.ssp.ad.listener.RewardVideoAdAdapter
import java.util.Date

/**
*激励视频广告：13903
*/
fun requestRewardAd(context: Context, callback:(type:String)->Unit) {

    val adType = "激励视频广告"
    val gson = Gson()
    val adConfig = gson.fromJson(SharedPreferencesUtils(context).get("adConfig"), AdConfigEntity::class.java)
    val adId = adConfig.rewardVideoAdId
    val adData = CommonUtils.getSystemParams(context)
    adData["adId"] = adId
    adData["adType"] = adType
    adData["mediaId"] = adConfig.mediaId
    val adClient = AdClient(context as Activity)
    adClient.requestRewardAd(adId, object : RewardVideoAdAdapter() {
        override fun loadRewardAdSuc(sspAd: SSPAd?) {
            super.loadRewardAdSuc(sspAd)
            callback("loadRewardAdSuc")
            adData["status"] = "loadRewardAdSuc"
            HttpUtils.adRequest(adData)
            SharedPreferencesUtils(context).set("adSuccess_requestRewardAd","${Date().time}")
        }

        override fun loadRewardAdFail(p0: String?) {
            super.loadRewardAdFail(p0)
            callback("loadRewardAdFail")
            SharedPreferencesUtils(context).set("adError_requestRewardAd","${Date().time}")
        }

        override fun onReward(p0: SSPAd?, p1: Boolean, p2: MutableMap<String, Any>?) {
            super.onReward(p0, p1, p2)
            callback("onReward")
            adData["status"] = "onReward"
            HttpUtils.adRequest(adData)
        }

        override fun rewardVideoClosed() {
            super.rewardVideoClosed()
            callback("rewardVideoClosed")
        }


    })
}