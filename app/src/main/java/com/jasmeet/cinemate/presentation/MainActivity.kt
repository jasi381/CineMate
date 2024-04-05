package com.jasmeet.cinemate.presentation

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.Scaffold
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.jasmeet.cinemate.data.data.BottomNavigationItem
import com.jasmeet.cinemate.presentation.appComponents.BottomBar
import com.jasmeet.cinemate.presentation.navigation.CineMateNavigator
import com.jasmeet.cinemate.presentation.theme.CineMateTheme
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge(
            statusBarStyle = SystemBarStyle.dark(android.graphics.Color.TRANSPARENT),
            navigationBarStyle = SystemBarStyle.dark(android.graphics.Color.TRANSPARENT)
        )

        setContent {
            CineMateTheme {
                val windowSize = calculateWindowSizeClass(activity = this)
                CineMateApp(windowSize = windowSize)
            }
        }
    }

}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun CineMateApp(
    windowSize: WindowSizeClass
) {

    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination
    val navigationItems = listOf(
        BottomNavigationItem.Home,
        BottomNavigationItem.Search,
        BottomNavigationItem.Wishlist,
        BottomNavigationItem.Profile
    )

    val bottomBarDestination = navigationItems.any {
        it.route == currentDestination?.route
    }

    Scaffold(
        bottomBar = {
            if (bottomBarDestination)
                BottomBar(
                    navController = navController,
                    currentDestination = currentDestination,
                    screens = navigationItems
                )
        }
    ) {
        CineMateNavigator(
            windowSize = windowSize,
            navController = navController,
        )
    }



}


