package com.bootx.ai.ui.screen

import android.util.Log
import androidx.annotation.FloatRange
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.TabPosition
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.lerp
import androidx.navigation.NavController
import com.bootx.ai.util.AppInfoUtils
import com.bootx.ai.util.CommonUtils
import kotlinx.coroutines.launch
import kotlin.math.abs
import kotlin.reflect.jvm.internal.impl.descriptors.Visibilities.Local

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
    var selectedTabIndex by remember { mutableIntStateOf(2) }
    val coroutineScope = rememberCoroutineScope()
    val pageSate = rememberPagerState(
        initialPage = selectedTabIndex,
        pageCount = {tabs.size}
    )
    val context = LocalContext.current

    LaunchedEffect(pageSate.currentPage) {
        selectedTabIndex = pageSate.currentPage
        val appInfo = AppInfoUtils.getAppInfo(
            context
        )
        Log.e("AppInfoUtils", "MainScreen: ${appInfo.versionCode}", )
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                ScrollableTabRow(
                    selectedTabIndex = selectedTabIndex,
                    modifier = Modifier
                        .wrapContentWidth()
                        .fillMaxWidth(),
                    edgePadding = 0.dp,
                    divider = {},
                    indicator = {tabPositions->
                        PagerTabIndicator(tabPositions,pageSate, height = 4.dp)
                    }
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


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun PagerTabIndicator(
    tabPositions: List<TabPosition>,
    pagerState: PagerState,
    color: Color = MaterialTheme.colorScheme.primary,
    @FloatRange(from = 0.0, to = 1.0) percent: Float = 0.4f,
    height: Dp = 5.dp,
) {
    val currentPage by rememberUpdatedState(newValue = pagerState.currentPage)
    val fraction by rememberUpdatedState(newValue = pagerState.currentPageOffsetFraction)
    val currentTab = tabPositions[currentPage]
    val previousTab = tabPositions.getOrNull(currentPage - 1)
    val nextTab = tabPositions.getOrNull(currentPage + 1)
    Canvas(
        modifier = Modifier.fillMaxSize(),
        onDraw = {
            val indicatorWidth = currentTab.width.toPx() * percent
            val indicatorOffset = if (fraction > 0 && nextTab != null) {
                lerp(currentTab.left, nextTab.left, fraction).toPx()
            } else if (fraction < 0 && previousTab != null) {
                lerp(currentTab.left, previousTab.left, -fraction).toPx()
            } else {
                currentTab.left.toPx()
            }
            val canvasHeight = size.height
            drawRoundRect(
                color = color,
                topLeft = Offset(
                    indicatorOffset + (currentTab.width.toPx() * (1 - percent) / 2),
                    canvasHeight - height.toPx()
                ),
                size = Size(indicatorWidth + indicatorWidth * abs(fraction), height.toPx()),
                cornerRadius = CornerRadius(50f)
            )
        }
    )
}