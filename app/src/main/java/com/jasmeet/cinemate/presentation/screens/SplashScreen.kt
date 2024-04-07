package com.jasmeet.cinemate.presentation.screens

import androidx.compose.animation.animateColor
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavOptions
import com.jasmeet.cinemate.R
import com.jasmeet.cinemate.presentation.appComponents.LottieComponent
import com.jasmeet.cinemate.presentation.theme.libreBaskerville
import com.jasmeet.cinemate.presentation.viewModel.SplashViewModel
import kotlinx.coroutines.delay


@Composable
fun SplashScreen(
    navController: NavHostController,
    splashViewModel: SplashViewModel = hiltViewModel()
) {

    val isUserLoggedIn = splashViewModel.isUserLoggedIn

    val transition = rememberInfiniteTransition(label = "")
    val color by transition.animateColor(
        initialValue = Color.White,
        targetValue = Color(0xffE50914),
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 1000),
            repeatMode = RepeatMode.Reverse
        ), label = ""
    )
    val fontSize by transition.animateFloat(
        initialValue = 30f,
        targetValue = 40f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 1000),
            repeatMode = RepeatMode.Reverse
        ), label = ""
    )

    val alpha by transition.animateFloat(
        initialValue = 0.5f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 1000),
            repeatMode = RepeatMode.Reverse
        ), label = ""
    )

    val scaleX by transition.animateFloat(
        initialValue = 1f,
        targetValue = 1.2f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 1000),
            repeatMode = RepeatMode.Reverse
        ), label = ""
    )

    val scaleY by transition.animateFloat(
        initialValue = 1f,
        targetValue = 1.2f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 1000),
            repeatMode = RepeatMode.Reverse
        ), label = ""
    )

    LaunchedEffect(true) {
        splashViewModel.checkForActiveSession()
        delay(3200)

        if (isUserLoggedIn.value == true) {
            val navOptions = NavOptions.Builder()
                .setPopUpTo(Screens.Splash.route, inclusive = true)
                .build()
            navController.navigate(Screens.Home.route, navOptions)

        }else{
            val navOptions = NavOptions.Builder()
                .setPopUpTo(Screens.Splash.route, inclusive = true)
                .build()
            navController.navigate(Screens.Login.route, navOptions)

        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xff131313)),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        LottieComponent(
            rawRes = R.raw.splash,
            modifier = Modifier.fillMaxSize(0.5f)
        )

        Text(
            text = stringResource(id = R.string.app_name),
            color = color,
            fontFamily = libreBaskerville,
            modifier = Modifier
                .offset(y = (-50).dp)
                .fillMaxWidth()
                .alpha(alpha)
                .graphicsLayer(
                    scaleX = scaleX,
                    scaleY = scaleY
                )
                .animateContentSize(),
            fontSize = fontSize.sp,
            textAlign = TextAlign.Center
        )
    }
}
