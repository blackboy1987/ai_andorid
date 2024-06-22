package com.bootx.ai.ui.screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.bootx.ai.ui.components.CountdownTimer
import com.bootx.ai.ui.navigation.Destinations
import com.bootx.ai.viewmodal.HomeModel
import com.bootx.ai.viewmodal.LoginModel
import com.bootx.ai.util.CommonUtils
import com.bootx.ai.util.SharedPreferencesUtils
import kotlinx.coroutines.launch
import kotlin.math.log


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginCodeScreen(
    navController: NavController,
    loginModel: LoginModel = viewModel()
) {
    val context = LocalContext.current
    var countDown by remember {
        mutableLongStateOf(SharedPreferencesUtils(context).getCodeCountDown().toLong())
    }
    var mobile by remember {
        mutableStateOf(SharedPreferencesUtils(context).get("mobile"))
    }

    var isEnd by remember {
        mutableStateOf(false)
    }
    var coroutineScope = rememberCoroutineScope()

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
                    text = "输入验证码",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )
            }
            item {
                Text(
                    text = "已发送4位验证码至 ${CommonUtils.hide(mobile,4,4)}",
                    fontSize = 12.sp,
                    modifier = Modifier.padding(bottom = 24.dp)
                )
            }
            item {
                Row(
                    modifier = Modifier.fillMaxWidth(0.8f),
                    horizontalArrangement = Arrangement.SpaceBetween,
                ) {
                    OTPTextField(onOTPComplete = { otp ->
                        coroutineScope.launch {
                            val login = loginModel.login(context, otp)
                            if(login.code==0){
                                // 登录成功,跳转到首页
                                navController.navigate(Destinations.MainFrame.route)
                            }
                        }
                    })
                }
            }
            item {
                if(!isEnd){
                    Spacer(modifier = Modifier.height(8.dp))
                    CountdownTimer(countDown = countDown) {
                        isEnd = true
                    }
                }else{
                    Text(
                        modifier = Modifier.padding(8.dp).clickable {
                            if(!loginModel.loading){
                                coroutineScope.launch {
                                    loginModel.sendCode(context,mobile)
                                    countDown = SharedPreferencesUtils(context).getCodeCountDown().toLong()
                                    isEnd = false
                                }
                            }
                        },
                        text = "重新获取",
                        fontSize = 12.sp,
                    )
                }
            }
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OTPTextField(
    otpLength: Int = 4,
    onOTPComplete: (String) -> Unit
) {
    var otpValues by remember { mutableStateOf(List(otpLength) { "" }) }
    val focusRequesters = remember { List(otpLength) { FocusRequester() } }

    for (index in 0 until otpLength) {
        OutlinedTextField(
            value = otpValues[index],
            onValueChange = { value ->
                if (value.length <= 1) {
                    otpValues = otpValues.toMutableList().apply {
                        this[index] = value
                    }
                    if (value.isNotEmpty() && index < otpLength - 1) {
                        focusRequesters[index + 1].requestFocus()
                    }
                }

                if (otpValues.all { it.isNotEmpty() }) {
                    onOTPComplete(otpValues.joinToString(""))
                }
            },
            modifier = Modifier
                .width(60.dp)
                .height(60.dp)
                .focusRequester(focusRequesters[index])
                .onFocusChanged {
                    if (it.isFocused && otpValues[index].isNotEmpty()) {
                        otpValues = otpValues
                            .toMutableList()
                            .apply {
                                this[index] = ""
                            }
                    }
                },
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = Color.Blue,
                unfocusedBorderColor = Color.Gray,
                cursorColor = Color.Blue
            )
        )
    }
}
