package com.jasmeet.cinemate.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.jasmeet.cinemate.presentation.appComponents.CustomTab
import com.jasmeet.cinemate.presentation.appComponents.ImageRow
import com.jasmeet.cinemate.presentation.appComponents.OverLayLayout
import com.jasmeet.cinemate.presentation.viewModel.SplashScreenViewModel
import com.jasmeet.cinemate.presentation.viewModel.WindowSizeViewModel

@Composable
fun SplashScreen(

    navController: NavHostController,
    windowSize: WindowSizeClass
) {


    val splashScreenViewModel: SplashScreenViewModel = viewModel()
    val vm: WindowSizeViewModel = viewModel()

    val tabs = listOf("Login", "Signup")
    var selectedTabIndex by rememberSaveable {
        mutableIntStateOf(0)
    }
    val screenOrientation = LocalConfiguration.current.orientation



    ConstraintLayout(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xff212121))
    ) {

        val (imgLayout, overlay, loginLayout) = createRefs()

        ImageLayout(
            v = splashScreenViewModel,
            modifier = Modifier.constrainAs(imgLayout) {
                top.linkTo(parent.top)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            },
            windowSize = windowSize
        )
        OverLayLayout(
            modifier = Modifier
                .constrainAs(overlay) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    bottom.linkTo(imgLayout.bottom)
                }
        )

        Row(
            Modifier.fillMaxWidth(.5f)
                .constrainAs(loginLayout) {
                    bottom.linkTo(imgLayout.bottom, margin = (30).dp)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)

                }
                .wrapContentWidth()
                .clip(RoundedCornerShape(8.dp))
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

    }






}

@Composable
private fun ImageLayout(
    v: SplashScreenViewModel,
    modifier: Modifier,
    windowSize: WindowSizeClass,
) {

    val vm: WindowSizeViewModel = viewModel()

    LaunchedEffect(Unit) {
        vm.fetchWindowSize()
    }

    val compactFirstRow by vm.compactFirstRow.collectAsState()
    val compactSecondRow by vm.compactSecondRow.collectAsState()
    val compactThirdRow by vm.compactThirdRow.collectAsState()


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
                .blur(0.6.dp),
            contentScale = ContentScale.FillBounds,
            modifierImg = Modifier.fillMaxHeight(if (
                windowSize.widthSizeClass == WindowWidthSizeClass.Compact || windowSize.widthSizeClass == WindowWidthSizeClass.Medium
            ) 0.3f else 0.33f).fillMaxWidth(),

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
                .blur(0.4.dp)
                .alpha(0.7f),
            contentScale = ContentScale.FillBounds,
            modifierImg = Modifier.fillMaxHeight(if (
                windowSize.widthSizeClass == WindowWidthSizeClass.Compact || windowSize.widthSizeClass == WindowWidthSizeClass.Medium
            ) 0.3f else 0.33f).fillMaxWidth(),
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
                .blur(0.6.dp),
            modifierImg = Modifier.fillMaxHeight(if (
                windowSize.widthSizeClass == WindowWidthSizeClass.Compact || windowSize.widthSizeClass == WindowWidthSizeClass.Medium
            ) 0.3f else 0.33f).fillMaxWidth(),
            contentScale = ContentScale.FillBounds,

            )
    }
}


private fun calculateAlpha(index: Int, totalImages: Int): Float {
    return when (index) {
        0, totalImages - 1 -> 0.7f
        else -> 0.8f
    }
}

