package com.bootx.ai.ui.components

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.bootx.ai.ui.navigation.Destinations
import com.bootx.ai.ui.screen.AppScreen
import com.bootx.ai.ui.screen.HomeScreen
import com.bootx.ai.ui.screen.LoginCodeScreen
import com.bootx.ai.ui.screen.MainScreen
import com.bootx.ai.ui.screen.WriteScreen

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
            MainScreen(navController)
        }
        composable(
            Destinations.HomeFrame.route,
        ) {
            HomeScreen(navController)
        }
        composable(
            Destinations.LoginCodeFrame.route,
        ) {
            LoginCodeScreen(navController)
        }
        composable(
            Destinations.WriteFrame.route+"/{id}",
        ) {
            val id = it.arguments?.getString("id") ?: "0"
            WriteScreen(navController,id)
        }
    }
}