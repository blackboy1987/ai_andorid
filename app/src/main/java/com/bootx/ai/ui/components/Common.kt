package com.bootx.ai.ui.components

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.media3.exoplayer.ExoPlayer
import kotlinx.coroutines.delay
import java.util.Date

@Composable
fun MyInput(
    value: String = "",
    minLines: Int = 1,
    maxLines: Int = 1,
    onValueChange: (value: String) -> Unit
) {
    var content by remember { mutableStateOf(value) }
    BasicTextField(
        minLines = minLines,
        maxLines = maxLines,
        value = content,
        onValueChange = {
            content = it
            onValueChange(it)
        },
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 4.dp)
            .border(
                BorderStroke(1.dp, Color.Blue), // 设置边框颜色
                shape = MaterialTheme.shapes.small // 使用默认形状
            )
            .padding(8.dp), // 内边距
        decorationBox = { innerTextField ->
            Box(modifier = Modifier.padding(4.dp)) {
                innerTextField()
            }
        }
    )
}

@Composable
fun MySelect(options: List<String>, selectIndex: Int = -1,onClick: (index:Int) -> Unit) {
    var selected by remember { mutableIntStateOf(selectIndex) }
    options.forEachIndexed { index, option ->
        Row(
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            CustomRadioButton(
                selected = selected == index,
                onClick = {
                    selected = index
                    onClick(index)
                },
                size = 16.dp // 设置单选按钮的大小
            )
            Spacer(modifier = Modifier.width(4.dp))
            Text(
                text = option,
                fontSize = MaterialTheme.typography.labelLarge.fontSize,
            )
        }
    }
}

@Composable
fun MyMultiSelect(options: List<String>, selectIndex: List<Int>) {
    options.forEachIndexed() { index, option ->
        Row(
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            CustomCheckbox(
                checked = selectIndex.indexOf(index) >= 0,
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
                    size = Size(size.toPx(), size.toPx())
                )
            } else {
                drawRoundRect(
                    color = color,
                    size = Size(size.toPx(), size.toPx()),
                    style = Stroke(width = 2.dp.toPx())
                )
            }
        }
    }
}

@SuppressLint("DefaultLocale")
@Composable
fun CountdownTimer(
    countDown: Long,
    onFinish: () -> Unit
) {
    var timeLeft by remember { mutableLongStateOf(countDown-Date().time) }
    LaunchedEffect(key1 = timeLeft) {
        if (timeLeft > 0) {
            delay(1000L)
            timeLeft -= 1000L
        } else {
            onFinish()
        }
    }
    val seconds = timeLeft / 1000
    Text(text = "${String.format("%02d", seconds)} 秒重新获取",fontSize = 12.sp,)
}