package com.jasmeet.cinemate.presentation.screens

import android.util.Log
import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.platform.SoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavOptions
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.jasmeet.cinemate.R
import com.jasmeet.cinemate.presentation.appComponents.CustomTab
import com.jasmeet.cinemate.presentation.appComponents.ImageRow
import com.jasmeet.cinemate.presentation.appComponents.LoadingButton
import com.jasmeet.cinemate.presentation.appComponents.OrDivider
import com.jasmeet.cinemate.presentation.appComponents.PasswordFieldComponent
import com.jasmeet.cinemate.presentation.appComponents.TextComponent
import com.jasmeet.cinemate.presentation.appComponents.TextFieldComponent
import com.jasmeet.cinemate.presentation.authLauncher.rememberFirebaseAuthLauncher
import com.jasmeet.cinemate.presentation.theme.customShapeAllCorners
import com.jasmeet.cinemate.presentation.theme.customShapeBottomCorners
import com.jasmeet.cinemate.presentation.theme.customShapeTopCorners
import com.jasmeet.cinemate.presentation.theme.libreBaskerville
import com.jasmeet.cinemate.presentation.utils.Utils
import com.jasmeet.cinemate.presentation.viewModel.SignInViewModel
import com.jasmeet.customtoast.main.ToastMagic
import com.jasmeet.customtoast.utils.Utils.CustomToast
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch


@Composable
fun LoginScreen(
    navController: NavHostController,
    windowSize: WindowSizeClass,
    signInViewModel: SignInViewModel = hiltViewModel()
) {

    val focusManager = LocalFocusManager.current
    val keyboardController = LocalSoftwareKeyboardController.current
    val context = LocalContext.current

    val height = LocalConfiguration.current.screenHeightDp.dp


    val token = stringResource(R.string.default_web_client_id)
    val gso = remember {
        GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(token)
            .requestEmail()
            .requestProfile()
            .build()
    }

    val googleSignInClient = remember {
        GoogleSignIn.getClient(context, gso)
    }
    val tabs = listOf("Login", "Signup")
    var selectedTabIndex by rememberSaveable {
        mutableIntStateOf(0)
    }

    var googleLoading by rememberSaveable {
        mutableStateOf(false)
    }

    val errorMessage by signInViewModel.errorState.collectAsState()

    if (errorMessage != null && errorMessage?.isNotEmpty() == true) {
        CustomToast(
            message = errorMessage ?: "Something went wrong !",
            iconColor = Color.White,
            textColor = Color.White,
            icon = painterResource(id = R.drawable.ic_error),
            backgroundColor = Color(0xfff44336),
            fontFamily = libreBaskerville,
            textSize = 14.sp,
            duration = Toast.LENGTH_SHORT,
            shape = customShapeAllCorners
        )
    }


    BackHandler {
        if (selectedTabIndex ==1 ) {
            selectedTabIndex = 0
        }

        if (selectedTabIndex ==0) {
            navController.popBackStack()
        }

    }

    val launcher = rememberFirebaseAuthLauncher(
        onAuthComplete = {result ->
            googleLoading = false
            signInViewModel.saveData(result)
            val navOptions = NavOptions.Builder()
                .setPopUpTo(Screens.Login.route,inclusive = true)
                .build()

            navController.navigate(Screens.Home.route,navOptions)


        },
        onAuthError = { error ->
            googleLoading = false
            error.let {
                signInViewModel.setErrorMessage(it)
            }

        }
    )


    ConstraintLayout(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xff131313))
    ) {

        val (imgLayout, loginSlider, loginLayout,dashedDivider,googleBtn) = createRefs()

        ImageLayout(
            modifier = Modifier
                .constrainAs(imgLayout) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                },
            windowSize = windowSize,
            signInViewModel = signInViewModel
        )

        Row(
            Modifier
                .fillMaxWidth(.85f)
                .constrainAs(loginSlider) {
                    bottom.linkTo(imgLayout.bottom, margin = height * 0.08f)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }

                .clip(customShapeTopCorners)
                .background(
                    color = Color(0xff333336),
                    shape = customShapeTopCorners
                )
                .height(50.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            tabs.forEachIndexed { index, tab ->
                CustomTab(
                    text = tab,
                    isSelected = selectedTabIndex == index,
                    index
                ) {
                    selectedTabIndex = it
                }
            }

        }

        AnimatedContent(
            targetState = selectedTabIndex,
            modifier = Modifier
                .clip(customShapeBottomCorners)
                .fillMaxWidth(0.85f)
                .wrapContentHeight()
                .constrainAs(loginLayout) {
                    top.linkTo(loginSlider.bottom)
                    start.linkTo(loginSlider.start)
                    end.linkTo(loginSlider.end)
                },
            label = "",
            transitionSpec = {
                slideInHorizontally(
                    animationSpec = tween(500, easing = FastOutSlowInEasing),
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
                    .wrapContentHeight(),

                shape = customShapeBottomCorners
            ) {
                when (targetTabIndex) {
                    0 -> LoginUi(
                        focusManager = focusManager,
                        keyboardController = keyboardController,
                        navController = navController,
                        signInViewModel = signInViewModel

                    )
                    1 -> SignupUi(
                        focusManager = focusManager,
                        keyboardController = keyboardController,
                        navController = navController,
                        signInViewModel = signInViewModel

                    )

                }
            }
        }
        OrDivider(

            modifier = Modifier
                .fillMaxWidth(0.85f)
                .constrainAs(dashedDivider) {
                    top.linkTo(loginLayout.bottom, margin = 20.dp)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
        )
        LoadingButton(
            onClick = {
                googleLoading = true
                launcher.launch(googleSignInClient.signInIntent)
            },
            loading = googleLoading,
            modifier = Modifier
                .padding(horizontal = 10.dp)
                .fillMaxWidth(0.85f)
                .constrainAs(googleBtn) {
                    top.linkTo(dashedDivider.bottom, margin = 25.dp)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                },
            backgroundColor = Color.White,
            textColor = Color.Black,
            content = {
                Row(
                    Modifier
                        .fillMaxWidth()
                        .padding(vertical = 3.2.dp),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.ic_google),
                        contentDescription = "Google",
                        modifier = Modifier.size(25.dp)
                    )
                    Spacer(modifier = Modifier.width(15.dp))
                    Text(
                        text = "Continue with Google",
                        fontFamily = libreBaskerville,
                        fontSize = 17.sp,
                        fontWeight = FontWeight.SemiBold
                    )


                }
            }
        )



    }

}


