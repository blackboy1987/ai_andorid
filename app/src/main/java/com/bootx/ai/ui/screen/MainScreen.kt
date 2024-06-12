package com.bootx.ai.ui.screen

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun MainScreen(
    navController: NavController
) {
    val tabs = listOf(
        "聊天",
        "应用",
        "绘图",
        "视频",
        "论文",
    )
    var selectedTabIndex by remember { mutableIntStateOf(0) }
    val coroutineScope = rememberCoroutineScope()
    val pageSate = rememberPagerState(
        initialPage = selectedTabIndex,
        pageCount = {tabs.size}
    )

    LaunchedEffect(pageSate.currentPage) {
        selectedTabIndex = pageSate.currentPage
    }

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
                                coroutineScope.launch {
                                    pageSate.animateScrollToPage(index)
                                }
                            },
                        )
                    }
                }
            })
        }
    ) {
        HorizontalPager(state = pageSate, modifier = Modifier
            .padding(it)
            .fillMaxSize()) { page->
            when(page){
                0-> ChatScreen(navController)
                1-> AppScreen(navController)
                2-> ImageScreen(navController)
                3-> VideoScreen(navController)
                4-> PaperScreen(navController)
            }
        }
    }
}