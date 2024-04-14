package com.jasmeet.cinemate.presentation.appComponents

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.LifecycleOwner
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView

@Composable
fun VideoView(
    modifier: Modifier = Modifier,
    id: String,
    lifecycleOwner: LifecycleOwner
) {

    AndroidView(
        factory = { context ->

            YouTubePlayerView(context = context).apply {
                lifecycleOwner.lifecycle.addObserver(this)
                addYouTubePlayerListener(object : AbstractYouTubePlayerListener() {

                    override fun onReady(youTubePlayer: YouTubePlayer) {
                        youTubePlayer.loadVideo(id, 0f)
                    }
                }
                )
            }

        },
        modifier = modifier.navigationBarsPadding().fillMaxSize()
    )

}