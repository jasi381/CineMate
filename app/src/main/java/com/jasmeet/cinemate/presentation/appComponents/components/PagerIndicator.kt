package com.jasmeet.cinemate.presentation.appComponents.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.jasmeet.cinemate.presentation.theme.customShapeAllCorners


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun PagerIndicator(
    modifier: Modifier = Modifier,
    pagerState: PagerState,
    itemCount: Int,
    indicatorColor: Color = Color.LightGray,
    selectedIndicatorColor: Color = Color(0xffE50914),
    indicatorSize: Dp = 8.dp,
    indicatorSpacing: Dp = 8.dp,
) {
    Box(
        modifier = modifier
            .padding(vertical = indicatorSpacing)
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(indicatorSpacing),
            modifier = Modifier.padding(horizontal = indicatorSpacing)
        ) {
            repeat(itemCount) { index ->
                val isSelected = index == pagerState.currentPage
                val targetColor = if (isSelected) selectedIndicatorColor else indicatorColor
                val targetSize = if (isSelected) indicatorSize * 2 else indicatorSize

                val color = animateColorAsState(
                    targetValue = targetColor,
                    animationSpec = spring(),
                    label = ""
                ).value

                val size = animateDpAsState(
                    targetValue = targetSize,
                    animationSpec = spring(),
                    label = ""
                ).value

                Box(
                    modifier = Modifier
                        .size(size, indicatorSize)
                        .clip(if (isSelected) customShapeAllCorners else CircleShape)
                        .background(color)
                )
            }
        }
    }
}

