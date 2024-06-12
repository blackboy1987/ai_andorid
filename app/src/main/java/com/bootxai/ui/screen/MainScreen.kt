package com.bootxai.ui.screen

import android.content.Context
import android.util.DisplayMetrics
import androidx.activity.ComponentActivity
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.core.view.WindowInsetsControllerCompat
import com.bootxai.R

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun MainScreen() {
    val context = LocalContext.current
    val configuration = LocalConfiguration.current
    val screenWidthDp = configuration.screenWidthDp
    val screenHeightDp = configuration.screenHeightDp

    val statusBarHeight = remember { getStatusBarHeight(context) }
    val menuBarHeight = remember { getMenuBarHeight(context) }


    val pageState = rememberPagerState(
        initialPage = 0,
        pageCount = { 3 },
    )

    Box(modifier = Modifier.fillMaxSize()) {
        Box(modifier = Modifier.fillMaxSize()){
            Column(modifier = Modifier.fillMaxSize()) {
                Image(
                    painter = painterResource(id = R.drawable.bt_up),
                    contentDescription = null,
                    modifier = Modifier.width(width = screenWidthDp.dp).height(((screenHeightDp+statusBarHeight)/2).dp),
                    contentScale = ContentScale.FillBounds
                )
                Image(
                    painter = painterResource(id = R.drawable.bg_down),
                    contentDescription = null,
                    modifier = Modifier.width(width = screenWidthDp.dp).height(((screenHeightDp+statusBarHeight+menuBarHeight)/2).dp),
                    contentScale = ContentScale.FillBounds
                )
            }
        }
    }

    /*HorizontalPager(state = pageState) {

        Box(
            contentAlignment = Alignment.Center, modifier = Modifier
                .fillMaxSize()
                .background(
                    Color(0xff171926),
                )
        ) {
            when (it) {
                0 -> Image(
                    painter = painterResource(id = R.drawable.bg_01), contentDescription = ""
                )

                1 -> Image(
                    painter = painterResource(id = R.drawable.bg_02), contentDescription = ""
                )

                2 -> Image(
                    painter = painterResource(id = R.drawable.bg_03), contentDescription = ""
                )
            }
        }
    }*/
}


fun getStatusBarHeight(context: Context): Int {
    val resourceId = context.resources.getIdentifier("status_bar_height", "dimen", "android")
    return if (resourceId > 0) {
        context.resources.getDimensionPixelSize(resourceId)
    } else {
        0
    }
}

fun getMenuBarHeight(context: Context): Int {
    val resourceId = context.resources.getIdentifier("navigation_bar_height", "dimen", "android")
    return if (resourceId > 0) {
        context.resources.getDimensionPixelSize(resourceId)
    } else {
        0
    }
}