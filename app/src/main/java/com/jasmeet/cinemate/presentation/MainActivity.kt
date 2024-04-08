package com.jasmeet.cinemate.presentation

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.os.Bundle
import android.provider.Settings
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.jasmeet.cinemate.R
import com.jasmeet.cinemate.data.data.BottomNavigationItem
import com.jasmeet.cinemate.presentation.appComponents.BottomBar
import com.jasmeet.cinemate.presentation.appComponents.LottieComponent
import com.jasmeet.cinemate.presentation.navigation.CineMateNavigator
import com.jasmeet.cinemate.presentation.theme.CineMateTheme
import com.jasmeet.cinemate.presentation.theme.libreBaskerville
import com.jasmeet.cinemate.presentation.theme.sans
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        enableEdgeToEdge(
            statusBarStyle = SystemBarStyle.dark(android.graphics.Color.TRANSPARENT),
            navigationBarStyle = SystemBarStyle.dark(android.graphics.Color.TRANSPARENT)
        )

        setContent {
            CineMateTheme {
                val windowSize = calculateWindowSizeClass(activity = this)
                CineMateApp(windowSize = windowSize)

            }
        }
    }

}


@Composable
fun CineMateApp(
    windowSize: WindowSizeClass
) {

    val navController = rememberNavController()
    val context = LocalContext.current
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination
    val navigationItems = listOf(
        BottomNavigationItem.Home,
        BottomNavigationItem.Search,
        BottomNavigationItem.Wishlist,
        BottomNavigationItem.Profile
    )

    val bottomBarDestination = navigationItems.any {
        it.route == currentDestination?.route
    }


    var showNetworkDialog by remember { mutableStateOf(false) }

    DisposableEffect(Unit) {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkCallback = object : ConnectivityManager.NetworkCallback() {
            override fun onCapabilitiesChanged(
                network: Network,
                networkCapabilities: NetworkCapabilities
            ) {
                super.onCapabilitiesChanged(network, networkCapabilities)
                showNetworkDialog =
                    !networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)
            }

            override fun onLost(network: Network) {
                super.onLost(network)
                showNetworkDialog = true
            }

            override fun onUnavailable() {
                super.onUnavailable()
                showNetworkDialog = true
            }
        }
        connectivityManager.registerDefaultNetworkCallback(networkCallback)

        onDispose {
            connectivityManager.unregisterNetworkCallback(networkCallback)
        }
    }


    if (showNetworkDialog) {
        Box(
            Modifier
                .fillMaxSize()
                .background(Color(0xff131313)), contentAlignment = Alignment.Center
        ) {
            NetworkConnectionDialog2()
        }


    } else {
        Scaffold(
            bottomBar = {
                if (bottomBarDestination)
                    BottomBar(
                        navController = navController,
                        currentDestination = currentDestination,
                        screens = navigationItems
                    )
            }
        ) { paddingValues ->
            CineMateNavigator(
                windowSize = windowSize,
                navController = navController,
                paddingValues = paddingValues
            )
        }
    }
}


@Composable
fun NetworkConnectionDialog2() {

    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()

    var firstPressed by remember { mutableStateOf(false) }

    Dialog(
        onDismissRequest = {
            if (firstPressed) {
                val activity = (context as? Activity)
                activity?.finish()
            } else {
                firstPressed = true
                Toast.makeText(context, "Press back again to exit", Toast.LENGTH_SHORT).show()
                coroutineScope.launch {
                    delay(5000L)
                    firstPressed = false
                }
            }
        }
    ) {
        Surface(
            modifier = Modifier.padding(16.dp),
            shape = MaterialTheme.shapes.medium,
            color = Color(0xff363636),
            shadowElevation = 8.dp
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                LottieComponent(
                    rawRes = R.raw.lost_wifi,
                    modifier = Modifier
                        .size(160.dp, 150.dp)
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "Switch to Wi-Fi",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                    fontFamily = libreBaskerville,
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "For better performance or to access restricted content, switch to a Wi-Fi network.",
                    fontSize = 14.sp,
                    color = Color.White,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.widthIn(max = 280.dp),
                    fontFamily = libreBaskerville,
                )

                Spacer(modifier = Modifier.height(24.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {

                    TextButton(
                        onClick = {
                            val activity = (context as? Activity)
                            activity?.finish()
                        },
                    ) {
                        Text(
                            text = "Exit",
                            fontWeight = FontWeight.Bold,
                            color = Color.White,
                            fontFamily = sans
                        )
                    }

                    TextButton(
                        onClick = {
                            context.startActivity(Intent(Settings.ACTION_WIFI_SETTINGS))
                        },
                    ) {
                        Text(
                            text = "Switch to Wi-Fi",
                            fontWeight = FontWeight.Bold,
                            color = Color.White,
                            fontFamily = sans
                        )
                    }

                }
            }
        }
    }
}










