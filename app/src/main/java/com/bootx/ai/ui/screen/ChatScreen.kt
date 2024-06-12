package com.bootx.ai.ui.screen

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.bootx.ai.ui.viewmodal.HomeModel

@Composable
fun ChatScreen(
    navController: NavController,
    homeModel: HomeModel = viewModel()
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp)
    ) {
        item {
            Row {
                AsyncImage(
                    contentScale = ContentScale.FillBounds,
                    modifier = Modifier.clip(CircleShape),
                    model = "https://cdn.aichatzw.com/home/assets/logo-a42cf706.png",
                    contentDescription = ""
                )
                Text(text = "sadfasfassadfasfassadfasfassadfasfassadfasfassadfasfassadfasfassadfasfassadfasfassadfasfassadfasfassadfasfassadfasfassadfasfassadfasfassadfasfassadfasfassadfasfassadfasfassadfasfassadfasfassadfasfassadfasfassadfasfassadfasfassadfasfassadfasfassadfasfassadfasfassadfasfassadfasfassadfasfassadfasfassadfasfassadfasfassadfasfassadfasfassadfasfassadfasfassadfasfassadfasfassadfasfassadfasfassadfasfassadfasfassadfasfassadfasfassadfasfassadfasfassadfasfas")
            }
        }
    }
}