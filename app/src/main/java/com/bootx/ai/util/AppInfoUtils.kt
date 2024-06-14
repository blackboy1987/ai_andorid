package com.bootx.ai.util

import android.content.Context
import com.bootx.ai.entity.AppInfo


object AppInfoUtils {

    /**
     * 获取当前app的信息
     */
    fun getAppInfo(context: Context):AppInfo{
        val appInfo = context.packageManager.getApplicationInfo(context.packageName, 0)
        val appName = context.packageManager.getApplicationLabel(appInfo).toString()
        val packageInfo = context.packageManager.getPackageInfo(context.packageName, 0)
        val versionName = packageInfo.versionName
        val versionCode =
            packageInfo.longVersionCode
        val icon = context.packageManager.getApplicationIcon(context.packageName)
        //val appSize = appInfo.sourceDir?.let { context.packageManager.getPackageSize(it) } ?: "N/A"
        return AppInfo(
            versionName,
            versionCode,
            appName,
            icon=icon,
        )
    }
}