package com.bootx.ai.ui.screen

import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SecondaryScrollableTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.bootx.ai.ui.viewmodal.ImageViewModel
import java.util.Date

@OptIn(
    ExperimentalMaterial3Api::class,
    ExperimentalLayoutApi::class, ExperimentalFoundationApi::class
)
@Composable
fun ImageScreen(
    navController: NavController,
    imageViewModel: ImageViewModel = viewModel()
) {
    val context = LocalContext.current
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
    var prompt by rememberSaveable {
        mutableStateOf("")
    }
    LaunchedEffect(Unit) {
        imageViewModel.config(context)
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize(),
    ) {
        stickyHeader {
            SecondaryScrollableTabRow(
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
        }

        item {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Text(
                        text = "请输入提示词",
                        fontSize = MaterialTheme.typography.labelSmall.fontSize,
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Icon(imageVector = Icons.Default.Info,
                        contentDescription = "",
                        modifier = Modifier
                            .size(14.dp)
                            .clip(
                                CircleShape
                            )
                            .clickable {

                            })
                }
                Row(
                    modifier = Modifier
                        .clickable {
                            val samples = imageViewModel.imageAppEntity.textToImage.samples
                            val index = Date().time % samples.size
                            prompt = samples[index.toInt()].title
                        }
                        .padding(horizontal = 4.dp)
                        .clip(RoundedCornerShape(4.dp)),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Icon(
                        imageVector = Icons.Default.Refresh,
                        contentDescription = "",
                        modifier = Modifier.size(14.dp),
                    )
                    Text(
                        text = "随机输入",
                        modifier = Modifier.padding(start = 2.dp),
                        fontSize = MaterialTheme.typography.labelSmall.fontSize,
                    )
                }
            }
            BasicTextField(
                minLines = 8,
                maxLines = 8,
                value = prompt,
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
            Text(text = "形象", modifier = Modifier.padding(start = 8.dp))
            FlowRow(
                maxItemsInEachRow = 4,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 8.dp),
            ) {
                val modelList1 =
                    imageViewModel.imageAppEntity.modelList.filter { item -> item.category == "profile" }
                modelList1.forEachIndexed { index, modelList ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth(0.24f)
                            .padding(4.dp),
                    ) {
                        AsyncImage(
                            model = "${modelList.cover}",
                            contentDescription = ""
                        )
                        Text(
                            text = "${modelList.modelName}",
                            modifier = Modifier.fillMaxWidth(),
                            textAlign = TextAlign.Center,
                            fontSize = MaterialTheme.typography.labelSmall.fontSize,
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
                Text(text = "风格")
            }
            FlowRow(
                maxItemsInEachRow = 4,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
            ) {
                val modelList1 =
                    imageViewModel.imageAppEntity.modelList.filter { item -> item.category == "style" }
                modelList1.forEachIndexed { index, modelList ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth(0.24f)
                            .padding(8.dp)
                    ) {
                        AsyncImage(
                            model = "${modelList.cover}",
                            contentDescription = ""
                        )
                        Text(
                            text = "${modelList.modelName}",
                            modifier = Modifier.fillMaxWidth(),
                            textAlign = TextAlign.Center,
                            fontSize = MaterialTheme.typography.labelSmall.fontSize,
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
                Text(text = "图片大小")
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp, vertical = 8.dp),
                horizontalArrangement = Arrangement.SpaceAround,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                imageViewModel.imageAppEntity.textToImage.resolutions.forEachIndexed { index, s ->
                    var text = "1:1"
                    var width = 32.dp
                    var height = 32.dp
                    if (index == 1) {
                        width = 32.dp
                        height = 17.dp
                        text = "16:9"
                    } else if (index == 2) {
                        width = 17.dp
                        height = 32.dp
                        text = "9:16"
                    }

                    Column(modifier = Modifier.width(100.dp)) {
                        Box(
                            contentAlignment = Alignment.Center,
                            modifier = Modifier
                                .padding(end = 16.dp)
                                .size(40.dp)
                                .background(Color.Gray)
                        ) {
                            Box(
                                modifier = Modifier
                                    .width(width)
                                    .height(height)
                                    .background(Color.Red)
                            )
                        }
                        Text(
                            text = text,
                            modifier = Modifier.width(40.dp),
                            textAlign = TextAlign.Center,
                            fontSize = MaterialTheme.typography.labelSmall.fontSize,
                        )
                    }
                }
            }
        }
    }
}
