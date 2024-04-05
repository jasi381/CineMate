package com.jasmeet.cinemate.presentation.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.skydoves.landscapist.animation.circular.CircularRevealPlugin
import com.skydoves.landscapist.coil.CoilImage
import com.skydoves.landscapist.components.rememberImageComponent
import com.skydoves.landscapist.placeholder.shimmer.Shimmer
import com.skydoves.landscapist.placeholder.shimmer.ShimmerPlugin

@Composable
fun SearchScreen(modifier: Modifier = Modifier, navController: NavHostController) {
    Column(
        Modifier
            .fillMaxSize()
            .background(Color(0xff131313)),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Search", color = Color.White, fontSize = 22.sp)

        CoilImage(
            imageModel = { "https://image.tmdb.org/t/p/w780/geCRueV3ElhRTr0xtJuEWJt6dJ1.jpg" },
            modifier = Modifier.aspectRatio(0.8f),
            component = rememberImageComponent {
                +ShimmerPlugin(
                    Shimmer.Resonate(
                        baseColor = if (isSystemInDarkTheme()) {
                            Color.Black
                        } else {
                            Color.White
                        },
                        highlightColor = Color.LightGray,
                    ),
                )
                +CircularRevealPlugin(
                    duration = 750
                )
            },
        )
    }



}