package com.jasmeet.cinemate.presentation.screens.homeScreen.tvSeries

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
fun MainTvSeriesLayout(modifier: Modifier = Modifier, navController: NavHostController) {
    Column(
        Modifier.verticalScroll(rememberScrollState())
    ) {
        BannerTvSeriesView(
            onItemClick = { seriesId ->
                navController.navigate(
                    Screens.Detail.passMoviesId(
                        id = seriesId,
                        isMovie = false
                    )
                )
            }
        )
        Spacer(modifier = Modifier.height(10.dp))

        AiringTvSeriesView(
            modifier = Modifier.padding(horizontal = 8.dp),
            onItemClick = { seriesId ->
                navController.navigate(
                    Screens.Detail.passMoviesId(
                        id = seriesId,
                        isMovie = false
                    )
                )
            }
        )

        Spacer(modifier = Modifier.height(10.dp))

        TopRatedTvSeriesView(
            modifier = Modifier.padding(horizontal = 8.dp),
            onItemClick = { seriesId ->
                navController.navigate(
                    Screens.Detail.passMoviesId(
                        id = seriesId,
                        isMovie = false
                    )
                )

            }
        )
        Spacer(modifier = Modifier.height(15.dp))

    }
}