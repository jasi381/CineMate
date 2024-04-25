package com.jasmeet.cinemate.presentation.appComponents.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.jasmeet.cinemate.R
import com.jasmeet.cinemate.data.apiResponse.remote.movies.trendingMovies.Result
import com.jasmeet.cinemate.presentation.theme.customShapeAllCorners
import com.jasmeet.cinemate.presentation.theme.libreBaskerville
import com.jasmeet.cinemate.presentation.utils.ImgSize
import com.jasmeet.cinemate.presentation.utils.Utils
import com.skydoves.landscapist.animation.circular.CircularRevealPlugin
import com.skydoves.landscapist.coil.CoilImage
import com.skydoves.landscapist.components.rememberImageComponent
import com.skydoves.landscapist.placeholder.shimmer.Shimmer
import com.skydoves.landscapist.placeholder.shimmer.ShimmerPlugin

@Composable
fun MovieCarouselBox(it: Result) {

    val imgUrl = Utils.getImageLinkWithSize(it.backdrop_path,ImgSize.W780)

    Box(Modifier.clip(customShapeAllCorners)) {
        CoilImage(
            imageModel = { imgUrl },
            modifier = Modifier
                .height(
                    dimensionResource(id = R.dimen.home_grid_poster_height)
                )
                .fillMaxWidth(),
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
        )

        Text(
            text = it.title,
            color = Color.White,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis,
            fontFamily = libreBaskerville,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier
                .align(Alignment.BottomStart)
                .background(Color.Black.copy(alpha = 0.3f), RoundedCornerShape(topEnd = 12.dp))
                .padding(
                    horizontal = dimensionResource(id = R.dimen.normal_padding),
                    vertical = dimensionResource(id = R.dimen.small_padding)
                )
        )
    }

}