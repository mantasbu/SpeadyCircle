package com.kotlisoft.speedycircle.ui

import androidx.compose.foundation.Canvas
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import com.kotlisoft.speedycircle.Constants.RADIUS

@Composable
fun Circle(
    centerX: Float,
    centerY: Float
) {
    Canvas(modifier = Modifier) {
        drawCircle(
            color = Color.Red,
            radius = RADIUS.toFloat(),
            center = Offset(centerX, centerY)
        )
    }
}