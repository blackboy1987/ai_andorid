package com.bootxai.ui.viewmodal

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bootxai.util.SSEClient
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class HomeModel : ViewModel() {

    private val sseClient = SSEClient("http://172.16.13.94:9902/api/message?content=%E4%BD%A0%E6%98%AF%E8%B0%81")
    private val _messages = MutableStateFlow<List<String>>(emptyList())
    val messages = _messages.asStateFlow()

    init {
        viewModelScope.launch {
            sseClient.events.collect { message ->
                Log.e("viewModelScope", "HomeModel: $message", )
                _messages.value += message
            }
        }
        sseClient.connect()
    }

    public override fun onCleared() {
        super.onCleared()
        sseClient.disconnect()
    }
}