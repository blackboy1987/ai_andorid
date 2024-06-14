package com.bootx.ai

import SplashScreen
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.core.view.WindowCompat
import androidx.lifecycle.lifecycleScope
import com.bootx.ai.config.Config
import com.bootx.ai.entity.AdConfigEntity
import com.bootx.ai.repository.DataBase
import com.bootx.ai.repository.entity.ConfigEntity
import com.bootx.ai.ui.components.NavHostApp
import com.bootx.ai.ui.theme.AppTheme
import com.bootx.ai.util.CommonUtils
import com.bootx.ai.util.DeviceInfoUtils
import com.bootx.ai.util.HttpUtils
import com.bootx.ai.util.IHttpCallback
import com.bootx.ai.util.SharedPreferencesUtils
import com.google.gson.Gson
import com.umeng.commonsdk.UMConfigure
import com.youxiao.ssp.core.SSPSdk
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.Q)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        WindowCompat.setDecorFitsSystemWindows(window, false)
        /**
         * 该方法执行的时间比较长
         */
        init {
            setContent{
                var adStatus by remember {
                    mutableIntStateOf(-1)
                }
                if(SharedPreferencesUtils(this@MainActivity).get("loadAdStatus")=="0"){
                    SplashScreen(){
                        adStatus = it
                    }
                }

                if (adStatus == -1 || adStatus == 2) {
                    AppTheme {
                        NavHostApp()
                    }
                }
            }
        }
    }

    private fun init(callback: ()->Unit) {
        val deviceInfo = DeviceInfoUtils.getDeviceInfo(this@MainActivity)
        UMConfigure.init(this, "664eb5c2cac2a664de3a2b22", "Umeng", UMConfigure.DEVICE_TYPE_PHONE, "")
        //获取唯一id
        SharedPreferencesUtils(this@MainActivity).set("deviceId",deviceInfo.androidId)
        setting()
        login()
        lifecycleScope.launch {
            loadAd()
            callback()
        }
        HttpUtils.getIp(object : IHttpCallback {
            override fun onSuccess(data: Any?) {
                try {
                    val gson = Gson()
                    val adConfig = gson.fromJson(data.toString(), Map::class.java)
                    Config.ip = adConfig["ip"].toString()
                }catch (_:Exception){}
            }

            override fun onFailed(error: Any?) {
            }
        })
    }

    /**
     *
     * 登录
     */
    private fun login() {
        HttpUtils.get(
            CommonUtils.getSystemParams(this@MainActivity),
            Config.baseUrl + "/api/login",
            object : IHttpCallback {
                override fun onSuccess(data: Any?) {

                }

                override fun onFailed(error: Any?) {
                }
            })
    }

    /**
     * 网站的配置。
     */
    private fun setting() {
        HttpUtils.get(
            CommonUtils.getSystemParams(this@MainActivity),
            Config.baseUrl + "/api/zc/setting",
            object : IHttpCallback {
                override fun onSuccess(data: Any?) {
                    val configDao = DataBase.getDb(this@MainActivity)?.getConfigDao()
                    val toJson = data.toString()
                    val gson = Gson()
                    val adConfig = gson.fromJson(toJson, ConfigEntity::class.java)
                    val byId = configDao?.getById(1)
                    /*if(byId==null){
                        adConfig.id = 1
                        configDao?.insert(adConfig)
                    }else{
                        adConfig.id = 1
                        configDao?.update(adConfig)
                    }*/


                }

                override fun onFailed(error: Any?) {
                }
            })
    }

    /**
     * 加载广告配置
     */
    private fun loadAd() {
        val result = runBlocking {
            HttpUtils.loadAd(
                CommonUtils.getSystemParams(this@MainActivity),
                Config.baseUrl + "/api/adConfig")
        }
        if (result != null) {
            if(result.isNotBlank()){
                try {
                    SharedPreferencesUtils(this@MainActivity).set("adConfig",result)
                    val gson = Gson()
                    val adConfig = gson.fromJson(result, AdConfigEntity::class.java)
                    SSPSdk.init(this@MainActivity, adConfig.mediaId, true)
                    SharedPreferencesUtils(this@MainActivity).set("loadAdStatus","0")
                }catch (e:Exception){
                    SharedPreferencesUtils(this@MainActivity).set("loadAdStatus","-1")
                }
            }else{
                SharedPreferencesUtils(this@MainActivity).set("loadAdStatus","-1")
            }
        }else{
            SharedPreferencesUtils(this@MainActivity).set("loadAdStatus","-1")
        }
    }
}