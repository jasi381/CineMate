package com.jasmeet.cinemate.presentation.appComponents

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun CategoriesCardItem(
    colors: List<Color>,
    xShimmer :Float,
    yShimmer :Float,
    gradientWidth: Float,
) {
    val brush = Brush.linearGradient(
        colors,
        start = Offset(xShimmer - gradientWidth,yShimmer - gradientWidth),
        end = Offset(xShimmer,yShimmer)
    )

    Row(
        modifier = Modifier
            .clip(RoundedCornerShape(10.dp))
            .height(LocalConfiguration.current.screenHeightDp.dp / 8)
            .fillMaxWidth()
            .padding(horizontal = 15.dp)
            .background(brush = brush, shape = RoundedCornerShape(10.dp))
    ) {}

}

@Composable
fun LoadingCategoriesListShimmer(
    imageHeight: Dp = 180.dp,
    padding: Dp = 16.dp,
    modifier: Modifier
) {

    BoxWithConstraints(modifier = modifier.fillMaxSize()) {
        val cardWidthPx = with(LocalDensity.current){(maxWidth - (padding * 2)).toPx()}
        val cardHeightPx = with(LocalDensity.current){(imageHeight - padding).toPx()}

        val gradientWidth :Float = (0.1f * cardHeightPx)

        val infiniteTransition = rememberInfiniteTransition(label = "")

        val xCardShimmer = infiniteTransition.animateFloat(
            initialValue = 0f,
            targetValue = (cardWidthPx + gradientWidth),
            animationSpec = infiniteRepeatable(
                animation = tween(
                    durationMillis = 1300,
                    easing = LinearEasing,
                    delayMillis = 300
                ),
                repeatMode = RepeatMode.Restart
            ),
            label = "",
        )

        val yCardShimmer = infiniteTransition.animateFloat(
            initialValue = 0f,
            targetValue = (cardHeightPx + gradientWidth),
            animationSpec = infiniteRepeatable(
                animation = tween(
                    durationMillis = 1300,
                    easing = LinearEasing,
                    delayMillis = 300
                ),
                repeatMode = RepeatMode.Restart
            ),
            label = "",
        )

        val colors  = listOf(
            Color(0xff333336).copy(alpha = 0.9f),
            Color(0xff333336).copy(alpha = 0.3f),
            Color(0xff333336).copy(alpha = 0.9f),
        )

        LazyColumn(verticalArrangement = Arrangement.spacedBy(18.dp), modifier = Modifier.fillMaxSize().padding(vertical = 10.dp)){
            items(8){
                CategoriesCardItem(
                    colors = colors,
                    xShimmer = xCardShimmer.value,
                    yShimmer = yCardShimmer.value,
                    gradientWidth = gradientWidth,
                )
            }
        }

    }

}