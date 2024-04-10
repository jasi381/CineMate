package com.jasmeet.cinemate.presentation.screens.homeScreen.tvSeries

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun MainTvSeriesLayout(modifier: Modifier = Modifier) {
    Column(
        Modifier.verticalScroll(rememberScrollState())
    ) {
        BannerTvSeriesView(
            onItemClick = { seriesid ->
                Log.d("Banner", "HomeScreen: $seriesid")
            }
        )
        Spacer(modifier = Modifier.height(10.dp))

        AiringTvSeriesView(
            modifier = Modifier.padding(horizontal = 8.dp),
            onItemClick = { movieId ->
                Log.d("Now Playing", "HomeScreen: $movieId")
            }
        )

        Spacer(modifier = Modifier.height(10.dp))

        TopRatedTvSeriesView(
            modifier = Modifier.padding(horizontal = 8.dp),
            onItemClick = { seriesId ->
                Log.d("UpComing", "HomeScreen: $seriesId")

            }
        )
        Spacer(modifier = Modifier.height(15.dp))

    }
}