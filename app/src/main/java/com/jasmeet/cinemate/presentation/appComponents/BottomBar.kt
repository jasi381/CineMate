package com.jasmeet.cinemate.presentation.appComponents

import android.app.Activity
import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.EaseInExpo
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.layout.RowScope
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemColors
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavDestination
import androidx.navigation.NavHostController
import androidx.navigation.NavOptions
import com.jasmeet.cinemate.data.BottomNavigationItem
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
        containerColor = MaterialTheme.colorScheme.background,
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

    val selectedColor = remember { Color(0xffE50914) }
    val unselectedColor = MaterialTheme.colorScheme.onBackground
    val targetColor = if (isSelected) selectedColor else unselectedColor
    val color by animateColorAsState(
        targetValue = targetColor,
        animationSpec = tween(easing = EaseInExpo ),
        label = ""
    )

    val selectedTextSize = if (isSelected) 14.5.sp else 12.sp
    val targetTextSize by animateFloatAsState(
        targetValue = selectedTextSize.value,
        animationSpec = tween(easing = EaseInExpo ),
        label = ""
    )



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

            AnimatedVisibility(
                visible = isSelected,
                enter = scaleIn(),
                exit = scaleOut()
            ) {
                Icon(
                    imageVector = ImageVector.vectorResource(id =  screen.selectedIcon ),
                    contentDescription = "Icon",
                    tint = color,
                )
            }

            AnimatedVisibility(
                visible = !isSelected,
                enter = scaleIn(),
                exit = scaleOut()
            ) {
                Icon(
                    imageVector = ImageVector.vectorResource(id = screen.unselectedIcon),
                    contentDescription = "Icon",
                    tint = color,
                )

            }

        },
        label = {
            Text(
                text = screen.title,
                style = TextStyle(
                    fontFamily = libreBaskerville,
                    fontWeight = FontWeight(500),
                    color = color,
                    textAlign = TextAlign.Center,
                    fontSize = targetTextSize.sp
                ),
            )
        }
    )
}