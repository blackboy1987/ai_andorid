package com.bootx.ai.ui.screen

import android.app.Activity
import android.content.Context
import android.view.inputmethod.InputMethodManager
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.bootx.ai.ui.viewmodal.HomeModel
import com.bootx.ai.util.CommonUtils

@Composable
fun ChatScreen(
    navController: NavController,
    homeModel: HomeModel = viewModel()
) {
    val messages by homeModel.messages.collectAsState()
    var content by remember {
        mutableStateOf("")
    }
    val context = LocalContext.current
    val focusManager = LocalFocusManager.current
    Column{
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .weight(1.0f)
                .padding(8.dp),
        ) {
            items(10) { index ->
                if (index % 2 == 0) {
                    OtherContent()
                } else {
                    MyContent()
                }
            }
        }
        Row {
            BasicTextField(
                minLines = 1,
                value = content,
                onValueChange = { value ->
                    content = value
                },
                modifier = Modifier
                    .weight(1.0f)
                    .padding(horizontal = 8.dp)
                    .border(
                        BorderStroke(1.dp, Color.Blue), // 设置边框颜色
                        shape = MaterialTheme.shapes.small // 使用默认形状
                    )
                    .padding(8.dp), // 内边距
                decorationBox = { innerTextField ->
                    Box(modifier = Modifier.padding(8.dp)) {
                        innerTextField()
                    }
                }
            )
            IconButton(onClick = {
                if(content.isNotBlank()){
                    homeModel.connect(content)
                    content = ""
                }else{
                    CommonUtils.toast(context,"请输入内容")
                }

                hideKeyboard(context)
                focusManager.clearFocus()
            }) {
                Icon(imageVector = Icons.AutoMirrored.Filled.Send, contentDescription = "")
            }
        }
        Spacer(modifier = Modifier.height(8.dp))
    }
}

fun hideKeyboard(context: Context) {
    val inputMethodManager =
        context.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    val currentFocus = (context as? Activity)?.currentFocus
    currentFocus?.let {
        inputMethodManager.hideSoftInputFromWindow(it.windowToken, 0)
    }
}

@Composable
fun OtherContent() {
    Row(
        modifier = Modifier.padding(bottom = 8.dp)
    ) {
        AsyncImage(
            contentScale = ContentScale.FillBounds,
            modifier = Modifier
                .clip(CircleShape)
                .height(32.dp)
                .width(32.dp),
            model = "https://cdn.aichatzw.com/home/assets/logo-a42cf706.png",
            contentDescription = ""
        )
        Spacer(modifier = Modifier.width(4.dp))
        Card(
            modifier = Modifier
                .weight(1.0f),
            colors = CardDefaults.cardColors(
                containerColor = Color(0xff3c3e64)
            ),
        ) {
            Text(
                modifier = Modifier.padding(8.dp),
                color = Color.White,
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
                text = "我是中文我是中文我是中文我是中文我是中文我是中文我是中文我是中文我是中文我是中文我是中文我是中文我是中文我是中文我是中文我是中文我是中文我是中文我是中文我是中文我是中文我是中文我是中文我是中文我是中文我是中文我是中文我是中文我是中文我是中文我是中文我是中文我是中文我是中文我是中文"
            )
        }
        Spacer(modifier = Modifier.width(40.dp))
    }
}

@Composable
fun MyContent() {
    Row(
        modifier = Modifier.padding(bottom = 8.dp)
    ) {
        Spacer(modifier = Modifier.width(40.dp))
        Card(
            modifier = Modifier
                .weight(1.0f),
            colors = CardDefaults.cardColors(
                containerColor = Color(0xff7d86ff)
            ),
        ) {
            Text(
                modifier = Modifier.padding(8.dp),
                color = Color.White,
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
                text = "sadfasfassadfasfassadfasfassadfasfassadfasfassadfasfassadfasfassadfasfassadfasfassadfasfassadfasfassadfasfassadfasfassadfasfassadfasfassadfasfassadfasfassadfasfassadfasfassadfasfassadfasfassadfasfassadfasfassadfasfassadfasfassadfasfassadfasfassadfasfassadfasfassadfasfassadfasfassadfasfassadfasfassadfasfassadfasfassadfasfassadfasfassadfasfassadfasfassadfasfassadfasfassadfasfassadfasfassadfasfassadfasfassadfasfassadfasfassadfasfassadfasfassadfasfas"
            )
        }
        Spacer(modifier = Modifier.width(4.dp))
        AsyncImage(
            contentScale = ContentScale.FillBounds,
            modifier = Modifier
                .clip(CircleShape)
                .height(32.dp)
                .width(32.dp),
            model = "https://cdn.aichatzw.com/home/assets/logo-a42cf706.png",
            contentDescription = ""
        )
    }
}