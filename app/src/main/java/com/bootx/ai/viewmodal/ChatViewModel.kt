package com.bootx.ai.viewmodal

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.bootx.ai.config.Config
import com.bootx.ai.entity.TextMessageEntity
import com.bootx.ai.util.SSEClient
import com.google.gson.Gson
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ChatViewModel : ViewModel() {
    private var _messages = MutableStateFlow<List<TextMessageEntity>>(emptyList())
    val messages: StateFlow<List<TextMessageEntity>> = _messages.asStateFlow()
    var count by mutableIntStateOf(0)
    private val sseClient = SSEClient(Config.baseUrl + "/api/member/message")
    val gson = Gson()

    fun connect(content: String) {
        count = 0
        val parameters = mutableMapOf<String, String>()
        parameters["content"] = content
        sseClient.connect(parameters) { message ->
            CoroutineScope(Dispatchers.Main).launch {
                val textMessageEntity = gson.fromJson(message, TextMessageEntity::class.java)
                val mutableList = mutableListOf<TextMessageEntity>()
                mutableList.addAll(_messages.value)
                val current = mutableList.filter { item ->
                    item.requestId == textMessageEntity.requestId
                }
                if(current.isEmpty()){
                    mutableList.add(textMessageEntity)
                }else{
                    current[0].content += textMessageEntity.content
                }
                _messages.value = mutableList
                count += 1
                Log.e("connect", "connect: ${_messages.value}", )
            }
        }
    }
    public override fun onCleared() {
        super.onCleared()
        sseClient.disconnect()
    }
}