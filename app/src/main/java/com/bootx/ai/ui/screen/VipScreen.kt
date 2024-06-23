package com.bootx.ai.ui.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.bootx.ai.R
import com.bootx.ai.viewmodal.HomeModel


@Composable
fun VipScreen(
    navController: NavController,
    homeModel: HomeModel = viewModel()
) {
    Box(modifier = Modifier.fillMaxSize()){
        Image(painter = painterResource(id = R.drawable.bg_title), contentDescription = "", modifier = Modifier.fillMaxWidth(), contentScale = ContentScale.FillWidth)
    }
}
