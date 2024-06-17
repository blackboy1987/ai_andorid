package com.bootx.ai.ui.viewmodal

import android.content.Context
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.bootx.ai.config.Config
import com.bootx.ai.entity.AppEntity
import com.bootx.ai.entity.CategoryAppTaskDetail
import com.bootx.ai.entity.CategoryAppTaskEntity
import com.bootx.ai.entity.CategoryEntity
import com.bootx.ai.service.AppService
import com.bootx.ai.service.WriteLogService
import com.bootx.ai.util.SharedPreferencesUtils
import com.google.gson.Gson
import com.google.gson.JsonArray
import com.squareup.moshi.Json
import java.util.UUID

class WriteLogModel : ViewModel() {
    private val writeLogService = WriteLogService.instance()
    var loading by mutableStateOf(false)
    var categoryAppTaskList by mutableStateOf(listOf<CategoryAppTaskEntity>())
    var categoryAppTaskDetail by mutableStateOf(CategoryAppTaskDetail())

    suspend fun list(context: Context,id:Int) {

        val res = writeLogService.list(
            SharedPreferencesUtils(context).getToken(),
            SharedPreferencesUtils(context).getDeviceId(),
            id
        )
        if (res.code == 0) {
            categoryAppTaskList = res.data
        }
    }
    suspend fun detail(context: Context,taskId:String) {

        val res = writeLogService.detail(
            SharedPreferencesUtils(context).getToken(),
            SharedPreferencesUtils(context).getDeviceId(),
            taskId
        )
        if (res.code == 0) {
            categoryAppTaskDetail = res.data
        }
    }

}