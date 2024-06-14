package com.bootx.ai.ui.viewmodal

import android.content.Context
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.bootx.ai.entity.CategoryEntity
import com.bootx.ai.service.CategoryService
import com.bootx.ai.util.SharedPreferencesUtils

class AppModel : ViewModel() {
    private val categoryService = CategoryService.instance()
    var categories by mutableStateOf(listOf<CategoryEntity>())

    suspend fun list(context: Context) {

        val res = categoryService.list(
            SharedPreferencesUtils(context).getToken(),
            SharedPreferencesUtils(context).getDeviceId(),
        )
        if (res.code == 0) {
            categories = res.data
        }
    }
}