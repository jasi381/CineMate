package com.jasmeet.cinemate.presentation

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.jasmeet.cinemate.presentation.appComponents.CustomTab
import com.jasmeet.cinemate.presentation.appComponents.ImageRow
import com.jasmeet.cinemate.presentation.viewModel.WindowSizeViewModel


@Composable
fun LoginScreen(
    navController: NavHostController,
    windowSize: WindowSizeClass
) {
    val tabs = listOf("Login", "Signup")
    var selectedTabIndex by rememberSaveable {
        mutableIntStateOf(0)
    }

    ConstraintLayout(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xff212121))
    ) {

        val (imgLayout,loginSlider, loginLayout) = createRefs()

        ImageLayout(
            modifier = Modifier.constrainAs(imgLayout) {
                top.linkTo(parent.top)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            },
            windowSize = windowSize
        )

        Row(
            Modifier
                .fillMaxWidth(.5f)
                .constrainAs(loginSlider) {
                    bottom.linkTo(imgLayout.bottom, margin = (10).dp)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
                .wrapContentWidth()
                .clip(RoundedCornerShape(topStart = 8.dp, topEnd = 8.dp))
                .background(color = Color(0xff333336))
                .height(50.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            tabs.forEachIndexed { index, tab ->
                CustomTab(
                    text = tab, selectedTabIndex == index, index
                ) {
                    selectedTabIndex = it
                }
            }

        }

        AnimatedContent(
            targetState = selectedTabIndex,
            modifier = Modifier
                .fillMaxWidth(0.5f)
                .fillMaxHeight(0.4f)
                .constrainAs(loginLayout) {
                    top.linkTo(loginSlider.bottom)
                    start.linkTo(loginSlider.start)
                    end.linkTo(loginSlider.end)
                }, label = "",
            transitionSpec ={
                slideInHorizontally (animationSpec = tween(400),
                    initialOffsetX = {
                        if (targetState == 0) it else -it
                    },

                    ) togetherWith
                        fadeOut(animationSpec = tween(50))
            }
        ) { targetTabIndex ->
            Surface(
                color = Color(0xff333336),
                modifier = Modifier
                    .fillMaxWidth(0.5f)
                    .fillMaxHeight(0.4f),
                shape = RoundedCornerShape(bottomEnd = 8.dp, bottomStart = 8.dp)
            ) {
                when (targetTabIndex) {
                    0 -> Box(
                        Modifier
                            .fillMaxSize()
                            .background(Color.White)
                    ) {
                        Text(text = "Hello world", modifier = Modifier.padding(5.dp))
                    }
                    1 ->
                        Box(
                            Modifier
                                .fillMaxSize()
                                .background(Color.Cyan)
                        ) {
                            Text(text = "Hello Bro", modifier = Modifier.padding(5.dp))
                        }
                }
            }
        }
    }

}

@Composable
private fun ImageLayout(
    modifier: Modifier,
    windowSize: WindowSizeClass,
    windowSizeViewModel: WindowSizeViewModel = hiltViewModel()
) {



    val compactFirstRow by windowSizeViewModel.firstRowImages.collectAsState()
    val compactSecondRow by windowSizeViewModel.secondRowImages.collectAsState()
    val compactThirdRow by windowSizeViewModel.thirdRowImages.collectAsState()

    val imageHeight = calculateImageHeight(windowSize.widthSizeClass)


    ConstraintLayout(
        modifier = modifier
            .fillMaxWidth()
            .fillMaxHeight(0.4f)

    ) {
        val (firstRow, secondRow, thirdRow) = createRefs()

        ImageRow(
            imageRow = compactFirstRow.take(if (windowSize.widthSizeClass == WindowWidthSizeClass.Compact) 3 else 4),
            alpha = { index -> calculateAlpha(index, compactFirstRow.size) },
            modifier = Modifier
                .constrainAs(firstRow) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }

                .fillMaxWidth()
                .alpha(0.8f)
                .blur(0.5.dp),
            contentScale = ContentScale.FillBounds,
            modifierImg = Modifier
                .fillMaxHeight(imageHeight)
                .fillMaxWidth(),

            )

        ImageRow(
            imageRow = compactSecondRow.take(if (windowSize.widthSizeClass == WindowWidthSizeClass.Compact) 2 else 3),
            alpha = { index -> calculateAlpha(index, compactSecondRow.size) },
            modifier = Modifier
                .constrainAs(secondRow) {
                    top.linkTo(firstRow.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
                .blur(0.5.dp)
                .alpha(0.7f),
            contentScale = ContentScale.FillBounds,
            modifierImg = Modifier
                .fillMaxHeight(imageHeight)
                .fillMaxWidth(),
        )

        ImageRow(
            imageRow = compactThirdRow.take(if (windowSize.widthSizeClass == WindowWidthSizeClass.Compact) 3 else 4),
            alpha = { index -> calculateAlpha(index, compactThirdRow.size) },
            modifier = Modifier
                .constrainAs(thirdRow) {
                    top.linkTo(secondRow.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
                .alpha(0.6f)
                .blur(0.5.dp),
            modifierImg = Modifier
                .fillMaxHeight(imageHeight)
                .fillMaxWidth(),
            contentScale = ContentScale.FillBounds,

            )
    }
}


private fun calculateImageHeight(widthSizeClass: WindowWidthSizeClass): Float {
    return if (widthSizeClass == WindowWidthSizeClass.Compact || widthSizeClass == WindowWidthSizeClass.Medium) {
        0.3f
    } else {
        0.33f
    }
}

private fun calculateAlpha(index: Int, totalImages: Int): Float {
    return when (index) {
        0, totalImages - 1 -> 0.6f
        else -> 0.5f
    }
}

