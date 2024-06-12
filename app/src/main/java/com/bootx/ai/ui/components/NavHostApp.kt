package com.bootx.ai.ui.components

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.bootx.ai.ui.navigation.Destinations
import com.bootx.ai.ui.screen.HomeScreen
import com.bootx.ai.ui.screen.LoginCodeScreen
import com.bootx.ai.ui.screen.LoginScreen

@RequiresApi(Build.VERSION_CODES.Q)
@Composable
fun NavHostApp() {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = Destinations.MainFrame.route,
    ) {
        composable(
            Destinations.MainFrame.route,
        ) {
            LoginCodeScreen(navController)
        }
        composable(
            Destinations.HomeFrame.route,
        ) {
            HomeScreen(navController)
        }
    }
}