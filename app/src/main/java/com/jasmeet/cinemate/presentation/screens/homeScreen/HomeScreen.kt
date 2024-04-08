package com.jasmeet.cinemate.presentation.screens.homeScreen

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.jasmeet.cinemate.presentation.appComponents.CustomTab2
import com.jasmeet.cinemate.presentation.screens.homeScreen.movie.BannerMoviesView
import com.jasmeet.cinemate.presentation.screens.homeScreen.movie.NowPlayingMoviesView
import com.jasmeet.cinemate.presentation.screens.homeScreen.movie.TopRatedMoviesView
import com.jasmeet.cinemate.presentation.screens.homeScreen.movie.UpcomingMoviesView
import com.jasmeet.cinemate.presentation.theme.customShapeTopCorners

@Composable
fun HomeScreen(

) {

    val tabs = listOf("Movies", "Tv Shows")
    var selectedTabIndex by rememberSaveable {
        mutableIntStateOf(0)
    }


    Column {
        Box(Modifier.fillMaxWidth(), contentAlignment = Alignment.TopCenter) {
            Row(
                Modifier
                    .align(Alignment.TopCenter)
                    .statusBarsPadding()
                    .fillMaxWidth(.6f)
                    .clip(customShapeTopCorners)
                    .height(50.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {

                tabs.forEachIndexed { index, tab ->
                    CustomTab2(
                        text = tab,
                        isSelected = selectedTabIndex == index,
                        index,
                    ) {
                        selectedTabIndex = it
                    }
                }

            }
        }

        Spacer(modifier = Modifier.height(15.dp))
        if (selectedTabIndex == 0) {

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
    }
    if (selectedTabIndex == 1) {
        Column(
            modifier = Modifier
                .background(Color.White)
                .fillMaxSize()
        ) {
            //TODO
        }
    }
}














