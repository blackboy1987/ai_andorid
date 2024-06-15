package com.bootx.ai.util

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Build
import android.provider.Settings
import android.telephony.TelephonyManager
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.bootx.ai.config.Config
import com.bootx.ai.entity.Env
import com.bootx.ai.ui.components.ad.requestRewardAd
import java.util.Date
import java.util.UUID


object CommonUtils {

    fun toast(context: Context, msg: String) {
        Toast.makeText(context, msg, Toast.LENGTH_LONG).show()
    }

    fun getSystemParams(context: Context): MutableMap<String, String> {
        val deviceInfo = DeviceInfoUtils.getDeviceInfo(context)
        SharedPreferencesUtils(context).set("deviceId", deviceInfo.androidId)
        return mutableMapOf(
            "deviceId" to deviceInfo.androidId,
            "model" to deviceInfo.model,
            "os" to deviceInfo.os,
            "manufacturer" to deviceInfo.manufacturer,
            "token" to SharedPreferencesUtils(context).get("token"),
            "appId" to Config.appId,
            "deviceId" to SharedPreferencesUtils(context).get("deviceId"),
            "requestId" to UUID.randomUUID().toString(),
        )
    }


    fun getEnv(context: Context): Env {
        val checkSimCard = checkSimCard(context)
        val isUsbDebuggingEnabled = isUsbDebuggingEnabled(context)
        val deviceInfo = DeviceInfoUtils.getDeviceInfo(context)
        return Env(
            isUsbDebuggingEnabled,
            simCardStatus = checkSimCard,
            deviceId = deviceInfo.androidId,
            model = deviceInfo.model,
            os = deviceInfo.os,
            manufacturer = deviceInfo.manufacturer,
            token = SharedPreferencesUtils(context).get("token"),
            appId = Config.appId,
        )



    }

    // 检查SIM卡状态
    fun checkSimCard(context:Context):Int {
        val telephonyManager = context.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager

        // 检查权限
        val permissionGranted = ContextCompat.checkSelfPermission(
            context,
            android.Manifest.permission.READ_PHONE_STATE
        ) == android.content.pm.PackageManager.PERMISSION_GRANTED
        /**
         * 0: SIM 卡状态未知。这通常表示设备尚未检测到 SIM 卡的状态。
         * 1：无 SIM 卡。设备上没有插入 SIM 卡。
         * 2：需要 PIN 码。SIM 卡已检测到，但需要用户输入 PIN 码进行解锁。
         * 3：需要 PUK 码。SIM 卡已被锁定，需要用户输入 PUK 码进行解锁。
         * 4：网络锁定。SIM 卡已被锁定，可能需要特定的网络解锁码。
         * 5：SIM 卡已准备好并可正常使用。
         * 6：SIM 卡未准备好。设备正在进行一些初始化工作，尚未完成。
         * 7：SIM 卡被永久禁用。可能是由于尝试解锁 SIM 卡失败次数过多，导致 SIM 卡被永久禁用。
         * 8：SIM 卡 I/O 错误。设备与 SIM 卡的通信出现错误。
         * 9：SIM 卡受限。设备检测到 SIM 卡的使用受限。
         */
        val simState = if (permissionGranted) {
            telephonyManager.simState
        } else {
            // 请求权限或提供替代处理
            TelephonyManager.SIM_STATE_UNKNOWN
        }
        return simState
    }

    /**
     * 判断手机是否开启了USB调试
     */
    fun isUsbDebuggingEnabled(context: Context): Boolean {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            Settings.Global.getInt(
                context.contentResolver,
                Settings.Global.ADB_ENABLED,
                0
            ) > 0
        } else {
            false
        }
    }

    fun navigateToDeveloperOptions(context:Context) {
        try {
            val intent = Intent(Settings.ACTION_APPLICATION_DEVELOPMENT_SETTINGS)
            context.startActivity(intent)
            restartApp()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    /**
     * 检查广告环境
     * true: 允许看广告
     * false: 不显示广告
     */
    fun checkAdEnv(context:Context,type: Int):Boolean{
        if(type==0){
            return true
        }
        if(type==1){
            return !isUsbDebuggingEnabled(context)
        }
        if(type==2){
            return checkSimCard(context) ==5
        }
        if(type==3){
            return checkSimCard(context) ==5 && !isUsbDebuggingEnabled(context)
        }
        return false
    }

    fun getAdRewardIcon(context:Context,baseIcon:Int,usbRate: Double,simRate:Double):Int{
        var rate = 1.0
        if(isUsbDebuggingEnabled(context)){
            rate *= usbRate
        }
        if(checkSimCard(context) !=5){
            rate *= simRate
        }
        return Math.round(baseIcon*rate).toInt()
    }

    private fun restartApp() {
        Runtime.getRuntime().exit(0)
    }


    fun getRewardVideoAd (context: Context,onClose:(status:Int)->Unit){
        var isEnd = false
        if(getAdSuccessStatus(context,"requestRewardAd") >0){
            toast(context,"广告观看过于频繁，请于1分钟之后再试")
            onClose(100)
            return
        }
        if(getAdErrorStatus(context,"requestRewardAd")){
            requestRewardAd(context) { status ->
                if(status=="loadRewardAdSuc" || status=="loadRewardAdFail"){
                    onClose(100)
                }
                if (status == "onReward") {
                    isEnd = true
                } else if (status == "loadRewardAdFail") {
                    toast(context, "广告加载失败")
                } else if (status == "rewardVideoClosed" && isEnd) {
                    //奖励获取到了
                    onClose(1)
                }
            }
        }else{
            toast(context,"广告加载失败，请5分钟之后再试")
            onClose(100)
        }

    }

    fun getAdErrorStatus (context: Context,adType: String):Boolean{
        val get = SharedPreferencesUtils(context).get("adError_$adType")
        if(get.isEmpty()){
            return true
        }
        val now = Date().time
        return now-1000*60*5>get.toLong()
    }

    fun getAdSuccessStatus (context: Context,adType: String):Long{
        val get = SharedPreferencesUtils(context).get("adSuccess_$adType")
        if(get.isEmpty()){
            return -1L
        }
        val now = Date().time
        val interval = (now-get.toLong())/1000
        return 60*1-interval
    }

    /**
     * 校验手机号
     */
    fun validatePhone(phoneNumber: String): Boolean {
        val regex = Regex("^1[3-9]\\d{9}$")
        return regex.matches(phoneNumber)
    }

    /**
     * 清除缓存
     */
    fun clearCache(context: Context) {
        try {
            val cacheDir = context.cacheDir
            val cacheSizeInBytes = cacheDir.walk().map { it.length() }.sum()
            cacheDir.deleteRecursively()
        } catch (e: Exception) {
            CommonUtils.toast(context, "Failed to clear cache: ${e.message}")
        }
    }

    fun hideKeyboard(context: Context) {
        val inputMethodManager =
            context.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        val currentFocus = (context as? Activity)?.currentFocus
        currentFocus?.let {
            inputMethodManager.hideSoftInputFromWindow(it.windowToken, 0)
        }
    }
}