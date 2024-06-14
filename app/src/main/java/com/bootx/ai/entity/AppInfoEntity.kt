package com.bootx.ai.entity

import android.graphics.drawable.Drawable

data class AppInfo(
    /**
     * 应用名称
     */
    val appName: String="",
    /**
     * 版本号
     */
    val versionCode: Long=0L,
    /**
     * 版本名
     */
    val versionName: String="",
    /**
     * 包名
     */
    val packageName: String="",
    /**
     * 第一次安装时间
     */
    val firstInstallTime: String="",
    /**
     * 最后更新时间
     */
    val lastUpdateTime: String="",
    /**
     * 目标 SDK 版本
     */
    val targetSdkVersion: String="",
    /**
     * 最低 SDK 版本
     */
    val minSdkVersion: String="",
    /**
     * 应用请求的权限列表
     */
    val permissions: String="",
    /**
     * 应用的安装目录
     */
    val sourceDir: String="",
    /**
     * 公共资源目录
     */
    val publicSourceDir: String="",
    /**
     * 本地库目录
     */
    val nativeLibraryDir: String="",
    /**
     * 安装位置
     */
    val installLocation: String="",
    /**
     * 应用名称
     */
    val icon: Drawable?,
    /**
     * 签名信息
     */
    val dataDir: String="",
    /**
     * 签名信息
     */
    val signatures: String="",
    /**
     * 应用大小
     */
    val appSize: String="",
    /**
     * 应用状态（是否正在运行）
     */
    val isRunning: String="",
    /**
     * 获取应用的用户ID
     */
    val uid: String="",
    /**
     * 安装包的SHA-256签名
     */
    val sha256: String="",
    /**
     * 缓存目录大小
     */
    val cacheDir: String="",

    )