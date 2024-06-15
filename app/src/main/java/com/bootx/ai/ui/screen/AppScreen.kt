package com.bootx.ai.ui.screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.SubcomposeAsyncImage
import com.bootx.ai.ui.navigation.Destinations
import com.bootx.ai.ui.viewmodal.AppModel


@OptIn(ExperimentalLayoutApi::class)
@Composable
fun AppScreen(
    navController: NavController,
    appModel: AppModel = viewModel(),
) {
    val context = LocalContext.current
    LaunchedEffect(Unit) {
        appModel.list(context)
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp),
    ) {
        items(appModel.categories){
            Text(text = it.name)
            FlowRow(
                maxItemsInEachRow = 3,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(end = 8.dp),
            ) {

                it.apps.forEachIndexed{_, appEntity ->
                    Card(
                        colors = CardDefaults.cardColors(
                            containerColor = Color(0xff343855)
                        ),
                        modifier = Modifier
                            .clickable {
                                navController.navigate(Destinations.WriteFrame.route+"/"+appEntity.id)
                            }
                            .fillMaxWidth(0.33f)
                            .padding(4.dp)
                    ) {
                        Column(
                            modifier = Modifier.padding(vertical = 16.dp),
                            horizontalAlignment = Alignment.CenterHorizontally,
                        ) {
                            SubcomposeAsyncImage(
                                model = appEntity.thumb,
                                contentDescription = "",
                                loading = {
                                    CircularProgressIndicator(strokeWidth=2.dp)
                                },
                                modifier = Modifier.size(80.dp),
                                contentScale = ContentScale.FillBounds,
                            )
                            Spacer(modifier = Modifier.height(16.dp))
                            Text(
                                text = appEntity.title,
                                fontSize = 12.sp,
                                maxLines = 1,
                                color = Color.White,
                                overflow = TextOverflow.Ellipsis,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 8.dp),
                                textAlign = TextAlign.Center
                            )
                            Text(
                                text = appEntity.memo,
                                fontSize = 12.sp,
                                maxLines = 1,
                                color = Color(0xff9d9cbc),
                                overflow = TextOverflow.Ellipsis,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 8.dp),
                                textAlign = TextAlign.Center
                            )
                        }
                    }
                }
            }
        }
    }
}
