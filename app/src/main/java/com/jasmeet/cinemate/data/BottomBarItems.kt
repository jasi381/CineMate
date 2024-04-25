package com.jasmeet.cinemate.data

import com.jasmeet.cinemate.R
import com.jasmeet.cinemate.presentation.screens.Screens

sealed class BottomNavigationItem(
    val route: String,
    val unselectedIcon: Int,
    val selectedIcon: Int,
    val title: String
){
    data object Home: BottomNavigationItem(
        route = Screens.Home.route,
        unselectedIcon = R.drawable.ic_home_unselected,
        selectedIcon =  R.drawable.ic_home_selected,
        title = "Home"
    )
    data object Wishlist: BottomNavigationItem(
        route = Screens.Wishlist.route,
        unselectedIcon = R.drawable.ic_wishlist_unselected,
        selectedIcon = R.drawable.ic_wishlist_selected,
        title = "Wishlist"
    )


}