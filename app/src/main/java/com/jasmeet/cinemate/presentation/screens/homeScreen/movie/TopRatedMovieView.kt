package com.jasmeet.cinemate.presentation.screens.homeScreen.movie

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import coil.compose.SubcomposeAsyncImage
import com.jasmeet.cinemate.presentation.appComponents.TextComponent
import com.jasmeet.cinemate.presentation.extensions.customClickable
import com.jasmeet.cinemate.presentation.theme.customShapeAllCorners
import com.jasmeet.cinemate.presentation.utils.ImgSize
import com.jasmeet.cinemate.presentation.utils.Utils
import com.jasmeet.cinemate.presentation.viewModel.movies.TopRatedMoviesViewModel
import com.jasmeet.cinemate.data.apiResponse.remote.movies.topRated.Result

@Composable
fun TopRatedMoviesView(
    modifier: Modifier = Modifier,
    topRatedMoviesViewModel: TopRatedMoviesViewModel = hiltViewModel(),
    onItemClick: (String) -> Unit = {}
) {

    val topRatedMovieResponseState = topRatedMoviesViewModel.topRatedMovies.collectAsLazyPagingItems()

    Column(
        modifier = modifier
    ) {
        TextComponent(
            text = "Top-Rated",
            modifier = Modifier,
            textColor = Color.White,
            textSize = 20.sp,
            fontWeight = FontWeight.SemiBold
        )
        Spacer(modifier = Modifier.height(15.dp))


        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(12.dp),

            ) {
            items(topRatedMovieResponseState.itemCount) { index ->
                val url = Utils.getImageLinkWithSize(
                    topRatedMovieResponseState[index]?.backdrop_path,
                    ImgSize.original
                )
                Box(
                    modifier = Modifier
                        .height(LocalConfiguration.current.screenHeightDp.dp * 0.19f)
                        .width(LocalConfiguration.current.screenWidthDp.dp * 0.6f)
                        .clip(customShapeAllCorners)
                        .clickable {
                            onItemClick(topRatedMovieResponseState[index]?.id.toString())
                        },

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
                                    .background(Color(0xff131313)),
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
                        text = topRatedMovieResponseState[index]?.title ?: "",
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
            topRatedMovieResponseState.apply {
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