package com.bootx.ai.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.bootx.ai.ui.components.WebView
import com.bootx.ai.ui.components.dialog.BasicDialog
import com.bootx.ai.viewmodal.HomeModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MineScreen(
    navController: NavController,
    homeModel: HomeModel = viewModel()
) {
    Scaffold(
        topBar = {
            TopAppBar(title = {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(end = 56.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text("我的")
                }
            }, navigationIcon = {
                Icon(imageVector = Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "")
            }, actions = {
                Icon(imageVector = Icons.Default.Settings, contentDescription = "")
            })
        }
    ) {
        LazyColumn(
            modifier = Modifier
                .padding(it)
                .fillMaxSize()
                .padding(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top,
        ) {
            item {
                Row(modifier = Modifier.fillMaxWidth()) {
                    AsyncImage(
                        modifier = Modifier
                            .size(48.dp)
                            .clip(CircleShape),
                        contentScale = ContentScale.FillBounds,
                        model = "https://files.oaiusercontent.com/file-9ki8WhRhSxU7ScmbBPaw8wQV?se=2123-10-27T23%3A49%3A11Z&sp=r&sv=2021-08-06&sr=b&rscc=max-age%3D31536000%2C%20immutable&rscd=attachment%3B%20filename%3Df35efe32-a9c5-48af-9bbd-dd68b842139f.png&sig=BCOPeMRfK/6CjCnpobbvOG8oLeok%2B9qHQ1Sm4ZVRm7Q%3D",
                        contentDescription = ""
                    )
                    Column(
                        modifier = Modifier.padding(start = 8.dp)
                    ) {
                        Text(
                            text = "138****1061",
                            fontWeight = FontWeight.Bold,
                            fontSize = 14.sp,
                        )
                        Text(text = "ID:1532788", fontSize = 12.sp)
                    }
                }
            }
        }
    }
    BasicDialog(
        height=300,
        title = "温馨提示",
        content = {
            WebView(url = "https://www.baidu.com")
        },
        cancelText = "拒绝",
        confirmText = "同意",
    )
}
