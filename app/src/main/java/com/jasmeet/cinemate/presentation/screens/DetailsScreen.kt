package com.jasmeet.cinemate.presentation.screens

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.twotone.Add
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.FilledTonalIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.jasmeet.cinemate.R
import com.jasmeet.cinemate.data.apiResponse.remote.movies.castAndCrew.Cast
import com.jasmeet.cinemate.data.apiResponse.remote.movies.castAndCrew.Crew
import com.jasmeet.cinemate.data.apiResponse.remote.movies.details.Genre
import com.jasmeet.cinemate.presentation.appComponents.TextComponent
import com.jasmeet.cinemate.presentation.appComponents.extensions.customClickable
import com.jasmeet.cinemate.presentation.theme.customShapeAllCorners
import com.jasmeet.cinemate.presentation.theme.libreBaskerville
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
        detailsViewModel.fetchVideoDetails(id)
        if (isMovie == true) detailsViewModel.fetchMovieCast(id)
    }

    val movieDetails = detailsViewModel.movieDetails.observeAsState()
    val seriesDetails = detailsViewModel.seriesDetails.observeAsState()
    val castDetails = detailsViewModel.movieCastDetails.observeAsState()

    val isLoading = detailsViewModel.isLoading.observeAsState()
    val trailerId = detailsViewModel.trailerId.observeAsState()

    val backdropImgUrl = if (isMovie == true) Utils.getImageLinkWithSize(
        movieDetails.value?.backdrop_path,
        ImgSize.W780
    ) else Utils.getImageLinkWithSize(seriesDetails.value?.backdrop_path, ImgSize.W780)



    Column(
        Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .verticalScroll(rememberScrollState()),
    ) {

        if (isLoading.value == true) {
            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator(
                    modifier = Modifier.size(40.dp),
                    color = Color.White.copy(alpha = 0.7f),
                    strokeWidth = 2.5f.dp,
                    strokeCap = StrokeCap.Round,
                )
            }
        } else {
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
                                MaterialTheme.colorScheme.background.copy(alpha = 0.4f).toArgb()
                            )
                        )
                    ) {
                        Icon(
                            imageVector = ImageVector.vectorResource(id = R.drawable.ic_back),
                            contentDescription = "Back Button",
                        )
                    }
                }
                Column(
                    Modifier
                        .fillMaxSize()

                ) {
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

                        Box(
                            Modifier
                                .offset(y = 25.dp)
                                .fillMaxWidth(0.5f)
                                .align(Alignment.BottomEnd)
                        ) {
                            val rating =
                                if (isMovie == true) movieDetails.value?.vote_average else seriesDetails.value?.vote_average
                            if (rating != null) {
                                if (trailerId.value != null) {

                                    ElevatedCard(
                                        Modifier
                                            .height(70.dp)
                                            .align(Alignment.BottomEnd),
                                        colors = CardDefaults.elevatedCardColors(
                                            containerColor = Color(
                                                0x66000000
                                            )
                                        ),
                                        shape = RoundedCornerShape(
                                            topStart = 45.dp,
                                            bottomStart = 45.dp
                                        )
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


                                                TextComponent(
                                                    text = if (rating > 0.0)
                                                        rating.toString()
                                                            .trimSubstring(
                                                                0,
                                                                3
                                                            ) else "Rating not available",
                                                    textColor = Color.White,
                                                    textSize = if (rating > 0.0) 18.sp else 12.sp,
                                                )
                                            }


                                            Column(
                                                horizontalAlignment = Alignment.CenterHorizontally,
                                                modifier = Modifier
                                                    .padding(horizontal = 10.dp)
                                                    .weight(1f)
                                                    .customClickable {
                                                        if (trailerId.value != null)
                                                            navController.navigate(
                                                                Screens.Video.passVideoId(
                                                                    trailerId.value ?: ""
                                                                )
                                                            )
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
                                } else {
                                    Box(
                                        Modifier
                                            .height(70.dp)
                                            .align(Alignment.BottomEnd),
                                    )
                                }
                            }
                        }
                    }

                    val movie = movieDetails.value
                    val series = seriesDetails.value

                    Row(
                        Modifier
                            .fillMaxWidth()
                            .padding(start = 20.dp, top = 30.dp, end = 20.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {

                        Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                            TextComponent(
                                text = if (isMovie == true) movie?.title.toString() else series?.name.toString(),
                                textColor = MaterialTheme.colorScheme.onBackground,
                                textSize = 22.sp,
                                modifier = Modifier.fillMaxWidth(0.8f),
                                fontWeight = FontWeight.Bold,
                                maxLines = 2,

                                )
                            Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {


                                (if (isMovie == true) movie?.release_date?.substring(
                                    0,
                                    4
                                ) else series?.first_air_date?.substring(0, 4))?.let {
                                    TextComponent(
                                        text = it,
                                        textColor = MaterialTheme.colorScheme.onBackground.copy(
                                            alpha = 0.7f
                                        ),
                                        textSize = 14.sp,
                                        modifier = Modifier,
                                        fontWeight = FontWeight.Normal
                                    )
                                }


                                if (isMovie == true) {
                                    movie?.runtime?.let {
                                        Utils.formatMinutesToHoursAndMinutes(
                                            it
                                        )
                                    }
                                        ?.let {
                                            TextComponent(
                                                text = it,
                                                textColor = MaterialTheme.colorScheme.onBackground.copy(
                                                    alpha = 0.7f
                                                ),
                                                textSize = 14.sp,
                                                modifier = Modifier,
                                                fontWeight = FontWeight.Normal
                                            )
                                        }
                                }

                            }
                        }

                        ElevatedCard(
                            onClick = {
                                //TODO: Add to watchlist functionality
                            },
                            colors = CardDefaults.elevatedCardColors(
                                containerColor = Color(
                                    0xfffe6d8e
                                )
                            ),
                            shape = customShapeAllCorners
                        ) {
                            Icon(
                                imageVector = Icons.TwoTone.Add,
                                contentDescription = null,
                                modifier = Modifier
                                    .padding(5.dp)
                                    .size(32.dp),
                                tint = Color.White
                            )

                        }
                    }

                    LazyRow(
                        Modifier
                            .padding(horizontal = 20.dp, vertical = 10.dp)
                            .fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {

                        val ite: List<*>? = if (isMovie == true) movie?.genres else series?.genres

                        items(ite ?: emptyList()) { item ->
                            when (item) {
                                is Genre -> {
                                    // Handle Genre for movies
                                    OutlinedCard(shape = customShapeAllCorners) {
                                        TextComponent(
                                            text = item.name.toString(),
                                            modifier = Modifier.padding(
                                                vertical = 5.dp,
                                                horizontal = 8.dp
                                            ),
                                            textColor = MaterialTheme.colorScheme.onBackground,
                                            textSize = 14.sp
                                        )
                                    }
                                }

                                is com.jasmeet.cinemate.data.apiResponse.remote.tvSeries.seriesDetails.Genre -> {
                                    // Handle Genre for TV Series
                                    OutlinedCard(shape = customShapeAllCorners) {
                                        TextComponent(
                                            text = item.name.toString(),
                                            modifier = Modifier.padding(
                                                vertical = 5.dp,
                                                horizontal = 8.dp
                                            ),
                                            textColor = MaterialTheme.colorScheme.onBackground,
                                            textSize = 14.sp
                                        )
                                    }
                                }
                            }
                        }


                    }

                    TextComponent(
                        text = "Over-View",
                        textColor = MaterialTheme.colorScheme.onBackground,
                        textSize = 20.sp,
                        modifier = Modifier
                            .padding(horizontal = 20.dp, vertical = 10.dp)
                            .fillMaxWidth(),
                        fontWeight = FontWeight.Bold,
                        maxLines = 2,

                        )
                    Text(
                        text = if (isMovie == true) movie?.overview.toString() else series?.overview.toString(),
                        color = MaterialTheme.colorScheme.onBackground.copy(0.7f),
                        fontSize = 16.sp,
                        modifier = Modifier
                            .padding(horizontal = 20.dp)
                            .fillMaxWidth(),
                        fontWeight = FontWeight.Normal,
                        fontFamily = libreBaskerville

                    )

                    TextComponent(
                        text = "Cast & Crew",
                        textColor = MaterialTheme.colorScheme.onBackground,
                        textSize = 20.sp,
                        modifier = Modifier
                            .padding(horizontal = 20.dp, vertical = 15.dp)
                            .fillMaxWidth(),
                        fontWeight = FontWeight.Bold,
                        maxLines = 2,

                        )

                    LazyRow(
                        Modifier
                            .fillMaxWidth()
                            .padding(start = 20.dp, end = 20.dp, bottom = 15.dp)
                            .navigationBarsPadding(),
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {

                        val combinedList = mutableListOf<Any>()
                        combinedList.addAll(castDetails.value?.cast?.filter { it.profile_path != null }
                            ?: emptyList())
                        combinedList.addAll(castDetails.value?.crew?.filter { it.profile_path != null }
                            ?: emptyList())

                        items(
                            combinedList,
                            key = {
                                it.toString()
                            }
                        ) {

                            when (it) {
                                is Cast -> {
                                    val profileUrl =
                                        Utils.getImageLinkWithSize(it.profile_path, ImgSize.W1280)

                                        Column(verticalArrangement = Arrangement.Center, modifier = Modifier.width(95.dp)){
                                            CoilImage(
                                                imageModel = { profileUrl },
                                                modifier = Modifier
                                                    .size(90.dp)
                                                    .clip(CircleShape),
                                                component = rememberImageComponent {
                                                    +ShimmerPlugin(
                                                        Shimmer.Resonate(
                                                            baseColor = Color.Black,
                                                            highlightColor = Color.LightGray,
                                                        ),
                                                    )
                                                    +CircularRevealPlugin(
                                                        duration = 350
                                                    )
                                                },
                                                imageOptions = ImageOptions(contentScale = ContentScale.FillBounds),

                                                )
                                            TextComponent(
                                                text = it.name.toString(),
                                                textColor = Color.White,
                                                maxLines = 2,
                                                textSize = 14.sp,
                                                fontWeight = FontWeight.Bold,
                                                modifier = Modifier.fillMaxWidth(),
                                                textAlign = TextAlign.Center
                                            )
                                        }
                                }

                                is Crew -> {
                                    val profileUrl =
                                        Utils.getImageLinkWithSize(it.profile_path, ImgSize.W1280)

                                    CoilImage(
                                        imageModel = { profileUrl },
                                        modifier = Modifier
                                            .size(90.dp)
                                            .clip(CircleShape),
                                        component = rememberImageComponent {
                                            +ShimmerPlugin(
                                                Shimmer.Resonate(
                                                    baseColor = Color.Black,
                                                    highlightColor = Color.LightGray,
                                                ),
                                            )
                                            +CircularRevealPlugin(
                                                duration = 350
                                            )
                                        },
                                        imageOptions = ImageOptions(contentScale = ContentScale.FillBounds),
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
