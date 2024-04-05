package com.jasmeet.cinemate.presentation.appComponents

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.airbnb.lottie.RenderMode
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition

@Composable
fun LottieComponent(rawRes :Int ,modifier: Modifier) {

    val composition by rememberLottieComposition(
        LottieCompositionSpec
            .RawRes(rawRes)
    )

    val progress by animateLottieCompositionAsState(
        composition,
        iterations = LottieConstants.IterateForever,
        isPlaying = true,
        speed = 1.5f,
        restartOnPlay = false

    )

    @Suppress("DEPRECATION")
    LottieAnimation(
        composition = composition,
        progress = progress,
        modifier = modifier,
        renderMode = RenderMode.HARDWARE

    )
}