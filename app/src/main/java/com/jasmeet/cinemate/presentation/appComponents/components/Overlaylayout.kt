package com.jasmeet.cinemate.presentation.appComponents.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color

@Composable
fun OverLayLayout(
    modifier: Modifier = Modifier,
    colors :List<Color> = listOf(
        Color.Black.copy(alpha = 0.12f),
        Color.Black.copy(alpha = 0.22f),
        Color.Black.copy(alpha = 0.32f),
    )
) {
    Box(
        modifier =  modifier
            .background(
                brush = Brush.verticalGradient(
                    colors = colors
                )
            )
    )
}