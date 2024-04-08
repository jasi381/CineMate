package com.jasmeet.cinemate.presentation.utils

import android.util.Patterns

object Utils {
    fun validateEmail(email: String): Boolean {
        return  Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    fun validatePassword(password: String): Boolean {
        return password.length >= 8
    }

    fun getImageLinkWithSize(imgPath :String? , size :ImgSize):String{

        if(imgPath == null)
            return "https://static.vecteezy.com/system/resources/previews/005/337/799/original/icon-image-not-found-free-vector.jpg"

        return "https://image.tmdb.org/t/p/$size/$imgPath"

    }
}


enum class ImgSize{
    w300,
    w780,
    w1280,
    original
}