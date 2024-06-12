package com.bootx.ai

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.core.view.WindowCompat
import com.bootx.ai.ui.components.NavHostApp
import com.bootx.ai.ui.theme.AppTheme
import com.umeng.commonsdk.UMConfigure

class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.Q)
    override fun onCreate(savedInstanceState: Bundle?) {
        UMConfigure.init(this, "664eb5c2cac2a664de3a2b22", "Umeng", UMConfigure.DEVICE_TYPE_PHONE, "")
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        WindowCompat.setDecorFitsSystemWindows(window, false)
        setContent {
            AppTheme {
                NavHostApp()
            }
        }
    }
}