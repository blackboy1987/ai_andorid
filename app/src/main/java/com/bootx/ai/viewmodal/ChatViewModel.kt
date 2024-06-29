package com.bootx.ai.viewmodal

import android.content.Context
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.bootx.ai.config.Config
import com.bootx.ai.entity.TextMessageEntity
import com.bootx.ai.repository.DataBase
import com.bootx.ai.repository.entity.ChatMessage
import com.bootx.ai.util.SSEClient
import com.google.gson.Gson
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ChatViewModel : ViewModel() {
    var messageList by mutableStateOf(listOf<ChatMessage>())

    suspend fun load(context: Context) {
        CoroutineScope(Dispatchers.IO).launch {
            // 写入到数据库
            try {
                val chatMessageDao = DataBase.getDb(context)?.getChatMessageDao()
                messageList = chatMessageDao?.getAll()!!
            }catch (e:Exception){
                Log.e("messageList", "insertDb: ${e.message}", )
                messageList = listOf()
            }
        }
    }

    suspend fun insertDb(context: Context, chatMessage: ChatMessage) {
        CoroutineScope(Dispatchers.IO).launch {
            // 写入到数据库
            try {
                val chatMessageDao = DataBase.getDb(context)?.getChatMessageDao()
                chatMessageDao?.insert(
                        chatMessage
                    )
                Log.e("IconButton", "ChatScreen: 保存消息", )
                messageList = chatMessageDao?.getAll()!!
                Log.e("messageList", "insertDb: $messageList", )
            }catch (e:Exception){
                Log.e("messageList", "insertDb: ${e.message}", )
            }
        }
    }
}