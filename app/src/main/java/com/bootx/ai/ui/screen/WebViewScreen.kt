package com.bootx.ai.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavController
import com.bootx.ai.ui.components.WebView


@Composable
fun WebViewScreen(
    navController: NavController,
    url: String,
) {
    WebView("http://192.168.31.214:5173/", modifier = Modifier
        .fillMaxSize()
        .background(Color.Red))
}
