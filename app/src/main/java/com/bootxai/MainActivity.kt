package com.bootxai

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.core.view.WindowCompat
import com.bootxai.ui.screen.HomeScreen
import com.bootxai.ui.theme.AppTheme
import com.umeng.commonsdk.UMConfigure

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        UMConfigure.init(this, "664eb5c2cac2a664de3a2b22", "Umeng", UMConfigure.DEVICE_TYPE_PHONE, "")
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        WindowCompat.setDecorFitsSystemWindows(window, false)
        setContent {
            AppTheme {
                HomeScreen()
            }
        }
    }
}