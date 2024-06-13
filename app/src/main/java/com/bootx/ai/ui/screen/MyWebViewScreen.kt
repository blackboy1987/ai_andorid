package com.bootx.ai.ui.screen

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.bootx.ai.ui.components.WebView


@Composable
fun MyWebView(
    navController: NavController,
    url: String
) {
    WebView(url = url)
}
