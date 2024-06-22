package com.bootx.ai.ui.screen

import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.bootx.ai.viewmodal.ImageViewModel

@Composable
fun DrawImageResultScreen(
    navController: NavController,
    taskId: String,
    imageViewModel: ImageViewModel = viewModel(),
) {
    val context = LocalContext.current
    LaunchedEffect(Unit) {
        imageViewModel.task(context,taskId)
    }
    val lazyListState = rememberLazyListState()
    val keyboardController = LocalSoftwareKeyboardController.current

    LazyColumn(
        state = lazyListState,
        modifier = Modifier
            .fillMaxSize()
            .pointerInput(Unit) {
                detectTapGestures(onTap = {
                    keyboardController?.hide()
                })
            },
    ) {
        item{
            Button(
                enabled = !imageViewModel.loading,
                modifier = Modifier.fillMaxWidth(), onClick = {
            }) {
                Text(text = "生成")
            }
        }
    }
}
