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
import com.jasmeet.cinemate.presentation.screens.LoginScreen
import com.jasmeet.cinemate.presentation.screens.Screens
import com.jasmeet.cinemate.presentation.screens.SplashScreen

@Composable
fun CineMateNavigator(windowSize: WindowSizeClass) {

    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Screens.Splash.route
    ) {

        composable(Screens.Splash.route){
         SplashScreen(navController = navController,)
        }
        composable(Screens.Login.route){
            LoginScreen(
                navController = navController,
                windowSize = windowSize
            )
        }
        composable(Screens.Home.route){
            Column(
                Modifier
                    .fillMaxSize()
                    .background(Color.Cyan)) {

            }
        }

    }
}