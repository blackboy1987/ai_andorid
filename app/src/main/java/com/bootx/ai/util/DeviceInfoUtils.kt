package com.bootx.ai.util

import android.annotation.SuppressLint
import android.content.Context
import android.net.wifi.WifiManager
import android.os.Build
import android.provider.Settings

object DeviceInfoUtils {

    @SuppressLint("HardwareIds")
    fun getDeviceInfo(context: Context): SystemInfo1 {
        var androidId = Settings.Secure.getString(context.contentResolver, Settings.Secure.ANDROID_ID)

        val wifiManager = context.applicationContext.getSystemService(Context.WIFI_SERVICE) as WifiManager
        val wifiInfo = wifiManager.connectionInfo
        var macAddress = wifiInfo.macAddress ?: "02:00:00:00:00:00"

        var manufacturer = ""
        try {
            manufacturer = Build.MANUFACTURER
        }catch (e: Exception){}
        var model = ""
        try {
            model = Build.MODEL
        }catch (e: Exception){}

        // 获取设备的操作系统版本
        var osVersion = ""
        try {
            osVersion = Build.VERSION.SDK_INT.toString()
        }catch (e: Exception){}



        return SystemInfo1(
            macAddress = macAddress,
            androidId = androidId,
            os = osVersion.toString(),
            model = model,
            manufacturer = manufacturer,
        )
    }

    data class SystemInfo1(
        val macAddress: String,
        val androidId: String,
        val os: String,
        val model: String,
        val manufacturer: String,
    )
}