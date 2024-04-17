package com.jasmeet.cinemate.presentation.utils

import android.util.Patterns
import androidx.compose.ui.text.intl.Locale
import androidx.compose.ui.text.toLowerCase

object Utils {
    fun validateEmail(email: String): Boolean {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    fun validatePassword(password: String): Boolean {
        return password.length >= 8
    }

    fun getImageLinkWithSize(imgPath: String?, size: ImgSize): String {

        if (imgPath == null)
            return "https://static.vecteezy.com/system/resources/previews/005/337/799/original/icon-image-not-found-free-vector.jpg"

        return "https://image.tmdb.org/t/p/${size.name.toLowerCase(Locale.current)}/$imgPath"

    }

    fun formatMinutesToHoursAndMinutes(minutes: Int): String {
        val hours = minutes / 60
        val remainingMinutes = minutes % 60
        return if (hours > 0) {
            "${hours}h${if (remainingMinutes > 0) " ${remainingMinutes}min" else ""}"
        } else {
            "Not Released"
        }
    }




}

enum class ImgSize {
    W300,
    W780,
    W1280,
    Original
}