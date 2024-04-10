package com.jasmeet.cinemate.presentation.screens.homeScreen

import android.app.Activity
import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.draggable
import androidx.compose.foundation.gestures.rememberDraggableState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.jasmeet.cinemate.presentation.appComponents.CustomTab2
import com.jasmeet.cinemate.presentation.screens.homeScreen.movie.MainMovieLayout
import com.jasmeet.cinemate.presentation.screens.homeScreen.tvSeries.MainTvSeriesLayout
import com.jasmeet.cinemate.presentation.theme.customShapeTopCorners

@Composable
fun HomeScreen(

) {

    val tabs = listOf("Movies", "Tv Shows")
    var selectedTabIndex by rememberSaveable {
        mutableIntStateOf(0)
    }


    Column(
        Modifier
            .draggable(
                orientation = Orientation.Horizontal,
                state = rememberDraggableState { delta ->
                    selectedTabIndex = if (delta > 0) {
                        0
                    } else {
                        1
                    }
                }
            )
    ) {
        Box(
            Modifier.fillMaxWidth(),
            contentAlignment = Alignment.TopCenter
        ) {
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

        AnimatedContent(
            targetState = selectedTabIndex,
            label = "",
            transitionSpec = {
                if (targetState > initialState) {
                    slideInHorizontally(
                        initialOffsetX = { fullWidth -> fullWidth },
                        animationSpec = tween(800)
                    ) togetherWith slideOutHorizontally(
                        targetOffsetX = { fullWidth -> -fullWidth },
                        animationSpec = tween(800)
                    )
                } else {
                    slideInHorizontally(
                        initialOffsetX = { fullWidth -> -fullWidth },
                        animationSpec = tween(800)
                    ) togetherWith slideOutHorizontally(
                        targetOffsetX = { fullWidth -> fullWidth },
                        animationSpec = tween(800)
                    )
                }
            }
        ) { currentTabIndex ->
            when (currentTabIndex) {
                0 -> {
                    MainMovieLayout()
                }

                1 -> {
                    MainTvSeriesLayout()
                }
            }

        }

    }

}














