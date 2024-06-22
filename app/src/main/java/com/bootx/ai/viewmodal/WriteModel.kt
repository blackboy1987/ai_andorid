package com.bootx.ai.viewmodal

import android.content.Context
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.bootx.ai.config.Config
import com.bootx.ai.entity.AppEntity
import com.bootx.ai.service.AppService
import com.bootx.ai.util.SharedPreferencesUtils
import com.google.gson.Gson
import com.google.gson.JsonArray
import com.squareup.moshi.Json
import java.util.UUID

class WriteModel : ViewModel() {
    private val appService = AppService.instance()
    var appEntity by mutableStateOf(AppEntity())
    var loading by mutableStateOf(false)
    val gson = Gson()

    suspend fun config(context: Context,id:Int) {

        val res = appService.config(
            SharedPreferencesUtils(context).getToken(),
            SharedPreferencesUtils(context).getDeviceId(),
            id
        )
        if (res.code == 0) {
            appEntity = res.data
        }
    }

    suspend fun changeValue(label: String,value: String) {
        val newAppEntity = appEntity
        val filter =
            newAppEntity.formDataList.filter { item -> item.label == label }
        if(filter.isNotEmpty()){
            if(filter[0].formType=="select"){
                filter[0].radioIndex = value.toInt()
            }else{
                filter[0].value = value
            }
        }
        appEntity = newAppEntity
    }

    suspend fun submit(context: Context) {
        loading = true
        val params = mutableMapOf(
            "categoryAppId" to appEntity.id,
            "categoryAppName" to appEntity.title,
        )
        // 解析出来参数
        for (formData in appEntity.formDataList) {
            if(formData.formType=="select"){
                params[formData.label] = formData.options[formData.radioIndex]
            }else{
                params[formData.label] = formData.value
            }
        }
        appService.write(
            SharedPreferencesUtils(context).getToken(),
            SharedPreferencesUtils(context).getDeviceId(),
            appEntity.id,
            gson.toJson(params)
        )

    }
}