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
import androidx.navigation.NavHostController
import com.jasmeet.cinemate.presentation.screens.Screens

@Composable
fun MainMovieLayout(modifier: Modifier = Modifier, navController: NavHostController) {
    Column(
        Modifier.verticalScroll(rememberScrollState())
    ) {
        BannerMoviesView(
            onItemClick = { movieId ->
                navController.navigate(
                    Screens.Detail.passMoviesId(
                        id = movieId,
                        isMovie = true
                    )
                )
            }
        )
        Spacer(modifier = Modifier.height(10.dp))

        NowPlayingMoviesView(
            modifier = Modifier.padding(horizontal = 8.dp),
            onItemClick = { movieId ->
                navController.navigate(
                    Screens.Detail.passMoviesId(
                        id = movieId,
                        isMovie = true
                    )
                )
            }
        )

        Spacer(modifier = Modifier.height(10.dp))

        UpcomingMoviesView(
            modifier = Modifier.padding(horizontal = 8.dp),
            onItemClick = { movieId ->
                navController.navigate(
                    Screens.Detail.passMoviesId(
                        id = movieId,
                        isMovie = true
                    )
                )

            }
        )

        Spacer(modifier = Modifier.height(10.dp))



        TopRatedMoviesView(
            modifier = Modifier.padding(horizontal = 8.dp),
            onItemClick = { movieId ->
                navController.navigate(
                    Screens.Detail.passMoviesId(
                        id = movieId,
                        isMovie = true
                    )
                )

            }
        )
        Spacer(modifier = Modifier.height(15.dp))

    }
}