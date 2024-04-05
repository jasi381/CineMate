package com.jasmeet.cinemate.presentation.navigation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.jasmeet.cinemate.presentation.screens.LoginScreen
import com.jasmeet.cinemate.presentation.screens.Screens
import com.jasmeet.cinemate.presentation.screens.SplashScreen

@Composable
fun CineMateNavigator(
    windowSize: WindowSizeClass,
    navController : NavHostController
) {


    NavHost(
        navController = navController,
        startDestination = Screens.Splash.route
    ) {

        composable(Screens.Splash.route){
            SplashScreen(navController = navController)
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
                    .background(Color(0xff131313)),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ){
                Text(text = "Home", color = Color.White, fontSize = 22.sp)
            }
        }

        composable(Screens.Search.route){
            Column(
                Modifier
                    .fillMaxSize()
                    .background(Color(0xff131313)),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ){
                Text(text = "Search", color = Color.White, fontSize = 22.sp)
            }
        }
        composable(Screens.Wishlist.route){
            Column(
                Modifier
                    .fillMaxSize()
                    .background(Color(0xff131313)),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ){
                Text(text = "Wishlist", color = Color.White, fontSize = 22.sp)
            }
        }

        composable(Screens.Profile.route){
            Column(
                Modifier
                    .fillMaxSize()
                    .background(Color(0xff131313)),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ){
                Text(text = "Profile", color = Color.White, fontSize = 22.sp)
            }
        }

    }
}