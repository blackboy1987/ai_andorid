package com.bootx.ai.ui.screen

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.bootx.ai.viewmodal.HomeModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    navController: NavController,
    homeModel: HomeModel = viewModel()
) {
    var content by remember {
        mutableStateOf("")
    }

    Scaffold {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(it),
        ) {
            LazyColumn(modifier = Modifier
                .padding(horizontal = 16.dp)
                .weight(1f)) {

            }
            Row(
                modifier = Modifier
                    .background(Color.Red)
                    .fillMaxWidth()
                    .padding(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                BasicTextField(
                    value = content,
                    onValueChange = {value-> content = value },
                    modifier = Modifier
                        .weight(1f)
                        .border(
                            BorderStroke(2.dp, Color.Blue), // 设置边框颜色
                            shape = MaterialTheme.shapes.small // 使用默认形状
                        )
                        .padding(8.dp), // 内边距
                    decorationBox = { innerTextField ->
                        Box(modifier = Modifier.padding(8.dp)) {
                            if (content.isEmpty()) {
                                Text(text = "Enter text", color = Color.Gray)
                            }
                            innerTextField()
                        }
                    }
                )

                IconButton(onClick = {
                    if(content.isNotBlank()){

                    }
                }) {
                    Icon(imageVector = Icons.Default.Send, contentDescription = "Send", tint = Color.Blue)
                }
            }
        }
    }
}