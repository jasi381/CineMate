package com.jasmeet.cinemate.presentation.screens

import android.content.pm.ActivityInfo
import android.os.Build
import android.view.WindowInsets
import androidx.activity.ComponentActivity
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.FilledTonalIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.vectorResource
import androidx.navigation.NavHostController
import com.jasmeet.cinemate.R
import com.jasmeet.cinemate.presentation.appComponents.components.VideoView

@RequiresApi(Build.VERSION_CODES.R)
@Composable
fun VideoScreen(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    id: String?
) {



    val activity = LocalContext.current as? ComponentActivity
    val requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE

    LaunchedEffect(key1 = "lockOrientation") {
        activity?.requestedOrientation = requestedOrientation
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            activity?.window?.insetsController?.apply {
                hide(WindowInsets.Type.statusBars())
                hide(WindowInsets.Type.navigationBars())

            }
        }

    }
    DisposableEffect(key1 = "unlockOrientation") {
        onDispose {
            // Reset the orientation when the composable is removed
            activity?.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED
            activity?.window?.insetsController?.apply {
                show(WindowInsets.Type.statusBars())
                show(WindowInsets.Type.navigationBars())

            }
        }
    }



    Box(
        modifier = Modifier
            .background(Color(0x1A000000))
            .fillMaxSize()
    ) {
        // VideoView
        Box(Modifier.safeDrawingPadding()){id?.let { VideoView(id = it, lifecycleOwner = LocalLifecycleOwner.current) }}

        // Back Button
        FilledTonalIconButton(
            onClick = {
                navController.popBackStack()
            },
            modifier = Modifier
                .align(Alignment.TopStart)
                .statusBarsPadding(),
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


}