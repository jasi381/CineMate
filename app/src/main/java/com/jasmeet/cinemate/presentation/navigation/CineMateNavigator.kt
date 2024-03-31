package com.jasmeet.cinemate.presentation.navigation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.jasmeet.cinemate.presentation.SplashScreen

@Composable
fun CineMateNavigator(windowSize: WindowSizeClass) {

    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "splash"
    ) {
        composable("splash"){
            SplashScreen(
                navController = navController,
                windowSize = windowSize
            )
        }
        composable("home"){
            Column(Modifier.fillMaxSize().background(Color.Cyan)) {

            }
        }

    }
}