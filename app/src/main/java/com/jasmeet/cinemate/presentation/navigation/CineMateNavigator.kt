package com.jasmeet.cinemate.presentation.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.DarkMode
import androidx.compose.material.icons.outlined.LightMode
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.NavOptions
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.google.firebase.auth.FirebaseAuth
import com.jasmeet.cinemate.data.themeSwitcher.DataStoreUtil
import com.jasmeet.cinemate.presentation.screens.DetailsScreen
import com.jasmeet.cinemate.presentation.screens.ID
import com.jasmeet.cinemate.presentation.screens.IS_MOVIE
import com.jasmeet.cinemate.presentation.screens.homeScreen.HomeScreen
import com.jasmeet.cinemate.presentation.screens.LoginScreen
import com.jasmeet.cinemate.presentation.screens.Screens
import com.jasmeet.cinemate.presentation.screens.SearchScreen
import com.jasmeet.cinemate.presentation.screens.SplashScreen
import com.jasmeet.cinemate.presentation.screens.VIDEO_ID
import com.jasmeet.cinemate.presentation.screens.VideoScreen
import kotlinx.coroutines.launch

@RequiresApi(Build.VERSION_CODES.R)
@Composable
fun CineMateNavigator(
    windowSize: WindowSizeClass,
    navController: NavHostController,
    paddingValues: PaddingValues,
    theme: Boolean,
    dataStoreUtil: DataStoreUtil
) {


    NavHost(
        navController = navController,
        startDestination = Screens.Splash.route,
        modifier = Modifier.fillMaxSize()
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
            HomeScreen(navController = navController, paddingValues = paddingValues)
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
            },
            exitTransition = {
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

            var themeSwitchState by rememberSaveable { mutableStateOf(theme) }

            val text = if (themeSwitchState) Color(0xff131313) else Color(0xffFFFFF0)


            Column(
                Modifier
                    .fillMaxSize()
                    .background(text),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {



                Text(text = "Profile", color = Color.White, fontSize = 22.sp)
                Button(onClick = {
                    val auth = FirebaseAuth.getInstance()
                    auth.signOut()
                    val navOptions = NavOptions.Builder()
                        .setPopUpTo(Screens.Home.route,inclusive = true)
                        .build()

                    navController.navigate(Screens.Login.route,navOptions)
                }) {
                    Text(text = "Logout")
                }
                ThemeSwitch(
                    themeSwitchState,
                    onCheckedChange = {
                        themeSwitchState = it
                    },
                    dataStoreUtil = dataStoreUtil
                )
            }
        }

        composable(
            route = Screens.Detail.route,
            enterTransition = {
                return@composable fadeIn(tween(1000))
            },
            exitTransition = {
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
            },
            arguments =  listOf(
                navArgument(ID){
                    type = NavType.StringType
                    defaultValue = ""
                    nullable = true
                },
                navArgument(IS_MOVIE.toString()){
                    type = NavType.BoolType
                }
            )
        ) {
            DetailsScreen(
                navController = navController,
                id = it.arguments?.getString(ID),
                isMovie = it.arguments?.getBoolean(IS_MOVIE.toString())
            )
        }

        composable(
            route = Screens.Video.route,
            enterTransition = {
                return@composable fadeIn(tween(1000))
            },
            exitTransition = {
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
            },
            arguments =  listOf(
                navArgument(VIDEO_ID){
                    type = NavType.StringType
                    defaultValue = ""
                    nullable = true
                },
            )
        ) {
            VideoScreen(
                navController = navController,
                id = it.arguments?.getString(VIDEO_ID),
            )
        }

    }
}

@Composable
fun ThemeSwitch(
    isCheck: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    dataStoreUtil: DataStoreUtil
) {
    val coroutineScope = rememberCoroutineScope()

    Switch(
        checked = isCheck,
        onCheckedChange = {
            onCheckedChange(it)
            coroutineScope.launch {
                dataStoreUtil.saveTheme(it)
            }
        },
        thumbContent = {
            if (isCheck) {
                Icon(
                    imageVector = Icons.Outlined.DarkMode,
                    contentDescription = null,
                    modifier = Modifier.size(SwitchDefaults.IconSize)
                )
            } else {
                Icon(
                    imageVector = Icons.Outlined.LightMode,
                    contentDescription = null,
                    modifier = Modifier.size(SwitchDefaults.IconSize)
                )

            }
        },
    )
}