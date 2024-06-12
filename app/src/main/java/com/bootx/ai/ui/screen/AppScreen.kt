package com.bootx.ai.ui.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.bootx.ai.ui.viewmodal.HomeModel


@OptIn(ExperimentalLayoutApi::class)
@Composable
fun AppScreen(
    navController: NavController,
    homeModel: HomeModel = viewModel()
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp),
    ) {
        item{
            Text(text = "品牌营销")
            FlowRow(
                maxItemsInEachRow = 3,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(end = 8.dp),
            ) {
                repeat(20) { index ->
                    Card(
                        colors = CardDefaults.cardColors(
                            containerColor = Color(0xff343855)
                        ),
                        modifier = Modifier
                            .fillMaxWidth(0.33f)
                            .padding(4.dp)
                    ) {
                        Column(
                            modifier = Modifier.padding(vertical = 32.dp),
                            horizontalAlignment = Alignment.CenterHorizontally,
                        ) {
                            AsyncImage(
                                model = "https://cdn.aichatzw.com/app_icons/6481bf744a4aa81624.png",
                                contentDescription = ""
                            )
                            Spacer(modifier = Modifier.height(24.dp))
                            Text(
                                text = "品牌起名及生成Slogan",
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
                                text = "为你起品牌名字并且生成Slogan",
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
        item{
            Text(text = "社交媒体")
        }
        item{
            Text(text = "短视频")
        }
        item{
            Text(text = "电商")
        }
        item{
            Text(text = "企业应用")
        }
        item{
            Text(text = "商业助手")
        }
        item{
            Text(text = "创作助手")
        }
    }
}
