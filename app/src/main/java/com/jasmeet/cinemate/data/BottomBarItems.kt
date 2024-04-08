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
    data object Search: BottomNavigationItem(
        route = Screens.Search.route,
        unselectedIcon = R.drawable.ic_search_unselected,
        selectedIcon = R.drawable.ic_search_selected,
        title = "Search"
    )
    data object Wishlist: BottomNavigationItem(
        route = Screens.Wishlist.route,
        unselectedIcon = R.drawable.ic_wishlist_unselected,
        selectedIcon = R.drawable.ic_wishlist_selected,
        title = "Wishlist"
    )
    data object Profile: BottomNavigationItem(
        route = Screens.Profile.route,
        unselectedIcon = R.drawable.ic_profile_unselected,
        selectedIcon = R.drawable.ic_profile_selected,
        title = "Profile"
    )

}