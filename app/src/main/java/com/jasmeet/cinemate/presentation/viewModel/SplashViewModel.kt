package com.jasmeet.cinemate.presentation.viewModel

import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject


@HiltViewModel
class SplashViewModel @Inject constructor(
    private val auth :FirebaseAuth
):ViewModel() {
    val isUserLoggedIn: MutableStateFlow<Boolean?> = MutableStateFlow(null)
    fun checkForActiveSession() {
        isUserLoggedIn.value = auth.currentUser != null
    }
}