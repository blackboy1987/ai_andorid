package com.bootx.ai.ui.screen

import android.provider.MediaStore.Audio.Radio
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.bootx.ai.ui.viewmodal.AppModel
import com.bootx.ai.ui.viewmodal.HomeModel
import com.bootx.ai.ui.viewmodal.WriteModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WriteScreen(
    navController: NavController,
    writeModel: WriteModel = viewModel()
) {
    val context = LocalContext.current
    LaunchedEffect(Unit) {
        writeModel.config(context, 1)
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
                IconButton(onClick = { /*TODO*/ }) {
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
            items(writeModel.appEntity.formDataList) {
                Text(text = it.label)
                if (it.formType == "input") {
                    TextField(
                        modifier = Modifier.fillMaxWidth(),
                        value = "",
                        onValueChange = {},
                        placeholder = { Text(text = it.placeholder) })
                } else if (it.formType == "textarea") {
                    TextField(
                        modifier = Modifier.fillMaxWidth(),
                        value = "",
                        onValueChange = {},
                        minLines = 4,
                        maxLines = 4,
                        placeholder = { Text(text = it.placeholder) })
                } else if (it.formType == "keywords") {
                    TextField(
                        modifier = Modifier.fillMaxWidth(),
                        value = "",
                        onValueChange = {},
                        placeholder = { Text(text = it.placeholder) })
                } else if (it.formType == "select") {
                    it.options.forEach { option ->
                        Row(
                            horizontalArrangement = Arrangement.Start,
                            verticalAlignment = Alignment.CenterVertically,
                        ) {
                            CustomRadioButton(
                                selected = false,
                                onClick = {  },
                                size = 16.dp // 设置单选按钮的大小
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = option,
                                fontSize = MaterialTheme.typography.labelLarge.fontSize,
                            )
                        }
                    }
                } else if (it.formType == "multiSelect") {
                    it.options.forEach { option ->
                        Row(
                            horizontalArrangement = Arrangement.Start,
                            verticalAlignment = Alignment.CenterVertically,
                        ) {
                            CustomCheckbox(
                                checked = false,
                                onCheckedChange = {},
                                size = 16.dp
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = option,
                                fontSize = MaterialTheme.typography.labelLarge.fontSize,
                            )
                        }
                    }
                }
            }
        }
    }
}
@Composable
fun CustomRadioButton(
    selected: Boolean,
    onClick: () -> Unit,
    size: Dp
) {
    Box(
        modifier = Modifier
            .size(size)
            .padding(4.dp)
            .clickable(onClick = onClick)
    ) {
        val color = if (selected) MaterialTheme.colorScheme.primary else Color.Gray

        Canvas(modifier = Modifier.matchParentSize()) {
            drawCustomRadioButton(selected, color, size.toPx())
        }
    }
}

fun DrawScope.drawCustomRadioButton(selected: Boolean, color: Color, size: Float) {
    val strokeWidth = size * 0.1f

    if (selected) {
        drawCircle(
            color = color,
            radius = size / 2,
            style = Stroke(width = strokeWidth)
        )
        drawCircle(
            color = color,
            radius = size / 4
        )
    } else {
        drawCircle(
            color = color,
            radius = size / 2,
            style = Stroke(width = strokeWidth)
        )
    }
}

@Composable
fun CustomCheckbox(
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    size: Dp
) {
    val color = if (checked) MaterialTheme.colorScheme.primary else Color.Gray

    Box(
        modifier = Modifier
            .size(size)
            .clickable { onCheckedChange(!checked) }
    ) {
        Canvas(modifier = Modifier.matchParentSize()) {
            if (checked) {
                drawRoundRect(
                    color = color,
                    size = androidx.compose.ui.geometry.Size(size.toPx(), size.toPx())
                )
            } else {
                drawRoundRect(
                    color = color,
                    size = androidx.compose.ui.geometry.Size(size.toPx(), size.toPx()),
                    style = Stroke(width = 2.dp.toPx())
                )
            }
        }
    }
}