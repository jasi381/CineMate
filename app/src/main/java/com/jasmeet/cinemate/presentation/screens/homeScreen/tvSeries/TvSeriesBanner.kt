package com.jasmeet.cinemate.presentation.screens.homeScreen.tvSeries

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.collectIsDraggedAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.jasmeet.cinemate.R
import com.jasmeet.cinemate.presentation.appComponents.PagerIndicator
import com.jasmeet.cinemate.presentation.appComponents.TvSeriesCarouselBox
import com.jasmeet.cinemate.presentation.appComponents.extensions.carouselTransition
import com.jasmeet.cinemate.presentation.theme.customShapeAllCorners
import com.jasmeet.cinemate.presentation.viewModel.series.BannerSeriesViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun BannerTvSeriesView(
    modifier: Modifier = Modifier,
    bannerSeriesViewModel: BannerSeriesViewModel = hiltViewModel(),
    onItemClick: (String) -> Unit = {}

) {

    val popularMoviesResponseState = bannerSeriesViewModel.bannerSeriesResponse.collectAsState()
    val coroutineScope = rememberCoroutineScope()

    val originalList = popularMoviesResponseState.value?.results ?: emptyList()
    val shuffledList = originalList.shuffled()
    val randomSubList = shuffledList.take(8)

    val pagerState: PagerState = rememberPagerState(pageCount = { randomSubList.size })
    val isUserScrolling = pagerState.interactionSource.collectIsDraggedAsState()

    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Box {
            HorizontalPager(
                state = pagerState,
                contentPadding = PaddingValues(
                    horizontal = dimensionResource(id = R.dimen.double_padding)
                ),
                pageSpacing = dimensionResource(id = R.dimen.normal_padding),
                modifier = Modifier.padding(bottom = 10.dp)

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
                            .clickable {
                                onItemClick(it.id.toString())
                            }
                    ) {
                        TvSeriesCarouselBox(it)
                    }
                }
            }
        }
        PagerIndicator(
            pagerState = pagerState,
            itemCount = randomSubList.size
        )
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
                    delay(100L)
                }
            }
        }
        onDispose {
            job.cancel()
        }
    }
}