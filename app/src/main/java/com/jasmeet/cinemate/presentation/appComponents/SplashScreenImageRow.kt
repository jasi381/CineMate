package com.jasmeet.cinemate.presentation.appComponents

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.FilterQuality
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import coil.compose.AsyncImage
import coil.request.ImageRequest
import coil.transition.CrossfadeTransition

@SuppressLint("ModifierParameter")
@Composable
fun ImageRow(
    modifierImg : Modifier = Modifier,
    modifier: Modifier = Modifier,
    imageRow: List<String>,
    alpha: (Int) -> Float,
    contentScale: ContentScale = ContentScale.Fit

) {

    val imgUrl ="https://image.tmdb.org/t/p/w780/"
    Row(
        modifier = modifier.fillMaxWidth()
    ) {
        imageRow.forEachIndexed { index, url ->
            Log.d("ImageRow", "ImageRow: $imgUrl+$url")
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current).data("$imgUrl$url")
                    .transitionFactory(
                        CrossfadeTransition.Factory(
                            durationMillis = 800 + index * 400
                        )
                    ).build(),
                contentDescription = null,
                modifier = modifierImg.alpha(alpha(index).coerceIn(0.0f, 1.0f)).weight(1f),
                contentScale = contentScale,
                filterQuality = FilterQuality.High
            )
        }
    }
}


