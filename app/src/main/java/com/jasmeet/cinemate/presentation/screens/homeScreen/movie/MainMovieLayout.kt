package com.jasmeet.cinemate.presentation.screens.homeScreen.movie

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
fun MainMovieLayout(modifier: Modifier = Modifier) {
    Column(
        Modifier.verticalScroll(rememberScrollState())
    ) {
        BannerMoviesView(
            onItemClick = { movieId ->
                Log.d("Banner", "HomeScreen: $movieId")
            }
        )
        Spacer(modifier = Modifier.height(10.dp))

        NowPlayingMoviesView(
            modifier = Modifier.padding(horizontal = 8.dp),
            onItemClick = { movieId ->
                Log.d("Now Playing", "HomeScreen: $movieId")
            }
        )

        Spacer(modifier = Modifier.height(10.dp))

        UpcomingMoviesView(
            modifier = Modifier.padding(horizontal = 8.dp),
            onItemClick = { movieId ->
                Log.d("UpComing", "HomeScreen: $movieId")

            }
        )

        Spacer(modifier = Modifier.height(10.dp))



        TopRatedMoviesView(
            modifier = Modifier.padding(horizontal = 8.dp),
            onItemClick = { movieId ->
                Log.d("Top Rated", "HomeScreen: $movieId")

            }
        )
        Spacer(modifier = Modifier.height(15.dp))

    }
}