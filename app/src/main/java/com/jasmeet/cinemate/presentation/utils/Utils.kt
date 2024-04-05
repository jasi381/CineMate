package com.jasmeet.cinemate.presentation.utils

import android.util.Patterns

object Utils {
    fun validateEmail(email: String): Boolean {
        return  Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    fun validatePassword(password: String): Boolean {
        return password.length >= 8
    }
}