@Composable
private fun ImageLayout(
    modifier: Modifier,
    windowSize: WindowSizeClass,
    signInViewModel: SignInViewModel
) {


    val compactFirstRow by signInViewModel.firstRowImages.collectAsState()
    val compactSecondRow by signInViewModel.secondRowImages.collectAsState()
    val compactThirdRow by signInViewModel.thirdRowImages.collectAsState()

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
        0.25f
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


@Composable
fun LoginUi(
    keyboardController: SoftwareKeyboardController?,
    focusManager: FocusManager,
    navController: NavHostController,
    signInViewModel: SignInViewModel
) {

    var email by rememberSaveable { mutableStateOf("") }
    var password by rememberSaveable { mutableStateOf("") }
    val loading by signInViewModel.isLoading.collectAsState()

    val errorMessage by signInViewModel.errorState.collectAsState()
    val coroutine = rememberCoroutineScope()

    if (errorMessage != null && errorMessage?.isNotEmpty() == true) {
        CustomToast(
            message = errorMessage ?: "Something went wrong !",
            iconColor = Color.White,
            textColor = Color.White,
            icon = painterResource(id = R.drawable.ic_error),
            backgroundColor = Color(0xfff44336),
            fontFamily = libreBaskerville,
            textSize = 14.sp,
            duration = Toast.LENGTH_SHORT,
            shape = customShapeAllCorners
        )
    }



    Column(
        Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .background(Color(0xff212121))
            .padding(horizontal = 5.dp)

    ) {

        TextComponent(
            text = "Email",
            fontFamily = libreBaskerville,
            modifier = Modifier.padding(top = 15.dp, start = 10.dp, bottom = 8.dp),
            textColor = Color.White,
            textSize = 15.sp
        )
        TextFieldComponent(
            value = email,
            onValueChange = {
                email = it
            },
            enabled = !loading,
            readyOnly = loading,
            labelValue = "Enter your email",
            fontSize = 15.sp,
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 10.dp, end = 10.dp, bottom = 15.dp),
            keyboardType = KeyboardType.Email
        )


        TextComponent(
            text = "Password",
            fontFamily = libreBaskerville,
            modifier = Modifier.padding(top = 10.dp, start = 10.dp, bottom = 8.dp),
            textColor = Color.White,
            textSize = 15.sp
        )
        PasswordFieldComponent(
            value = password,
            onValueChange = {
                password = it
            },
            enabled = !loading,
            readyOnly = loading,
            fontSize = 15.sp,
            labelValue = "Enter your password",
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 10.dp, end = 10.dp, bottom = 22.dp),
            keyboardActions = KeyboardActions(
                onDone = {
                    keyboardController?.hide()
                    focusManager.clearFocus()
                    validateAndInitiateSignIn(email, password, signInViewModel, coroutine, navController)

                }
            )
        )

        LoadingButton(
            onClick = {
                validateAndInitiateSignIn(email, password, signInViewModel, coroutine, navController)
            },
            loading = loading,
            text = "Login",
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 10.dp),

            )
        Spacer(modifier = Modifier.height(15.dp))


    }

}

