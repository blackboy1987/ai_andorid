package com.bootx.ai.ui.viewmodal

import android.content.Context
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.bootx.ai.entity.CommonResponse
import com.bootx.ai.service.LoginService
import com.bootx.ai.util.SharedPreferencesUtils
import java.util.Date

class LoginModel : ViewModel() {
    private val loginService = LoginService.instance()
    var loading by mutableStateOf(false)

    suspend fun sendCode(context: Context,mobile:String) {
        loading = true
        val res = loginService.sendCode(
            SharedPreferencesUtils(context).getToken(),
            SharedPreferencesUtils(context).getDeviceId(),
            mobile
        )
        // 写入发送时间。用于后期的倒计时
        SharedPreferencesUtils(context).setCodeCountDown("${Date().time+1000*60*2}")
        // 写入手机号缓存
        SharedPreferencesUtils(context).set("mobile",mobile)
        loading = false
    }

    suspend fun login(context: Context,code:String):CommonResponse {
        loading = true
        val res = loginService.login(
            SharedPreferencesUtils(context).getToken(),
            SharedPreferencesUtils(context).getDeviceId(),
            SharedPreferencesUtils(context).get("mobile"),
            code
        )
        if(res.code==0){
            // 登录成功
            SharedPreferencesUtils(context).setToken(res.data)
        }
        loading = false
        return res
    }

}