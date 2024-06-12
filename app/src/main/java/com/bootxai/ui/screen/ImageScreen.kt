package com.bootxai.ui.screen

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.bootxai.ui.viewmodal.HomeModel

@OptIn(
    ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class,
    ExperimentalLayoutApi::class
)
@Composable
fun ImageScreen(
    navController: NavController,
    homeModel: HomeModel = viewModel()
) {
    var selectedTabIndex by remember { mutableIntStateOf(0) }
    val tabs = listOf(
        "文本生成图像",
        "图像局部重绘",
        "Cosplay动漫人物",
        "人像风格重绘",
        "图像背景生成",
        "涂鸦作画",
        "图像画面扩展"
    )

    Scaffold(
        topBar = {
            TopAppBar(title = {
                ScrollableTabRow(
                    selectedTabIndex = selectedTabIndex,
                    modifier = Modifier
                        .wrapContentWidth()
                        .background(Color.White)
                        .fillMaxWidth(),
                    edgePadding = 0.dp,
                ) {
                    tabs.forEachIndexed { index, item ->
                        Tab(
                            text = {
                                if (index == selectedTabIndex) {
                                    Text(text = item, fontWeight = FontWeight.Bold)
                                } else {
                                    Text(text = item)
                                }
                            },
                            selectedContentColor = Color(0xff000000),
                            unselectedContentColor = Color(0xff9ca0ab),
                            selected = selectedTabIndex == index,
                            onClick = {
                                selectedTabIndex = index
                            },
                        )
                    }
                }
            })
        }
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(it),
        ) {
            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 8.dp, vertical = 8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Row {
                        Text(text = "请输入提示词")
                        Icon(imageVector = Icons.Default.Lock, contentDescription = "")
                    }
                    Row {
                        Icon(imageVector = Icons.Default.Home, contentDescription = "")
                        Text(text = "随机输入")
                    }
                }
                BasicTextField(
                    minLines = 12,
                    value = "content",
                    onValueChange = {},
                    modifier = Modifier
                        .fillMaxWidth()
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
            }
            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 8.dp, vertical = 8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(text = "图片风格")
                }
                FlowRow(
                    maxItemsInEachRow = 3,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                ) {
                    repeat(20) { index ->
                        Card(
                            modifier = Modifier
                                .fillMaxWidth(0.33f)
                                .padding(8.dp)
                        ) {
                            AsyncImage(
                                model = "https://broadscope-wanxiang.oss-cn-beijing.aliyuncs.com/haole/icon/yangguangshaonian.png",
                                contentDescription = ""
                            )
                            Text(
                                text = "阳光少年",
                                modifier = Modifier.fillMaxWidth(),
                                textAlign = TextAlign.Center
                            )
                        }
                    }
                }
            }
            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 8.dp, vertical = 8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(text = "图片比例")
                }
                FlowRow(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                ) {
                    repeat(3) { index ->
                        Column(modifier = Modifier.width(100.dp)) {
                            if(index==0){
                                Box(
                                    contentAlignment = Alignment.Center,
                                    modifier = Modifier
                                        .padding(end = 16.dp)
                                        .size(80.dp)
                                        .background(Color.Gray)
                                ) {
                                    Box(
                                        modifier = Modifier
                                            .width(64.dp)
                                            .height(64.dp)
                                            .background(Color.Red)
                                    )
                                }
                                Text(
                                    text = "1:1",
                                    modifier = Modifier.fillMaxWidth(),
                                    textAlign = TextAlign.Center,
                                )
                            }else if(index==1){
                                Box(
                                    contentAlignment = Alignment.Center,
                                    modifier = Modifier
                                        .padding(end = 16.dp)
                                        .size(80.dp)
                                        .background(Color.Gray)
                                ) {
                                    Box(
                                        modifier = Modifier
                                            .width(64.dp)
                                            .height(36.dp)
                                            .background(Color.Red)
                                    )
                                }
                                Text(
                                    text = "16:9",
                                    modifier = Modifier.fillMaxWidth(),
                                    textAlign = TextAlign.Center,
                                )
                            }else if(index==2){
                                Box(
                                    contentAlignment = Alignment.Center,
                                    modifier = Modifier
                                        .padding(end = 16.dp)
                                        .size(80.dp)
                                        .background(Color.Gray)
                                ) {
                                    Box(
                                        modifier = Modifier
                                            .width(36.dp)
                                            .height(64.dp)
                                            .background(Color.Red)
                                    )
                                }
                                Text(
                                    text = "9:16",
                                    modifier = Modifier.fillMaxWidth(),
                                    textAlign = TextAlign.Center,
                                )
                            }
                        }
                    }
                }
            }
            item{
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 8.dp, vertical = 8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(text = "模型")
                }
                FlowRow(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                ) {
                    repeat(3) { index ->
                        Column(modifier = Modifier.width(100.dp)) {
                            Box(
                                contentAlignment = Alignment.Center,
                                modifier = Modifier
                                    .padding(end = 16.dp)
                                    .size(80.dp)
                                    .background(Color.Gray)
                            ) {
                                Box(
                                    modifier = Modifier
                                        .width(64.dp)
                                        .height(64.dp)
                                        .background(Color.Red)
                                )
                            }
                            Text(
                                text = "1:1",
                                modifier = Modifier.fillMaxWidth(),
                                textAlign = TextAlign.Center,
                            )
                        }
                    }
                }
            }
            item{
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 8.dp, vertical = 8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(text = "模型")
                }
                FlowRow(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                ) {
                    repeat(3) { index ->
                        Column(modifier = Modifier.width(100.dp)) {
                            Box(
                                contentAlignment = Alignment.Center,
                                modifier = Modifier
                                    .padding(end = 16.dp)
                                    .size(80.dp)
                                    .background(Color.Gray)
                            ) {
                                Box(
                                    modifier = Modifier
                                        .width(64.dp)
                                        .height(64.dp)
                                        .background(Color.Red)
                                )
                            }
                            Text(
                                text = "1:1",
                                modifier = Modifier.fillMaxWidth(),
                                textAlign = TextAlign.Center,
                            )
                        }
                    }
                }
            }
        }
    }
}
