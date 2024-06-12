package com.bootx.ai.ui.screen

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.bootx.ai.ui.viewmodal.HomeModel


@Composable
fun AppScreen(
    navController: NavController,
    homeModel: HomeModel = viewModel()
) {
    Text(text = "AppScreen")
}
