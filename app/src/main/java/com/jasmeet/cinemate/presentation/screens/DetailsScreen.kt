package com.jasmeet.cinemate.presentation.screens

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.FilledTonalIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.jasmeet.cinemate.R
import com.jasmeet.cinemate.presentation.appComponents.TextComponent
import com.jasmeet.cinemate.presentation.appComponents.extensions.customClickable
import com.jasmeet.cinemate.presentation.utils.ImgSize
import com.jasmeet.cinemate.presentation.utils.Utils
import com.jasmeet.cinemate.presentation.viewModel.DetailsViewModel
import com.skydoves.landscapist.ImageOptions
import com.skydoves.landscapist.animation.circular.CircularRevealPlugin
import com.skydoves.landscapist.coil.CoilImage
import com.skydoves.landscapist.components.rememberImageComponent
import com.skydoves.landscapist.placeholder.shimmer.Shimmer
import com.skydoves.landscapist.placeholder.shimmer.ShimmerPlugin
import okhttp3.internal.trimSubstring

@RequiresApi(Build.VERSION_CODES.R)
@Composable
fun DetailsScreen(
    navController: NavHostController,
    id: String?,
    isMovie: Boolean?,
    detailsViewModel: DetailsViewModel = hiltViewModel()

) {

    LaunchedEffect(Unit) {
        detailsViewModel.fetchMovieDetails(id!!, isMovie!!)
    }

    val movieDetails = detailsViewModel.movieDetails.observeAsState()
    val seriesDetails = detailsViewModel.seriesDetails.observeAsState()

    val backdropImgUrl = if (isMovie == true) Utils.getImageLinkWithSize(
        movieDetails.value?.backdrop_path,
        ImgSize.W1280
    ) else Utils.getImageLinkWithSize(seriesDetails.value?.backdrop_path, ImgSize.W1280)





    Column(
        Modifier
            .fillMaxSize()
            .background(Color(0xff131313)),
    ) {
        Box {
            Box(
                Modifier
                    .align(Alignment.TopStart)
                    .statusBarsPadding()
                    .zIndex(1f)
            ) {
                FilledTonalIconButton(
                    onClick = {
                        navController.popBackStack()
                    },
                    modifier = Modifier,
                    colors = IconButtonDefaults.filledIconButtonColors(
                        containerColor = Color(
                            0x4D000000
                        )
                    )
                ) {
                    Icon(
                        imageVector = ImageVector.vectorResource(id = R.drawable.ic_back),
                        contentDescription = "Back Button"
                    )
                }
            }
            Column {
                Box {
                    CoilImage(
                        imageModel = { backdropImgUrl },
                        modifier = Modifier
                            .height(
                                LocalConfiguration.current.screenHeightDp.dp * 0.4f
                            )
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(bottomStart = 40.dp)),
                        component = rememberImageComponent {
                            +ShimmerPlugin(
                                Shimmer.Resonate(
                                    baseColor = Color.Black,
                                    highlightColor = Color.LightGray,
                                ),
                            )
                            +CircularRevealPlugin(
                                duration = 750
                            )
                        },
                        imageOptions = ImageOptions(contentScale = ContentScale.FillBounds)
                    )

                    ElevatedCard(
                        Modifier
                            .offset(y = 25.dp)
                            .fillMaxWidth(0.5f)
                            .height(70.dp)
                            .align(Alignment.BottomEnd),
                        colors = CardDefaults.elevatedCardColors(
                            containerColor = Color(
                                0x66000000
                            )
                        ),
                        shape = RoundedCornerShape(topStart = 45.dp, bottomStart = 45.dp)
                    ) {

                        Row(
                            Modifier
                                .padding(horizontal = 10.dp)
                                .fillMaxSize()
                                .align(Alignment.CenterHorizontally),
                            verticalAlignment = Alignment.CenterVertically
                        ) {

                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally,
                                modifier = Modifier
                                    .padding(horizontal = 10.dp)
                                    .weight(1f)
                            ) {
                                Icon(
                                    imageVector = ImageVector.vectorResource(id = R.drawable.ic_ratings),
                                    contentDescription = null,
                                    tint = Color.White,
                                    modifier = Modifier.size(25.dp)
                                )

                                val rating =
                                    if (isMovie == true) movieDetails.value?.vote_average else seriesDetails.value?.vote_average
                                TextComponent(
                                    text = rating.toString().trimSubstring(0, 3),
                                    textColor = Color.White,
                                )
                            }

                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally,
                                modifier = Modifier
                                    .padding(horizontal = 10.dp)
                                    .weight(1f)
                                    .customClickable {
                                        navController.navigate(Screens.Video.passVideoId("Way9Dexny3w"))
                                    }
                            ) {
                                Icon(
                                    painter = painterResource(id = R.drawable.ic_trailer),
                                    contentDescription = null,
                                    tint = Color.White,
                                    modifier = Modifier.size(25.dp)
                                )

                                TextComponent(
                                    text = "Trailer",
                                    textColor = Color.White,
                                )
                            }

                        }
                    }
                }
                TextComponent(text = id.toString(), textColor = Color.White)
            }

        }
    }
}