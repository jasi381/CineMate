package com.jasmeet.cinemate.presentation.navigation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
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
import com.jasmeet.cinemate.presentation.screens.HomeScreen
import com.jasmeet.cinemate.presentation.screens.LoginScreen
import com.jasmeet.cinemate.presentation.screens.Screens
import com.jasmeet.cinemate.presentation.screens.SearchScreen
import com.jasmeet.cinemate.presentation.screens.SplashScreen

@Composable
fun CineMateNavigator(
    windowSize: WindowSizeClass,
    navController: NavHostController,
    paddingValues: PaddingValues
) {


    NavHost(
        navController = navController,
        startDestination = Screens.Splash.route,
        modifier = Modifier.background(Color(0xff131313)).fillMaxSize().padding(bottom = paddingValues.calculateBottomPadding())
    ) {

        composable(
            route = Screens.Splash.route,
            enterTransition = {
                return@composable fadeIn(tween(1000))
            }, exitTransition = {
                return@composable slideOutOfContainer(
                    AnimatedContentTransitionScope.SlideDirection.Start, tween(700)
                )
            }, popEnterTransition = {
                return@composable fadeIn(tween(1000))
            },
            popExitTransition = {
                return@composable slideOutOfContainer(
                    AnimatedContentTransitionScope.SlideDirection.Start, tween(700)
                )
            }
        ) {
            SplashScreen(navController = navController)
        }
        composable(
            route = Screens.Login.route,
            enterTransition = {
                return@composable fadeIn(tween(1000))
            }, exitTransition = {
                return@composable slideOutOfContainer(
                    AnimatedContentTransitionScope.SlideDirection.Start, tween(700)
                )
            }, popEnterTransition = {
                return@composable fadeIn(tween(1000))
            },
            popExitTransition = {
                return@composable slideOutOfContainer(
                    AnimatedContentTransitionScope.SlideDirection.Start, tween(700)
                )
            }
        ) {
            LoginScreen(
                navController = navController,
                windowSize = windowSize
            )
        }
        composable(
            route = Screens.Home.route,
            enterTransition = {
                return@composable fadeIn(tween(1000))
            }, exitTransition = {
                return@composable slideOutOfContainer(
                    AnimatedContentTransitionScope.SlideDirection.Start, tween(700)
                )
            }, popEnterTransition = {
                return@composable fadeIn(tween(1000))
            },
            popExitTransition = {
                return@composable slideOutOfContainer(
                    AnimatedContentTransitionScope.SlideDirection.Start, tween(700)
                )
            }
        ) {
            HomeScreen()
        }

        composable(
            route = Screens.Search.route,
            enterTransition = {
                return@composable fadeIn(tween(1000))
            }, exitTransition = {
                return@composable slideOutOfContainer(
                    AnimatedContentTransitionScope.SlideDirection.Start, tween(700)
                )
            }, popEnterTransition = {
                return@composable fadeIn(tween(1000))
            },
            popExitTransition = {
                return@composable slideOutOfContainer(
                    AnimatedContentTransitionScope.SlideDirection.Start, tween(700)
                )
            }
        ) {
            SearchScreen(navController = navController)
        }
        composable(
            route = Screens.Wishlist.route,
            enterTransition = {
                return@composable fadeIn(tween(1000))
            }, exitTransition = {
                return@composable slideOutOfContainer(
                    AnimatedContentTransitionScope.SlideDirection.Start, tween(700)
                )
            }, popEnterTransition = {
                return@composable fadeIn(tween(1000))
            },
            popExitTransition = {
                return@composable slideOutOfContainer(
                    AnimatedContentTransitionScope.SlideDirection.Start, tween(700)
                )
            }
        ) {
            Column(
                Modifier
                    .fillMaxSize()
                    .background(Color(0xff131313)),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(text = "Wishlist", color = Color.White, fontSize = 22.sp)
            }
        }

        composable(
            route = Screens.Profile.route,
            enterTransition = {
                return@composable fadeIn(tween(1000))
            }, exitTransition = {
                return@composable slideOutOfContainer(
                    AnimatedContentTransitionScope.SlideDirection.Start, tween(700)
                )
            }, popEnterTransition = {
                return@composable fadeIn(tween(1000))
            },
            popExitTransition = {
                return@composable slideOutOfContainer(
                    AnimatedContentTransitionScope.SlideDirection.Start, tween(700)
                )
            }
        ) {
            Column(
                Modifier
                    .fillMaxSize()
                    .background(Color(0xff131313)),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(text = "Profile", color = Color.White, fontSize = 22.sp)
            }
        }

    }
}