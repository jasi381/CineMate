package com.jasmeet.cinemate.presentation.appComponents

import android.app.Activity
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.RowScope
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemColors
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination
import androidx.navigation.NavHostController
import androidx.navigation.NavOptions
import com.jasmeet.cinemate.data.data.BottomNavigationItem
import com.jasmeet.cinemate.presentation.screens.Screens
import com.jasmeet.cinemate.presentation.theme.libreBaskerville

@Composable
fun BottomBar(
    navController: NavHostController,
    currentDestination: NavDestination?,
    screens: List<BottomNavigationItem>
) {
    NavigationBar(
        tonalElevation = 10.dp,
        containerColor = Color(0xff131313),
    ) {
        screens.forEach { screen ->
            AddBottomNavItem(
                navHostController = navController,
                screen = screen,
                currentDestination = currentDestination
            )
        }
    }
}

@Composable
fun RowScope.AddBottomNavItem(
    navHostController: NavHostController,
    currentDestination: NavDestination?,
    screen: BottomNavigationItem,
) {
    val context = LocalContext.current

    val isSelected = currentDestination?.route == screen.route

    BackHandler {
        if (currentDestination?.route == Screens.Home.route) {
            val activity = (context as? Activity)
            activity?.finish()
        } else {
            val navOptions = NavOptions.Builder()
                .setPopUpTo(screen.route, inclusive = true)
                .build()
            navHostController.navigate(Screens.Home.route, navOptions)
        }
    }

    NavigationBarItem(
        colors = NavigationBarItemColors(
            selectedIndicatorColor = Color.Transparent,
            selectedTextColor = Color.Transparent,
            unselectedTextColor = Color.Transparent,
            disabledIconColor = Color.Transparent,
            disabledTextColor = Color.Transparent,
            selectedIconColor = Color.Transparent,
            unselectedIconColor = Color.Transparent
        ),
        selected = currentDestination?.route == screen.route,
        onClick = {
            if (currentDestination?.route != screen.route) {
                val navOptions = NavOptions.Builder()
                    .setPopUpTo(screen.route, inclusive = true)
                    .build()
                navHostController.navigate(screen.route, navOptions)
            }
        },
        icon = {
            Icon(
                imageVector = ImageVector.vectorResource(id = if (isSelected) screen.selectedIcon else screen.unselectedIcon),
                contentDescription = "Icon",
                tint = if (isSelected) {
                    Color(0xffF2C94C)
                } else {
                    Color.White
                },
            )
        },
        label = {
            Text(
                text = screen.title,
                style = TextStyle(
                    fontFamily = libreBaskerville,
                    fontWeight = FontWeight(500),
                    color = if (currentDestination?.route == screen.route) {
                        Color(0xffF2C94C)
                    } else {
                        Color.White
                    },
                    textAlign = TextAlign.Center,
                ),
            )
        }
    )
}