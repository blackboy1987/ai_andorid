package com.bootx.ai.ui.screen

import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.bootx.ai.entity.TextMessageEntity
import com.bootx.ai.util.CommonUtils
import com.bootx.ai.util.EventStreamManager
import com.bootx.ai.viewmodal.ChatViewModel
import com.google.gson.Gson
import kotlinx.coroutines.launch
import kotlin.math.log

@Composable
fun ChatScreen(
    navController: NavController,
    chatViewModel: ChatViewModel = viewModel()
) {

    var message by remember { mutableStateOf("") }
    val gson = Gson()
    var eventStreamManager by remember { mutableStateOf<EventStreamManager?>(null) }
    var content by remember {
        mutableStateOf("")
    }
    val scope = rememberCoroutineScope()
    val context = LocalContext.current
    val focusManager = LocalFocusManager.current
    Scaffold {
        Column(
            modifier = Modifier.padding(it),
        ){
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .weight(1.0f)
                    .padding(8.dp),
            ) {
                item{
                    OtherContent(content = message)
                }
            }
            Row {
                BasicTextField(
                    minLines = 1,
                    value = content,
                    onValueChange = { value ->
                        content = value
                    },
                    modifier = Modifier
                        .weight(1.0f)
                        .padding(horizontal = 8.dp)
                        .border(
                            BorderStroke(1.dp, Color.Blue), // 设置边框颜色
                            shape = MaterialTheme.shapes.small // 使用默认形状
                        )
                        .padding(8.dp), // 内边距
                    decorationBox = { innerTextField ->
                        Box(modifier = Modifier.padding(8.dp)) {
                            innerTextField()
                        }
                    }
                )
                IconButton(onClick = {
                    if(content.isNotBlank()){
                        if (eventStreamManager == null) {
                            eventStreamManager = EventStreamManager("http://192.168.31.214:9902/api/member1/message?content=$content") { data ->
                                scope.launch {
                                    Log.e("eventStreamManager", "ChatScreen: $data", )
                                    if(data.isNotEmpty()){
                                        val textMessageEntity = gson.fromJson(data.replace("data:",""), TextMessageEntity::class.java)
                                        message += textMessageEntity.content
                                    }
                                }
                            }
                            eventStreamManager?.start()
                        }
                        content = ""
                    }else{
                        CommonUtils.toast(context,"请输入内容")
                    }
                    CommonUtils.hideKeyboard(context)
                    focusManager.clearFocus()
                }) {
                    Icon(imageVector = Icons.AutoMirrored.Filled.Send, contentDescription = "")
                }
            }
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}

@Composable
fun OtherContent(content: String) {
    Row(
        modifier = Modifier.padding(bottom = 8.dp)
    ) {
        AsyncImage(
            contentScale = ContentScale.FillBounds,
            modifier = Modifier
                .clip(CircleShape)
                .height(32.dp)
                .width(32.dp),
            model = "https://cdn.aichatzw.com/home/assets/logo-a42cf706.png",
            contentDescription = ""
        )
        Spacer(modifier = Modifier.width(4.dp))
        Card(
            modifier = Modifier
                .weight(1.0f),
            colors = CardDefaults.cardColors(
                containerColor = Color(0xff3c3e64)
            ),
        ) {
            Text(
                modifier = Modifier.padding(8.dp),
                color = Color.White,
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
                text = content
            )
        }
        Spacer(modifier = Modifier.width(40.dp))
    }
}

@Composable
fun MyContent(content: String) {
    Row(
        modifier = Modifier.padding(bottom = 8.dp)
    ) {
        Spacer(modifier = Modifier.width(40.dp))
        Card(
            modifier = Modifier
                .weight(1.0f),
            colors = CardDefaults.cardColors(
                containerColor = Color(0xff7d86ff)
            ),
        ) {
            Text(
                modifier = Modifier.padding(8.dp),
                color = Color.White,
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
                text = content,
            )
        }
        Spacer(modifier = Modifier.width(4.dp))
        AsyncImage(
            contentScale = ContentScale.FillBounds,
            modifier = Modifier
                .clip(CircleShape)
                .height(32.dp)
                .width(32.dp),
            model = "https://cdn.aichatzw.com/home/assets/logo-a42cf706.png",
            contentDescription = ""
        )
    }
}