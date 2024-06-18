package com.bootx.ai.ui.components

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.bootx.ai.ui.navigation.Destinations
import com.bootx.ai.ui.screen.AppScreen
import com.bootx.ai.ui.screen.HomeScreen
import com.bootx.ai.ui.screen.LoginCodeScreen
import com.bootx.ai.ui.screen.LoginScreen
import com.bootx.ai.ui.screen.MainScreen
import com.bootx.ai.ui.screen.WriteLogDetailScreen
import com.bootx.ai.ui.screen.WriteLogScreen
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
        composable(
            Destinations.LoginFrame.route,
            enterTransition = {slideInHorizontally(initialOffsetX = { 1000 }) + fadeIn()},
            exitTransition = {slideOutHorizontally(targetOffsetX = { -1000 }) + fadeOut()},
            popEnterTransition = {slideInHorizontally(initialOffsetX = { -1000 }) + fadeIn()},
            popExitTransition = { slideOutHorizontally(targetOffsetX = { 1000 }) + fadeOut()}
        ) {
            LoginScreen(navController)
        }
        composable(
            Destinations.WriteLogFrame.route+"/{id}",
            enterTransition = {slideInHorizontally(initialOffsetX = { 1000 }) + fadeIn()},
            exitTransition = {slideOutHorizontally(targetOffsetX = { -1000 }) + fadeOut()},
            popEnterTransition = {slideInHorizontally(initialOffsetX = { -1000 }) + fadeIn()},
            popExitTransition = { slideOutHorizontally(targetOffsetX = { 1000 }) + fadeOut()}
        ) {
            val id = it.arguments?.getString("id") ?: "0"
            WriteLogScreen(navController,id)
        }
        composable(
            Destinations.WriteLogDetailFrame.route+"/{taskId}",
            enterTransition = {slideInHorizontally(initialOffsetX = { 1000 }) + fadeIn()},
            exitTransition = {slideOutHorizontally(targetOffsetX = { -1000 }) + fadeOut()},
            popEnterTransition = {slideInHorizontally(initialOffsetX = { -1000 }) + fadeIn()},
            popExitTransition = { slideOutHorizontally(targetOffsetX = { 1000 }) + fadeOut()}
        ) {
            val taskId = it.arguments?.getString("taskId") ?: "0"
            WriteLogDetailScreen(navController,taskId)
        }
    }
}