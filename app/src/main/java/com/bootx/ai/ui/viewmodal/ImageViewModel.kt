package com.bootx.ai.ui.viewmodal

import android.content.Context
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.bootx.ai.entity.ImageAppEntity
import com.bootx.ai.service.ImageService
import com.bootx.ai.util.SharedPreferencesUtils
import com.google.gson.Gson

class ImageViewModel : ViewModel() {
    private val imageService = ImageService.instance()
    var imageAppEntity by mutableStateOf(ImageAppEntity())
    var loading by mutableStateOf(false)
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
}