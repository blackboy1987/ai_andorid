package com.bootx.ai.ui.screen

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.bootx.ai.config.Config
import com.bootx.ai.entity.AppEntity
import com.bootx.ai.ui.components.MyInput
import com.bootx.ai.ui.components.MyMultiSelect
import com.bootx.ai.ui.components.MySelect
import com.bootx.ai.ui.viewmodal.WriteModel
import com.bootx.ai.util.SharedPreferencesUtils
import kotlinx.coroutines.launch
import java.util.UUID


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WriteScreen(
    navController: NavController,
    id: String,
    writeModel: WriteModel = viewModel(),
) {
    val context = LocalContext.current
    var appEntity by remember {
        mutableStateOf(AppEntity())
    }
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        try {
            writeModel.config(context, id.toInt())
            appEntity = writeModel.appEntity
        }catch (_:Exception){

        }
    }

    Scaffold(
        topBar = {
            TopAppBar(title = {
                Text(
                    text = writeModel.appEntity.title,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }, navigationIcon = {
                IconButton(onClick = {
                    navController.popBackStack()
                }) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.KeyboardArrowLeft,
                        contentDescription = ""
                    )
                }
            })
        }
    ) {
        LazyColumn(
            modifier = Modifier
                .padding(it)
                .fillMaxSize()
                .padding(8.dp),
        ) {
            items(appEntity.formDataList) {
                Text(text = it.label)
                if (it.formType == "input") {
                    MyInput(value= it.value, onValueChange = { value: String ->
                        coroutineScope.launch {
                            writeModel.changeValue(label = it.label,value)
                        }
                    })
                } else if (it.formType == "textarea") {
                    MyInput(minLines = 4, maxLines = 4, value= it.value, onValueChange = { value: String ->
                        coroutineScope.launch {
                            writeModel.changeValue(label = it.label,value)
                        }
                    })
                } else if (it.formType == "keywords") {
                    MyInput(value= it.value, onValueChange = { value: String ->
                        coroutineScope.launch {
                            writeModel.changeValue(label = it.label,value)
                        }
                    })
                } else if (it.formType == "select") {
                    Column(
                        modifier = Modifier.padding(8.dp)
                    ) {
                        MySelect(options = it.options,it.radioIndex, onClick = {value: Int->
                            coroutineScope.launch {
                                writeModel.changeValue(label = it.label,"$value")
                            }
                        })
                    }
                } else if (it.formType == "multiSelect") {
                    Column(
                        modifier = Modifier.padding(8.dp)
                    ) {
                        MyMultiSelect(options = it.options, selectIndex = listOf(1,2))
                    }

                }
            }
            item{
                Button(
                    enabled = !writeModel.loading,
                    onClick = {
                    coroutineScope.launch {
                        writeModel.submit(context)
                    }
                }, modifier = Modifier.fillMaxWidth()) {
                    Text(text = "创作")
                }
            }
        }
    }
}
