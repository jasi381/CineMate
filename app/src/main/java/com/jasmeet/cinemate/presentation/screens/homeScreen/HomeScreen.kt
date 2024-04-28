package com.jasmeet.cinemate.presentation.screens.homeScreen

import android.util.Log
import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.draggable
import androidx.compose.foundation.gestures.rememberDraggableState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import coil.compose.SubcomposeAsyncImage
import com.jasmeet.cinemate.R
import com.jasmeet.cinemate.presentation.appComponents.components.CustomTab2
import com.jasmeet.cinemate.presentation.appComponents.components.SearchBar
import com.jasmeet.cinemate.presentation.appComponents.components.TextComponent
import com.jasmeet.cinemate.presentation.screens.homeScreen.movie.MainMovieLayout
import com.jasmeet.cinemate.presentation.screens.homeScreen.tvSeries.MainTvSeriesLayout
import com.jasmeet.cinemate.presentation.theme.customShapeAllCorners
import com.jasmeet.cinemate.presentation.theme.customShapeTopCorners
import com.jasmeet.cinemate.presentation.utils.ImgSize
import com.jasmeet.cinemate.presentation.utils.Utils
import com.jasmeet.cinemate.presentation.viewModel.movies.SearchMovieViewModel

@Composable
fun HomeScreen(
    navController: NavHostController,
    paddingValues: PaddingValues,
    searchViewModel: SearchMovieViewModel = hiltViewModel()
) {

    val tabs = listOf("Movies", "Tv Shows")
    var selectedTabIndex by rememberSaveable {
        mutableIntStateOf(0)
    }
    var isSearchVisible by rememberSaveable {
        mutableStateOf(false)
    }

    var searchText by rememberSaveable {
        mutableStateOf("")
    }

    val searchResponse = searchViewModel.searchedMovies.collectAsLazyPagingItems()

    BackHandler {
        if (isSearchVisible) {
            isSearchVisible = false
            searchText = ""
        } else {
            navController.popBackStack()
        }
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
            .padding(bottom = paddingValues.calculateBottomPadding())
    ) {

        Row(
            Modifier
                .fillMaxWidth()
                .statusBarsPadding()
                .align(Alignment.CenterHorizontally),
            verticalAlignment = Alignment.CenterVertically
        ) {
            if (!isSearchVisible) {
                Box(Modifier.fillMaxWidth(0.9f)) {
                    this@Row.AnimatedVisibility(
                        visible = !isSearchVisible,
                        enter = scaleIn() + fadeIn(),
                        exit = fadeOut(),
                        modifier = Modifier
                            .fillMaxWidth(.6f)
                            .align(Alignment.Center),
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth(0.6f)
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
                }
            }
            if (isSearchVisible) {
                AnimatedVisibility(
                    visible = isSearchVisible,
                    enter = scaleIn() + fadeIn(),
                    exit = fadeOut()
                ) {
                    SearchBar(
                        value = searchText,
                        onValueChange = {
                            searchText = it
                        },
                        modifier = Modifier
                            .padding(horizontal = 8.dp)
                            .fillMaxWidth(0.9f)
                            .statusBarsPadding(),
                        labelValue = if (selectedTabIndex == 0) "Search Movies" else "Search Tv Shows",
                        onSearch = {
                            searchViewModel.searchMovies(searchText)
                        }
                    )
                }
            }

            IconButton(
                modifier = Modifier
                    .padding(end = 5.dp),
                onClick = {
                    if (selectedTabIndex == 0) {
                        isSearchVisible = !isSearchVisible
                    }
                    if (selectedTabIndex == 1) {
                        isSearchVisible = !isSearchVisible
                    }
                    if (isSearchVisible) {
                        searchText = ""
                        searchViewModel.clearQuery()
                    }
                }
            ) {
                Icon(
                    painter = painterResource(id = if (!isSearchVisible) R.drawable.ic_search_unselected else R.drawable.ic_close),
                    contentDescription = "search",
                    modifier = Modifier.size(25.dp)
                )
            }
        }

        Spacer(modifier = Modifier.height(15.dp))

        AnimatedVisibility(
            visible = !isSearchVisible,
            enter = scaleIn(),
            exit = scaleOut()
        ) {

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
                        MainMovieLayout(navController = navController)
                    }

                    1 -> {
                        MainTvSeriesLayout(navController = navController)
                    }
                }

            }
        }

        AnimatedVisibility(
            visible = isSearchVisible,
            enter = scaleIn(),
            exit = scaleOut()
        ) {

            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(12.dp),

                ) {
                items(
                    searchResponse.itemCount,
                    key = {
                        it.toString()
                    }) { index ->
                    val url = Utils.getImageLinkWithSize(
                        searchResponse[index]?.backdrop_path,
                        ImgSize.Original
                    )

                    val animatable = remember {
                        Animatable(0.7f)
                    }

                    LaunchedEffect(key1 = true) {
                        animatable.animateTo(
                            1f,
                            tween(350, delayMillis = 100, easing = FastOutSlowInEasing)
                        )

                    }
                    Box(
                        modifier = Modifier
                            .graphicsLayer {
                                this.scaleX = animatable.value
                                this.scaleY = animatable.value
                            }
                            .height(LocalConfiguration.current.screenHeightDp.dp * 0.19f)
                            .width(LocalConfiguration.current.screenWidthDp.dp * 0.6f)
                            .clip(customShapeAllCorners)
//                            .clickable {
//                                onItemClick.invoke(nowPlayingMoviesResponseState[index]!!.id.toString())
//                            },

                    ) {
                        SubcomposeAsyncImage(
                            model = url,
                            contentDescription = null,
                            modifier = Modifier
                                .fillMaxSize(),
                            contentScale = ContentScale.FillBounds,
                            loading = {
                                Box(
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .background(MaterialTheme.colorScheme.background.copy(alpha = 0.5f)),
                                    contentAlignment = Alignment.Center
                                ) {
                                    CircularProgressIndicator(
                                        modifier = Modifier.align(Alignment.Center),
                                        color = Color(0xffE50914),
                                        strokeWidth = 3.dp,
                                        strokeCap = StrokeCap.Round,

                                        )
                                }
                            }
                        )
                        TextComponent(
                            text = searchResponse[index]?.title ?: "",
                            modifier = Modifier
                                .fillMaxWidth()
                                .align(Alignment.BottomCenter)
                                .background(Color.Black.copy(alpha = 0.3f))
                                .padding(5.dp),
                            textColor = Color.White,
                            textSize = 16.sp,
                            textAlign = TextAlign.Center
                        )


                    }

                }
                searchResponse.apply {
                    when {
                        loadState.refresh is LoadState.Loading || loadState.append is LoadState.Loading -> {
                            item {
                                Box(
                                    modifier = Modifier.fillMaxSize(),
                                    contentAlignment = Alignment.Center
                                ) {
                                    CircularProgressIndicator(
                                        modifier = Modifier.align(Alignment.Center)
                                    )
                                }
                            }
                        }

                        loadState.refresh is LoadState.Error || loadState.append is LoadState.Error -> {
                            Log.d("TAG", "DogsScreen: Error")
                            item {
                                Box(
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .statusBarsPadding()
                                        .navigationBarsPadding()
                                ) {
                                    Text(
                                        text = "Error",
                                        modifier = Modifier.align(Alignment.Center)
                                    )
                                }
                            }
                        }
                    }
                }

            }



        }

    }

}













