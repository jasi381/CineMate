package com.jasmeet.cinemate.presentation.screens

const val ID = "id"
const val IS_MOVIE = false
const val VIDEO_ID ="video_id"


sealed class Screens(val route :String) {

    data object Splash : Screens("splash")
    data object Login : Screens("login")
    data object Home : Screens("home")
    data object Search : Screens("search")
    data object Wishlist : Screens("wishlist")
    data object Profile : Screens("profile")

    data object Video :Screens("video/{$VIDEO_ID}"){
        fun passVideoId(id : String) : String{
            return this.route.replace("{$VIDEO_ID}", id)
        }
    }

    data object Detail : Screens("detail/{$ID}/{$IS_MOVIE}"){

        fun passMoviesId(id : String, isMovie : Boolean) : String{
            return this.route
                .replace("{$ID}", id)
                .replace("{$IS_MOVIE}", isMovie.toString())

        }
    }

}