package com.bootxai.ui.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.bootxai.ui.viewmodal.HomeModel

@Composable
fun HomeScreen(
    homeModel: HomeModel = viewModel()
) {
    val messages by homeModel.messages.collectAsState()
    var content by remember {
        mutableStateOf("")
    }

    Column(modifier = Modifier
        .fillMaxSize()
        .padding(16.dp)) {
        LazyColumn(modifier = Modifier.weight(1f)) {
            items(messages) { message ->
                Text(text = message)
            }
            item {
                TextField(value = content, onValueChange = {
                    content = it
                })
                Button(onClick = { homeModel.connect(content) }) {
                    Text(text = "连接")
                }
            }
            item {
                Button(onClick = { homeModel.onCleared() }) {
                    Text(text = "关闭")
                }
            }
        }
    }

}