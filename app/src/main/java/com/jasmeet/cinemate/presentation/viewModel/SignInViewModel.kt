package com.jasmeet.cinemate.presentation.viewModel

import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.auth.AuthResult
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ListenerRegistration
import com.jasmeet.cinemate.domain.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor (
    private val db : FirebaseFirestore,
    private val analytics :FirebaseAnalytics,
    private val userRepository: UserRepository
)  : ViewModel() {


    private val _firstRowImages = MutableStateFlow<List<String>>(emptyList())
    val firstRowImages: StateFlow<List<String>> = _firstRowImages

    private val _secondRowImages = MutableStateFlow<List<String>>(emptyList())
    val secondRowImages: StateFlow<List<String>> = _secondRowImages

    private val _thirdRowImages = MutableStateFlow<List<String>>(emptyList())
    val thirdRowImages: StateFlow<List<String>> = _thirdRowImages

    private var listenerRegistration: ListenerRegistration? = null

    private val _authState = MutableStateFlow<AuthResult?>(null)
    val authState: StateFlow<AuthResult?> = _authState

    private val _errorState = MutableStateFlow<String?>(null)
    val errorState: StateFlow<String?> = _errorState

    private val debounceTimeMillis = 5000L
    private var lastErrorShownTime = 0L

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val state = MutableStateFlow(false)
    val stateFlow: StateFlow<Boolean> = state



    init {
        listenForChanges()
        viewModelScope.launch {
            while (true) {
                delay(debounceTimeMillis)
                setErrorMessage(null)
            }
        }
    }

    private fun listenForChanges() {
        viewModelScope.launch(Dispatchers.IO) {

            val docRef = db.collection("splash_img").document("window_size")

            listenerRegistration = docRef.addSnapshotListener { snapshot, exception ->
                if (exception != null) {

                    try {
                        analytics.logEvent("splashScreen_view_model_error", Bundle().apply {
                            putString("error", exception.message)
                        })

                    }catch (e:Exception){
                        Log.e("Error", e.message.toString())
                    }


                    return@addSnapshotListener
                }

                if (snapshot != null && snapshot.exists()) {
                    val compact = snapshot.data?.get("Compact") as? Map<*, *>
                    val firstRow = compact?.get("First Row") as? List<*>
                    val secondRow = compact?.get("Second Row") as? List<*>
                    val thirdRow = compact?.get("Third Row") as? List<*>

                    viewModelScope.launch(Dispatchers.Main) {
                        _firstRowImages.value = firstRow?.map { it.toString() } ?: emptyList()
                        _secondRowImages.value = secondRow?.map { it.toString() } ?: emptyList()
                        _thirdRowImages.value = thirdRow?.map { it.toString() } ?: emptyList()
                    }

                    analytics.logEvent("splashScreen_view_model_success", Bundle().apply {
                        putString("success", "Images updated")
                    })



                } else {
                    analytics.logEvent("splashScreen_view_model_error", Bundle().apply {
                        putString("error", "Document does not exist")
                    })

                }
            }
        }
    }

    fun setErrorMessage(errorMessage: String?) {
        val currentTimeMillis = System.currentTimeMillis()
        if (errorMessage != null && (currentTimeMillis - lastErrorShownTime) >= debounceTimeMillis) {
            _errorState.value = errorMessage
            lastErrorShownTime = currentTimeMillis

        } else if (errorMessage == null) {
            _errorState.value = null
            lastErrorShownTime = 0L
        }
    }

    fun signUpEmailPassword(email: String, password: String) {
        viewModelScope.launch {
            try {
                _isLoading.value = true
                val result = userRepository.signUpWithEmailAndPassword(email, password)
                _authState.value = result
                userRepository.saveUserDataToFirestore(result)
                _isLoading.value = false
                state.value = true
            } catch (e: Exception) {
                setErrorMessage(e.message)
                _isLoading.value = false
                state.value = false
            }
        }
    }

    fun loginWithEmailPassword(email: String, password: String) {
        viewModelScope.launch {
            try {
                _isLoading.value = true
                val result = userRepository.loginWithEmailAndPassword(email, password)
                _authState.value = result
                _isLoading.value = false
                state.value = true
            } catch (e: Exception) {
                setErrorMessage(e.message)
                _isLoading.value = false
                state.value = false
            }
        }
    }


    override fun onCleared() {
        super.onCleared()
        listenerRegistration?.remove()
    }

}
