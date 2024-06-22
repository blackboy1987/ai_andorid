package com.bootx.ai.ui.screen

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SecondaryScrollableTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.bootx.ai.viewmodal.ImageViewModel
import kotlinx.coroutines.launch

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
    val coroutineScope = rememberCoroutineScope()
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

    val lazyListState = rememberLazyListState()

    Column(
        modifier = Modifier
            .fillMaxSize(),
    ) {
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
                        coroutineScope.launch {
                            lazyListState.animateScrollToItem(0)
                        }
                        selectedTabIndex = index
                    },
                )
            }
        }
        DrawImageScreen(navController)
    }
}
