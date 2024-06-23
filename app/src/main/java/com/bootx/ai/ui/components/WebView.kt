package com.bootx.ai.ui.components

import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView

@Composable
fun WebView(
    url: String,
    modifier: Modifier = Modifier
) {
    AndroidView(
        factory = { context ->
            WebView(context).apply {
                webViewClient = WebViewClient()
                webViewClient = object : WebViewClient() {
                    override fun shouldOverrideUrlLoading(view: WebView?, request: WebResourceRequest?): Boolean {
                        // 在这里可以进行额外的安全检查
                        return super.shouldOverrideUrlLoading(view, request)
                    }
                }
                settings.apply {
                    // 仅在必要时启用 JavaScript
                    javaScriptEnabled = true

                    // 禁用文件访问
                    allowFileAccess = false
                    allowContentAccess = false

                    // 禁用通过文件 URL 访问文件
                    allowFileAccessFromFileURLs = false
                    allowUniversalAccessFromFileURLs = false
                }
                loadUrl(url)
            }
        },
        update = { webView ->
            webView.loadUrl(url)
        },
        modifier = modifier
    )
}