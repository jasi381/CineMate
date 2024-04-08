package com.jasmeet.cinemate.presentation.screens

import android.util.Log
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.collectIsDraggedAsState
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
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.jasmeet.cinemate.R
import com.jasmeet.cinemate.presentation.appComponents.CustomTab2
import com.jasmeet.cinemate.presentation.appComponents.MovieCarouselBox
import com.jasmeet.cinemate.presentation.appComponents.PagerIndicator
import com.jasmeet.cinemate.presentation.appComponents.TextComponent
import com.jasmeet.cinemate.presentation.extensions.carouselTransition
import com.jasmeet.cinemate.presentation.extensions.customClickable
import com.jasmeet.cinemate.presentation.theme.customShapeAllCorners
import com.jasmeet.cinemate.presentation.theme.customShapeTopCorners
import com.jasmeet.cinemate.presentation.utils.ImgSize
import com.jasmeet.cinemate.presentation.utils.Utils
import com.jasmeet.cinemate.presentation.viewModel.MoviesViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HomeScreen(
    vm: MoviesViewModel = hiltViewModel()
) {

    val tabs = listOf("Movies", "Tv Shows")
    var selectedTabIndex by rememberSaveable {
        mutableIntStateOf(0)
    }

    val popularMoviesResponseState = vm.popularMoviesResponse.collectAsState()
    val nowPlayingResponseState = vm.nowPlayingMovies.collectAsLazyPagingItems()

    val coroutineScope = rememberCoroutineScope()

    val originalList = popularMoviesResponseState.value?.results ?: emptyList()
    val shuffledList = originalList.shuffled()
    val randomSubList = shuffledList.take(8)

    val pagerState: PagerState = rememberPagerState(pageCount = { randomSubList.size })
    val isUserScrolling = pagerState.interactionSource.collectIsDraggedAsState()

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
        if(selectedTabIndex == 0) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Box {
                    HorizontalPager(
                        state = pagerState,
                        contentPadding = PaddingValues(
                            horizontal = dimensionResource(id = R.dimen.double_padding)
                        ),
                        pageSpacing = dimensionResource(id = R.dimen.normal_padding),

                        ) { page: Int ->
                        val item = randomSubList[page]
                        item.let {
                            Box(
                                modifier = Modifier
                                    .clip(customShapeAllCorners)
                                    .carouselTransition(
                                        page,
                                        pagerState
                                    )
                                    .customClickable {

                                        Log.d(
                                            "TAG",
                                            "HomeScreen: ${nowPlayingResponseState.itemCount}"
                                        )
                                    }
                            ) {
                                MovieCarouselBox(it)
                            }
                        }
                    }
                }
                PagerIndicator(
                    pagerState = pagerState,
                    itemCount = randomSubList.size
                )

                TextComponent(text = "Now Playing", modifier = Modifier.align(Alignment.Start), textColor = Color.White)
                Spacer(modifier = Modifier.height(15.dp))

                LazyRow(Modifier.padding(horizontal = 8.dp)) {

                    items(nowPlayingResponseState.itemCount) { index ->

                        val url ="https://image.tmdb.org/t/p/w780"
                        val imgBackPath = nowPlayingResponseState[index]?.backdrop_path
                        val imgUrl = if (nowPlayingResponseState[index]?.backdrop_path != null){
                            "$url$imgBackPath"
                        }else{
                            "https://static.vecteezy.com/system/resources/previews/005/337/799/original/icon-image-not-found-free-vector.jpg"
                        }

                        AsyncImage(
                            model = ImageRequest.Builder(LocalContext.current)
                                .data(imgUrl ?: R.drawable.ic_launcher_foreground)
                                .crossfade(true)
                                .build(),
                            placeholder = painterResource(R.drawable.ic_launcher_foreground),
                            contentDescription = "",
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .clip(RoundedCornerShape(10.dp))
                                .size(100.dp)
                        )

                    }
                    nowPlayingResponseState.apply {
                        when {
                            loadState.refresh is LoadState.Loading || loadState.append is LoadState.Loading -> {
                                Log.d("TAG", "DogsScreen: Loading")
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
                                    Box(modifier = Modifier
                                        .fillMaxSize()
                                        .statusBarsPadding()
                                        .navigationBarsPadding()){
                                        Text(text = "Error", modifier = Modifier.align(Alignment.Center) )
                                    }
                                }
                            }
                        }
                    }

                }

            }
        }
        if(selectedTabIndex == 1){
            Column(
                modifier = Modifier
                    .background(Color.White)
                    .fillMaxSize()
            ){
                //TODO
            }
        }
    }
    DisposableEffect(Unit) {
        val job = coroutineScope.launch {
            while (!isUserScrolling.value) {
                if (!isUserScrolling.value) {
                    delay(3000L)
                    val nextPage = (pagerState.currentPage + 1) % pagerState.pageCount
                    pagerState.animateScrollToPage(
                        page = nextPage,
                        animationSpec = tween(
                            durationMillis = 800,
                            easing = LinearEasing
                        )
                    )
                } else {
                    // Wait for user to stop scrolling
                    delay(100L)
                }
            }
        }
        onDispose {
            job.cancel()
        }
    }


}





