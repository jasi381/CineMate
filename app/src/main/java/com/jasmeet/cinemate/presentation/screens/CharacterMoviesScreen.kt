package com.jasmeet.cinemate.presentation.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.jasmeet.cinemate.presentation.appComponents.TextComponent
import com.jasmeet.cinemate.presentation.appComponents.extensions.customClickable
import com.jasmeet.cinemate.presentation.utils.ImgSize
import com.jasmeet.cinemate.presentation.utils.Utils
import com.jasmeet.cinemate.presentation.viewModel.movies.CharacterMoviesViewModel
import com.skydoves.landscapist.ImageOptions
import com.skydoves.landscapist.animation.circular.CircularRevealPlugin
import com.skydoves.landscapist.coil.CoilImage
import com.skydoves.landscapist.components.rememberImageComponent
import com.skydoves.landscapist.placeholder.shimmer.Shimmer
import com.skydoves.landscapist.placeholder.shimmer.ShimmerPlugin

@Composable
fun CharacterMoviesScreen(
    navController: NavHostController,
    id: String?,
    characterMoviesViewModel: CharacterMoviesViewModel = hiltViewModel(),
    name: String?,
    img: String?
) {

    val imgUrl = Utils.getImageLinkWithSize(img, ImgSize.W780)

    LaunchedEffect(Unit) {
        if (id != null) {
            characterMoviesViewModel.getCharacterMovies(id)
        }

    }

    val response = characterMoviesViewModel.characterMoviesResponse.observeAsState()



    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(horizontal = 20.dp)
    ) {
        Row(
            Modifier
                .padding(bottom = 10.dp)
                .fillMaxWidth()
                .statusBarsPadding(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            CoilImage(
                imageModel = { imgUrl },
                modifier = Modifier
                    .padding(horizontal = 15.dp)
                    .size(95.dp)
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
                text = name.toString(),
                textColor = MaterialTheme.colorScheme.onBackground,
                textSize = 22.sp,
                fontWeight = FontWeight.Bold
            )


        }

        LazyColumn(
            Modifier
                .fillMaxSize()
                .navigationBarsPadding()
                .weight(1f),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            items(response.value?.cast?.filter {
                it.poster_path != null
            } ?: emptyList()) {
                val posterUrl = Utils.getImageLinkWithSize(it.poster_path, ImgSize.W780)
                var expanded by remember { mutableStateOf(false) }

                ElevatedCard(
                    onClick = {
                        navController.navigate(Screens.Detail.passMoviesId(it.id.toString(), true))
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row {
                        CoilImage(
                            imageModel = { posterUrl },
                            modifier = Modifier
                                .padding(end = 15.dp)
                                .size(110.dp)
                                .aspectRatio(2 / 3f)
                        )
                        Column {
                            TextComponent(
                                text = it.title,
                                textColor = MaterialTheme.colorScheme.onBackground,
                                textSize = 20.sp,
                                fontWeight = FontWeight.Bold
                            )


                            AnimatedVisibility(
                                visible = !expanded,
                                enter = fadeIn() + expandVertically(),
                                exit = fadeOut() + shrinkVertically(),
                                modifier = Modifier.padding(5.dp)
                            ) {
                                Text(
                                    text = it.overview,
                                    color = MaterialTheme.colorScheme.onBackground.copy(0.7f),
                                    fontSize = 16.sp,
                                    modifier = Modifier
                                        .fillMaxWidth(),
                                    maxLines = 2,
                                    overflow = TextOverflow.Ellipsis
                                )
                            }
                            AnimatedVisibility(
                                visible = expanded,
                                enter = fadeIn() + expandVertically(),
                                exit = fadeOut() + shrinkVertically(),
                                modifier = Modifier.padding(5.dp)
                            ) {
                                Text(
                                    text = it.overview.orEmpty(),
                                    color = MaterialTheme.colorScheme.onBackground.copy(0.7f),
                                    fontSize = 16.sp,
                                    modifier = Modifier
                                        .fillMaxWidth(),
                                    maxLines = Int.MAX_VALUE, // Show all lines when expanded
                                    overflow = TextOverflow.Ellipsis
                                )
                            }

                            Text(
                                text = if (expanded) "Show Less" else "Show More",
                                color = MaterialTheme.colorScheme.primary,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier
                                    .customClickable {
                                        expanded = !expanded
                                    }
                                    .align(Alignment.End)
                                    .padding(horizontal = 8.dp, vertical = 5.dp)

                            )


                        }

                    }
                }

            }

        }

    }



}