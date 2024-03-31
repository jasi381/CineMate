package com.jasmeet.cinemate.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jasmeet.cinemate.R
import com.jasmeet.cinemate.presentation.navigation.CineMateNavigator
import com.jasmeet.cinemate.presentation.theme.Background
import com.jasmeet.cinemate.presentation.theme.CineMateTheme
import com.jasmeet.cinemate.presentation.theme.GradientColor1
import com.jasmeet.cinemate.presentation.theme.GradientColor2
import com.jasmeet.cinemate.presentation.theme.GradientColor3
import com.jasmeet.cinemate.presentation.theme.GradientColor4
import com.jasmeet.cinemate.presentation.theme.GradientColor5

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CineMateTheme {
                val windowSize = calculateWindowSizeClass(activity = this)
                CineMateNavigator(windowSize = windowSize)
            }
        }
    }

}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    CineMateTheme {
        Content()
    }
}


@Composable
fun Content(modifier: Modifier = Modifier) {

    val sweepAngle = 135f
    val animationDuration = 1500
    val plusAnimationDuration = 300
    val animationDelay = 100
    var animationPlayed by remember {
        mutableStateOf(false)
    }
    var plusAnimationPlayed by remember {
        mutableStateOf(false)
    }

    val currentPercent = animateFloatAsState(
        targetValue = if (animationPlayed) sweepAngle else 0f,
        animationSpec = tween(
            durationMillis = animationDuration,
            delayMillis = animationDelay,
            easing = FastOutLinearInEasing
        ),
        finishedListener = {
            plusAnimationPlayed = true
        }, label = ""
    )

    val scalePercent = animateFloatAsState(
        targetValue = if (plusAnimationPlayed) 0.8f else 0f,
        animationSpec = tween(
            durationMillis = animationDuration,
            delayMillis = animationDelay,
            easing = FastOutLinearInEasing
        ), label = ""
    )

    val rotationAngle by animateFloatAsState(
        targetValue = if (animationPlayed) 360f else 0f,
        animationSpec = tween(
            durationMillis = animationDuration,
            delayMillis = animationDelay,
            easing = FastOutLinearInEasing
        ) ,label = ""
    )

    val pulseEffect by animateFloatAsState(
        targetValue = if (animationPlayed) 0.9f else 0.7f,
        animationSpec = tween(
            durationMillis = animationDuration,
            delayMillis = animationDelay,
            easing = FastOutLinearInEasing
        ) ,label = ""
    )

    LaunchedEffect(key1 = true) {
        animationPlayed = true
    }


    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Background),
        contentAlignment = Alignment.Center
    ) {

        Box(modifier = Modifier
            .size(200.dp)
            .drawBehind {
                drawArc(
                    brush = Brush.linearGradient(
                        0f to GradientColor1,
                        0.2f to GradientColor2,
                        0.35f to GradientColor3,
                        0.45f to GradientColor4,
                        0.75f to GradientColor5,
                    ),
                    startAngle = -152f,
                    sweepAngle = currentPercent.value,
                    useCenter = false,
                    style = Stroke(width = 10f, cap = StrokeCap.Round)
                )
            }) { }
        Row {

            Text(
                text = stringResource(id = R.string.app_name),
                color = Color.White,
                fontFamily = FontFamily.Monospace,
                modifier = Modifier
                    .align(Alignment.CenterVertically)
                    .scale(pulseEffect)
                    .animateContentSize(
                        animationSpec = tween(
                            durationMillis = plusAnimationDuration,
                            easing = LinearEasing,
                            delayMillis = 0
                        )
                    ),
                fontSize = animateDpAsState(
                    if (animationPlayed) 40.dp else 32.dp, label = ""
                ).value.value.sp

            )
            Image(
                painter = painterResource(id = R.drawable.ic_plus),
                contentDescription = "Plus Image",
                colorFilter = ColorFilter.tint(Color.White),
                modifier = Modifier
                    .size(46.dp)
                    .align(Alignment.CenterVertically)
                    .scale(scalePercent.value)
                    .rotate(rotationAngle)
            )
        }

    }
}