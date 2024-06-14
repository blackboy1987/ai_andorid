package com.bootx.ai.ui.viewmodal

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bootx.ai.entity.CategoryEntity
import com.bootx.ai.entity.TextMessageEntity
import com.bootx.ai.util.SSEClient
import com.google.gson.Gson
import com.google.gson.JsonArray
import com.squareup.moshi.Json
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class HomeModel : ViewModel() {

    private val sseClient = SSEClient("http://172.16.13.94:9902/api/message")
    private val _messages = MutableStateFlow<List<String>>(emptyList())
    val messages = _messages.asStateFlow()
    var messageList by mutableStateOf(listOf<TextMessageEntity>())

    init {
        viewModelScope.launch {
            sseClient.events.collect { message ->
                val gson = Gson()
                val textMessageEntity = gson.fromJson(message, TextMessageEntity::class.java)
                _messages.value += textMessageEntity.content
                Log.e("viewModelScope", "HomeModel: $message", )
            }
        }
    }

    fun connect(content: String) {
        val parameters = mutableMapOf<String,String>()
        parameters["content"] = content
        sseClient.connect(parameters)
    }


    public override fun onCleared() {
        super.onCleared()
        sseClient.disconnect()
    }
}