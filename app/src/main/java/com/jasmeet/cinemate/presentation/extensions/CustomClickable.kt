package com.jasmeet.cinemate.presentation.extensions

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed

fun Modifier.customClickable(
    onclick :()->Unit
)= composed {

    val interactionSource = remember { MutableInteractionSource() }
    this.clickable(indication = null,interactionSource = interactionSource) {
        onclick.invoke()
    }
}