private fun validateAndInitiateSignIn(
    email: String,
    password: String,
    signInViewModel: SignInViewModel,
    coroutine: CoroutineScope,
    navController: NavHostController
) {
    if (Utils.validateEmail(email) && Utils.validatePassword(password)) {
        signInViewModel.loginWithEmailPassword(email, password)
        initiateSignInFlow(coroutine, signInViewModel, navController)
    } else if (email.isEmpty()) {
        signInViewModel.setErrorMessage("Email is Empty")
    } else if (password.isEmpty()) {
        signInViewModel.setErrorMessage("Password is Empty")
    } else if (!Utils.validateEmail(email)) {
        signInViewModel.setErrorMessage("Invalid Email! Please enter a valid email")
    } else if (!Utils.validatePassword(password)) {
        signInViewModel.setErrorMessage("Invalid Password! Please enter a valid password")
    }
}

private fun initiateSignInFlow(
    coroutine: CoroutineScope,
    signInViewModel: SignInViewModel,
    navController: NavHostController
) {
    coroutine.launch {
        signInViewModel.stateFlow.collect {
            if (it) {
                val navOptions = NavOptions.Builder()
                    .setPopUpTo(Screens.Login.route, inclusive = true)
                    .build()
                navController.navigate(Screens.Home.route, navOptions)
            }
        }

    }
}


@Composable
fun SignupUi(
    keyboardController: SoftwareKeyboardController?,
    focusManager: FocusManager,
    navController: NavHostController,
    signInViewModel: SignInViewModel
) {

    var email by rememberSaveable { mutableStateOf("") }
    var password by rememberSaveable { mutableStateOf("") }
    val loading by signInViewModel.isLoading.collectAsState()
    val errorMessage by signInViewModel.errorState.collectAsState()
    val coroutine = rememberCoroutineScope()

    if (errorMessage != null && errorMessage?.isNotEmpty() == true) {
        CustomToast(
            message = errorMessage ?: "Something went wrong !",
            iconColor = Color.White,
            textColor = Color.White,
            icon = painterResource(id = R.drawable.ic_error),
            backgroundColor = Color(0xfff44336),
            fontFamily = libreBaskerville,
            textSize = 14.sp,
            duration = Toast.LENGTH_SHORT
        )
    }


    Column(
        Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .background(Color(0xff212121))
            .padding(horizontal = 5.dp)

    ) {
        TextComponent(
            text = "Email",
            fontFamily = libreBaskerville,
            modifier = Modifier.padding(top = 15.dp, start = 10.dp, bottom = 8.dp),
            textColor = Color.White,
            textSize = 15.sp
        )
        TextFieldComponent(
            value = email,
            onValueChange = {
                email = it

            },
            labelValue = "Enter your email",
            fontSize = 15.sp,
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 10.dp, end = 10.dp, bottom = 15.dp),
            enabled = !loading,
            readyOnly = loading,
            keyboardType = KeyboardType.Email
        )


        TextComponent(
            text = "Password",
            fontFamily = libreBaskerville,
            modifier = Modifier.padding(top = 10.dp, start = 10.dp, bottom = 8.dp),
            textColor = Color.White,
            textSize = 15.sp
        )
        PasswordFieldComponent(
            value = password,
            onValueChange = {
                password = it
            },
            fontSize = 15.sp,
            labelValue = "Enter your password",
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 10.dp, end = 10.dp, bottom = 22.dp),
            enabled = !loading,
            readyOnly = loading,
            keyboardActions = KeyboardActions(
                onDone = {
                    keyboardController?.hide()
                    focusManager.clearFocus()
                    validateAndInitiateSignup(email, password, signInViewModel, coroutine, navController)
                }
            )
        )

        LoadingButton(
            onClick = {
                validateAndInitiateSignup(email, password, signInViewModel, coroutine, navController)
            },
            loading = loading,
            text = "SignUp",
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 10.dp),
        )

        Spacer(modifier = Modifier.height(15.dp))
    }

}

private fun validateAndInitiateSignup(
    email: String,
    password: String,
    signInViewModel: SignInViewModel,
    coroutine: CoroutineScope,
    navController: NavHostController
) {
    if (Utils.validateEmail(email) && Utils.validatePassword(password)) {
        signInViewModel.signUpEmailPassword(email, password)
        initiateSignUpFlow(coroutine, signInViewModel, navController)
    } else if (email.isEmpty()) {
        signInViewModel.setErrorMessage("Email is Empty")
    } else if (password.isEmpty()) {
        signInViewModel.setErrorMessage("Password is Empty")
    } else if (!Utils.validateEmail(email)) {
        signInViewModel.setErrorMessage("Invalid Email! Please enter a valid email")
    } else if (!Utils.validatePassword(password)) {
        signInViewModel.setErrorMessage("Invalid Password! Please enter a valid password")
    }
}

private fun initiateSignUpFlow(
    coroutine: CoroutineScope,
    signInViewModel: SignInViewModel,
    navController: NavHostController
) {
    coroutine.launch {
        signInViewModel.stateFlow.collect {
            if (it) {
                val navOptions = NavOptions.Builder()
                    .setPopUpTo(Screens.Login.route, inclusive = true)
                    .build()
                navController.navigate(Screens.Home.route, navOptions)
            }
        }

    }
}


