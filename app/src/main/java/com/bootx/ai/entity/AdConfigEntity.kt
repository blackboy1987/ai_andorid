package com.bootx.ai.entity

data class AdConfigEntity(
    /**
     * 媒体
     */
    val mediaId: String="0",
    /**
     * 开屏广告
     */
    val splashAdId: String="0",
    /**
     * 横幅广告
     */
    val bannerAdId: String="0",
    /**
     * 模板广告
     */
    val templateAdId: String="0",
    /**
     * 插屏广告
     */
    val interAdId: String="0",
    /**
     * 信息流广告
     */
    val feedAdId: String="0",
    /**
     * 模板视频信息流广告
     */
    val videoFeedAdId: String="0",
    /**
     * 全屏视频广告
     */
    val fullScreenVideoAdId: String="0",
    /**
     * 激励视频广告
     */
    val rewardVideoAdId: String="0",
)
data class AdConfigEntityResponse(val data: AdConfigEntity) : BaseResponse()