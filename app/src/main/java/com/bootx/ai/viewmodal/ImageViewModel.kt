package com.bootx.ai.viewmodal

import android.content.Context
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.bootx.ai.entity.CommonResponse
import com.bootx.ai.entity.ImageAppEntity
import com.bootx.ai.service.ImageService
import com.bootx.ai.util.CommonUtils
import com.bootx.ai.util.SharedPreferencesUtils
import com.google.gson.Gson

class ImageViewModel : ViewModel() {
    private val imageService = ImageService.instance()
    var imageAppEntity by mutableStateOf(ImageAppEntity())
    var loading by mutableStateOf(false)
    var taskId by mutableStateOf("")
    val gson = Gson()

    suspend fun config(context: Context) {
        val res = imageService.config(
            SharedPreferencesUtils(context).getToken(),
            SharedPreferencesUtils(context).getDeviceId(),
        )
        if (res.code == 0) {
            imageAppEntity = res.data
        }
    }


    suspend fun create(context: Context,prompt: String,style: String,size: String):CommonResponse {
        loading = true
        val res = imageService.create(
            SharedPreferencesUtils(context).getToken(),
            SharedPreferencesUtils(context).getDeviceId(),
            prompt,
            style,
            size,
        )
        if (res.code == 0) {
            taskId = res.data
            CommonUtils.toast(context,"任务已提交")
        }else{
            CommonUtils.toast(context,res.msg)
        }
        loading = false
        return res
    }

    suspend fun task(context: Context,taskId: String) {
        loading = true
        val res = imageService.task(
            SharedPreferencesUtils(context).getToken(),
            SharedPreferencesUtils(context).getDeviceId(),
            taskId,
        )
        if (res.code == 0) {
            CommonUtils.toast(context,"任务已提交")
        }else{
            CommonUtils.toast(context,res.msg)
        }
        loading = false
    }
}