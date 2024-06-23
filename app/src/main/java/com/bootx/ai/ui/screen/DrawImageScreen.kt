package com.bootx.ai.ui.screen

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.bootx.ai.ui.navigation.Destinations
import com.bootx.ai.viewmodal.ImageViewModel
import kotlinx.coroutines.launch
import java.util.Date

@OptIn(
    ExperimentalMaterial3Api::class,
    ExperimentalLayoutApi::class
)
@Composable
fun DrawImageScreen(
    navController: NavController,
    imageViewModel: ImageViewModel = viewModel()
) {
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()

    val styles = listOf("photography","portrait","3d cartoon","anime","oil painting","watercolor","sketch","chinese painting","flat illustration","auto")

    val scaffoldState = rememberBottomSheetScaffoldState()
    LaunchedEffect(Unit) {
        imageViewModel.config(context)
    }
    val lazyListState = rememberLazyListState()
    var prompt by rememberSaveable {
        mutableStateOf("")
    }
    var style by rememberSaveable {
        mutableStateOf("auto")
    }
    var size by rememberSaveable {
        mutableStateOf("1024*1024")
    }
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
        item {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Text(
                        text = "请输入提示词",
                        modifier = Modifier
                            .clickable {
                                coroutineScope.launch {
                                    scaffoldState.bottomSheetState.expand()
                                    scaffoldState.bottomSheetState.show()
                                }
                            },
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Icon(imageVector = Icons.Default.Info,
                        contentDescription = "",
                        modifier = Modifier
                            .size(14.dp)
                            .clip(
                                CircleShape
                            ),)
                }
                Row(
                    modifier = Modifier
                        .clickable {
                            val samples = imageViewModel.imageAppEntity.textToImage.samples
                            val index = Date().time % samples.size
                            prompt = samples[index.toInt()].title
                        }
                        .padding(horizontal = 4.dp)
                        .clip(RoundedCornerShape(4.dp)),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Icon(
                        imageVector = Icons.Default.Refresh,
                        contentDescription = "",
                        modifier = Modifier.size(14.dp),
                    )
                    Text(
                        text = "随机输入",
                        modifier = Modifier.padding(start = 2.dp),
                    )
                }
            }
            BasicTextField(
                minLines = 4,
                maxLines = 4,
                value = prompt,
                onValueChange = {
                    prompt = it
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp)
                    .border(
                        BorderStroke(1.dp, Color.Blue),
                        shape = MaterialTheme.shapes.small
                    )
                    .padding(8.dp),
                decorationBox = { innerTextField ->
                    Box(modifier = Modifier.padding(8.dp)) {
                        innerTextField()
                    }
                }
            )
        }
        item {
            Text(text = "风格", modifier = Modifier.padding(start = 8.dp))
            FlowRow(
                maxItemsInEachRow = 4,
                modifier = Modifier
                    .fillMaxWidth(),
            ) {
                styles.forEachIndexed { index, modelList ->
                    Button(onClick = {
                        style = modelList
                    }) {
                        Text(
                            text = modelList,
                            fontSize = MaterialTheme.typography.labelSmall.fontSize,
                        )
                    }
                }
            }
        }
        item {
            Text(text = "图片大小", modifier = Modifier.padding(start = 8.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp, vertical = 8.dp),
                horizontalArrangement = Arrangement.SpaceAround,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                imageViewModel.imageAppEntity.textToImage.resolutions.forEachIndexed { index, s ->
                    var width = 32.dp
                    var height = 32.dp
                    if (index == 1) {
                        width = 32.dp
                        height = 17.dp
                    } else if (index == 2) {
                        width = 17.dp
                        height = 32.dp
                    }

                    Column(modifier = Modifier.width(100.dp)) {
                        Box(
                            contentAlignment = Alignment.Center,
                            modifier = Modifier
                                .padding(end = 16.dp)
                                .size(40.dp)
                                .background(Color.Gray)
                                .clickable {
                                    size = s
                                }
                        ) {
                            Box(
                                modifier = Modifier
                                    .width(width)
                                    .height(height)
                                    .background(Color.Red)
                            )
                        }
                        Text(
                            text = s,
                            modifier = Modifier.width(40.dp),
                            textAlign = TextAlign.Center,
                            fontSize = MaterialTheme.typography.labelSmall.fontSize,
                        )
                    }
                }
            }
        }
        item{
            Button(
                enabled = !imageViewModel.loading,
                modifier = Modifier.fillMaxWidth(), onClick = {
                coroutineScope.launch {
                    val result = imageViewModel.create(context, prompt, style, size)
                    if(result.code==0){
                        navController.navigate(Destinations.DrawImageResultFrame.route+"/"+result.data)
                    }

                }
            }) {
                Text(text = "生成")
            }
        }
    }
}
