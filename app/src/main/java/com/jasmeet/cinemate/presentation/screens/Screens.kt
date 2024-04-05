package com.jasmeet.cinemate.presentation.screens

sealed class Screens(val route :String) {

    data object Splash : Screens("splash")
    data object Login : Screens("login")
    data object Home : Screens("home")
    data object Search : Screens("search")
    data object Wishlist : Screens("wishlist")
    data object Profile : Screens("profile")

}