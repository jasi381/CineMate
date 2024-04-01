package com.jasmeet.cinemate.presentation.viewModel

import android.content.Context
import android.os.Bundle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ListenerRegistration
import com.jasmeet.cinemate.presentation.utils.Utils
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WindowSizeViewModel @Inject constructor (
    private val auth : FirebaseAuth,
    private val db : FirebaseFirestore,
    @ApplicationContext private val context: Context

)  : ViewModel() {


    private val _firstRowImages = MutableStateFlow<List<String>>(emptyList())
    val firstRowImages: StateFlow<List<String>> = _firstRowImages

    private val _secondRowImages = MutableStateFlow<List<String>>(emptyList())
    val secondRowImages: StateFlow<List<String>> = _secondRowImages

    private val _thirdRowImages = MutableStateFlow<List<String>>(emptyList())
    val thirdRowImages: StateFlow<List<String>> = _thirdRowImages

    private var listenerRegistration: ListenerRegistration? = null


    init {
        listenForChanges()
    }

    private fun listenForChanges() {
        viewModelScope.launch(Dispatchers.IO) {

            val docRef = db.collection("splash_img").document("window_size")

            listenerRegistration = docRef.addSnapshotListener { snapshot, exception ->
                if (exception != null) {
                    Utils.logEvent(
                        "SplashScreen View-Model Error",
                        Bundle().apply {
                            putString("error", exception.message)
                        },
                        context

                    )
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

                    Utils.logEvent(
                        "SplashScreen View Model Success",
                        Bundle().apply {
                            putString("success", "Current data is not null")
                        },
                        context
                    )

                } else {
                    Utils.logEvent(
                        "SplashScreen View Model Error",
                        Bundle().apply {
                            putString("error", "Current data is null")
                        },
                        context
                    )
                }
            }
        }
    }
    override fun onCleared() {
        super.onCleared()
        listenerRegistration?.remove()
    }
}
