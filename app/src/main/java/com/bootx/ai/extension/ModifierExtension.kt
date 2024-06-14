package com.bootx.ai.extension

import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

fun Modifier.customBorder(
    topColor: Color = Color.Transparent,
    bottomColor: Color = Color.Transparent,
    startColor: Color = Color.Transparent,
    endColor: Color = Color.Transparent,
    borderWidth: Dp = 1.dp
) = this.then(
    Modifier.drawWithContent {
        drawContent()
        val strokeWidth = borderWidth.toPx()
        val width = size.width
        val height = size.height
        drawLine(
            color = topColor,
            start = Offset(0f, 0f),
            end = Offset(width, 0f),
            strokeWidth = strokeWidth
        )
        drawLine(
            color = bottomColor,
            start = Offset(0f, height),
            end = Offset(width, height),
            strokeWidth = strokeWidth
        )
        drawLine(
            color = startColor,
            start = Offset(0f, 0f),
            end = Offset(0f, height),
            strokeWidth = strokeWidth
        )
        drawLine(
            color = endColor,
            start = Offset(width, 0f),
            end = Offset(width, height),
            strokeWidth = strokeWidth
        )
    }
)
