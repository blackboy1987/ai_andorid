package com.bootx.ai.ui.viewmodal

import android.content.Context
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.bootx.ai.entity.AppEntity
import com.bootx.ai.service.AppService
import com.bootx.ai.util.SharedPreferencesUtils

class WriteModel : ViewModel() {
    private val appService = AppService.instance()
    var appEntity by mutableStateOf(AppEntity())

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
            filter[0].value = value
        }
        appEntity = newAppEntity
    }
}