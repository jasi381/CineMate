package com.jasmeet.cinemate.presentation.screens

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
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.jasmeet.cinemate.R
import com.jasmeet.cinemate.presentation.appComponents.CustomTab2
import com.jasmeet.cinemate.presentation.theme.customShapeTopCorners
import com.jasmeet.cinemate.presentation.viewModel.PopularMoviesViewModel


@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    vm: PopularMoviesViewModel = hiltViewModel()
) {

    val tabs = listOf("Movies", "Tv Shows")
    var selectedTabIndex by rememberSaveable {
        mutableIntStateOf(0)
    }

    val response = vm.popularMoviesResponse.collectAsLazyPagingItems()

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

        LazyVerticalStaggeredGrid(
            columns = StaggeredGridCells.Fixed(2),
            modifier = modifier
                .fillMaxSize()
                .weight(1f),
            horizontalArrangement = Arrangement.spacedBy(10.dp),
        ) {

            items(response.itemCount) {

                val url = "https://image.tmdb.org/t/p/w500"
                val img = response[it]?.backdrop_path
                val imgUrl = "$url$img"
                Box {
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
                            .align(Alignment.Center)
                    )

                    Column(Modifier.align(Alignment.BottomStart).background(Color.Black)) {
                        Text(
                            text = response[it]?.title ?: "",
                            modifier = Modifier,
                            color = Color.White,
                            maxLines = 1

                        )
                    }
                }
            }

            response.apply {
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