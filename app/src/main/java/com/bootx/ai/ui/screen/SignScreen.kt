package com.bootx.ai.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Place
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.bootx.ai.ui.components.ad.RequestExpressDrawFeedAd
import com.bootx.ai.ui.components.ad.requestFullScreenVideoAd
import com.bootx.ai.ui.components.ad.requestInteractionAd
import com.bootx.ai.ui.viewmodal.HomeModel
import com.bootx.ai.util.CommonUtils
import java.util.Date


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SignScreen(
    navController: NavController,
    homeModel: HomeModel = viewModel()
) {
    val context = LocalContext.current
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(end = 56.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text("每日签到")
                    }
                }, navigationIcon = {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.KeyboardArrowLeft,
                        contentDescription = ""
                    )
                })
        }
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
                .padding(horizontal = 16.dp)
        ) {
            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Row(
                        horizontalArrangement = Arrangement.Start,
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        val annotatedText = buildAnnotatedString {
                            withStyle(
                                style = SpanStyle(
                                    color = Color.Red, fontSize = 10.sp,
                                    fontWeight = FontWeight.Bold,
                                )
                            ) {
                                append("|  ")
                            }
                            withStyle(
                                style = SpanStyle(
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.Bold,
                                ),
                            ) {
                                append("做任务赚次数")
                            }
                        }
                        Text(
                            text = annotatedText,
                        )
                    }
                    Text(text = "每天都可以做鸭", fontSize = 10.sp)
                }
            }
            item {
                ListItem(
                    headlineContent = {
                        Text(text = "广告1")
                    },
                    supportingContent = {
                        Text(text = "+10积分")
                    },
                    leadingContent = {
                        Icon(imageVector = Icons.Default.Place, contentDescription = "")
                    },
                    trailingContent = {
                        Button(onClick = {
                            CommonUtils.getRewardVideoAd(context) { status ->
                                if (status == 1) {
                                    CommonUtils.toast(context, "已领取")
                                }
                            }
                        }) {
                            Text(text = "去领取")
                        }
                    }
                )
                ListItem(
                    headlineContent = {
                        Text(text = "广告2")
                    },
                    supportingContent = {
                        Text(text = "+10积分")
                    },
                    leadingContent = {
                        Icon(imageVector = Icons.Default.Place, contentDescription = "")
                    },
                    trailingContent = {
                        Button(onClick = {
                            val start = Date().time
                            var isClick = false
                            requestInteractionAd(context) { status ->
                                if (status == "onAdDismiss") {
                                    if (isClick) {
                                        if (Date().time - start > 1000 * 10) {
                                            CommonUtils.toast(context, "已领取")
                                        } else {
                                            CommonUtils.toast(context, "广告浏览时间需要达到10秒")
                                        }
                                    } else {
                                        CommonUtils.toast(context, "广告未点击")
                                    }


                                } else if (status == "onAdClick") {
                                    isClick = true
                                }
                            }
                        }) {
                            Text(text = "去领取")
                        }
                    }
                )
                ListItem(
                    headlineContent = {
                        Text(text = "广告3")
                    },
                    supportingContent = {
                        Text(text = "+10积分")
                    },
                    leadingContent = {
                        Icon(imageVector = Icons.Default.Place, contentDescription = "")
                    },
                    trailingContent = {
                        Button(onClick = {
                            val start = Date().time
                            var isClick = false
                            requestFullScreenVideoAd(context) { status ->
                                if (status == "onAdDismiss") {
                                    if (isClick) {
                                        if (Date().time - start > 1000 * 10) {
                                            CommonUtils.toast(context, "已领取")
                                        } else {
                                            CommonUtils.toast(context, "广告浏览时间需要达到10秒")
                                        }
                                    } else {
                                        CommonUtils.toast(context, "广告未点击")
                                    }


                                } else if (status == "onAdClick") {
                                    isClick = true
                                }
                            }
                        }) {
                            Text(text = "去领取")
                        }
                    }
                )
                ListItem(
                    headlineContent = {
                        Text(text = "广告4")
                    },
                    supportingContent = {
                        Text(text = "+10积分")
                    },
                    leadingContent = {
                        Icon(imageVector = Icons.Default.Place, contentDescription = "")
                    },
                    trailingContent = {
                        Button(onClick = {

                        }) {
                            Text(text = "去领取")
                        }
                    }
                )
            }
        }
    }

    BasicAlertDialog(
        onDismissRequest = { /*TODO*/ },
        modifier = Modifier
            .fillMaxSize(),
    ) {
        RequestExpressDrawFeedAd(context) {

        }
        Row(
            horizontalArrangement = Arrangement.Center
        ) {
            IconButton(onClick = { }) {
                Icon(imageVector = Icons.Default.Close, contentDescription = "", tint = Color.Red)
            }
        }
    }
}
