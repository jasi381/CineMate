package com.jasmeet.cinemate.presentation.utils

import android.content.Context
import android.os.Bundle
import com.google.firebase.analytics.FirebaseAnalytics

object Utils {
     fun logEvent(
        eventName: String,
        params: Bundle? = null,
        context: Context
    ) {
        FirebaseAnalytics.getInstance(context).logEvent(eventName, params)
    }
}