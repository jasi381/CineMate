package com.jasmeet.cinemate.presentation.screens

const val ID = "id"
const val IS_MOVIE = false
const val VIDEO_ID ="video_id"
const val CHARACTER_ID ="char_id"
const val CHARACTER_NAME ="char_name"
const val CHARACTER_IMG ="char_img"


sealed class Screens(val route :String) {

    data object Splash : Screens("splash")
    data object Login : Screens("login")
    data object Home : Screens("home")
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

    data object CharacterDetails : Screens("character/{$CHARACTER_ID}/{$CHARACTER_NAME}/{$CHARACTER_IMG}"){
        fun passCharacterId(id : String, name : String, img : String) : String{
            return this.route
                .replace("{$CHARACTER_ID}", id)
                .replace("{$CHARACTER_NAME}", name)
                .replace("{$CHARACTER_IMG}", img)
        }
    }

}