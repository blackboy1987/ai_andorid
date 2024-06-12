package com.bootx.ai.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.bootx.ai.ui.viewmodal.HomeModel
import com.bootx.ai.util.CommonUtils


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(
    navController: NavController,
    homeModel: HomeModel = viewModel()
) {
    var phone by remember {
        mutableStateOf("")
    }

    Scaffold(
        topBar = {
            TopAppBar(title = { }, navigationIcon = {
                Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "")
            })
        }
    ) {
        LazyColumn(
            modifier = Modifier
                .padding(it)
                .fillMaxSize()
                .padding(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top,
        ) {
            item {
                Text(
                    text = "手机验证码登录",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )
            }
            item {
                Text(
                    text = "* 新用户登录后自动创建账号 *",
                    fontSize = 12.sp,
                    modifier = Modifier.padding(bottom = 24.dp)
                )
            }
            item {
                OutlinedTextField(
                    leadingIcon = {
                        Icon(imageVector = Icons.Default.Phone, contentDescription = "")
                    },
                    shape = RoundedCornerShape(32.dp),
                    value = phone,
                    onValueChange = { value ->
                        if(value.length<=11){
                            phone = value
                        }

                    },
                    modifier = Modifier
                        .padding(bottom = 24.dp)
                        .fillMaxWidth(0.8f),
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Phone,
                    ),
                    placeholder = { Text(text = "输入手机号码") },
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor = if (phone.isNotEmpty() && CommonUtils.validatePhone(phone)) Color.Blue else Color.Red,
                        unfocusedBorderColor = if (phone.isNotEmpty() && CommonUtils.validatePhone(phone)) Color.Gray else Color.Red,
                        focusedLabelColor = if (phone.isNotEmpty() && CommonUtils.validatePhone(phone)) Color.Blue else Color.Red,
                        cursorColor = Color.Blue
                    )
                )
            }
            item {
                OutlinedButton(
                    enabled = CommonUtils.validatePhone(phone),
                    shape = RoundedCornerShape(32.dp),
                    onClick = {  },
                    modifier = Modifier
                        .height(72.dp)
                        .padding(bottom = 24.dp)
                        .fillMaxWidth(0.8f),
                ) {
                    Text(text = "获取验证码")
                }
            }
            item {
                Row(
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Checkbox(checked = true, onCheckedChange = {})
                    Text(text = "我已阅读并同意AI智能助手", fontSize = 12.sp)
                    Text(text = "《用户协议》", fontSize = 12.sp)
                    Text(text = "《隐私协议》", fontSize = 12.sp)
                }
            }
        }
    }
}
