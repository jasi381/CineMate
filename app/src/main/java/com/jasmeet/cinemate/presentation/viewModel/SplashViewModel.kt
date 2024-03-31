package com.jasmeet.cinemate.presentation.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.Firebase
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ListenerRegistration
import com.google.firebase.firestore.firestore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await


class SplashScreenViewModel : ViewModel() {
    private val db = Firebase.firestore


    private val _mediumImages = MutableLiveData<List<String>>(emptyList())
    val mediumImages: LiveData<List<String>> = _mediumImages

    private val _expandedImages = MutableLiveData<List<String>>(emptyList())
    val expandedImages: LiveData<List<String>> = _expandedImages

    private val _compactImages = MutableLiveData<List<String>>(emptyList())
    val compactImages: LiveData<List<String>> = _compactImages


    private var listenerRegistration: ListenerRegistration? = null

    init {
        listenForChanges()
    }


    private fun listenForChanges() {
        val docRef = db.collection("splash_img").document("window_size")
        listenerRegistration = docRef.addSnapshotListener { snapshot, exception ->
            if (exception != null) {
                Log.d("SplashScreenViewModel", "Listen failed.", exception)
                return@addSnapshotListener
            }

            if (snapshot != null && snapshot.exists()) {
                val compactWindowImagesList  = snapshot.data?.get("Compact") as? List<*>
               val expandedWindowImagesList  = snapshot.data?.get("Expanded") as? List<*>
                val mediumWindowImagesList  = snapshot.data?.get("Medium")?.javaClass
               Log.d("SplashScreenViewModel", "Current data: ${mediumWindowImagesList}")



            } else {
                Log.d("SplashScreenViewModel", "Current data: null")
            }
        }
    }

    suspend fun getMediumImages()  {
        val docRef = db.collection("splash_img").document("window_size")
        val snapshot = docRef.get().await()

        val mediumImages = snapshot.get("Medium")



    }


    override fun onCleared() {
        super.onCleared()
        // Remove the snapshot listener when the ViewModel is cleared
        listenerRegistration?.remove()
    }
}
data class MediumImages(
    val firstRow : List<String>,
    val secondRow : List<String>,
    val thirdRow : List<String>
)




class WindowSizeViewModel : ViewModel() {
    private val db = FirebaseFirestore.getInstance()

    // StateFlow objects for the fetched data
    private val _compactFirstRow = MutableStateFlow<List<String>>(emptyList())
    private val _compactSecondRow = MutableStateFlow<List<String>>(emptyList())
    private val _compactThirdRow = MutableStateFlow<List<String>>(emptyList())


    // Expose StateFlow as immutable properties
    val compactFirstRow: StateFlow<List<String>> = _compactFirstRow
    val compactSecondRow: StateFlow<List<String>> = _compactSecondRow
    val compactThirdRow: StateFlow<List<String>> = _compactThirdRow

    fun fetchWindowSize() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val document = db.collection("splash_img").document("window_size").get().await()
                val compact = document["Compact"] as Map<*, *>
                val expanded = document["Expanded"] as Map<*, *>
                val medium = document["Medium"] as Map<*, *>



                val firstRow = compact["First Row"] as List<*>
                val secondRow = compact["Second Row"] as List<*>
                val thirdRow = compact["Third Row"] as List<*>

                _compactFirstRow.value = firstRow.map { it.toString() }
                _compactSecondRow.value = secondRow.map { it.toString() }
                _compactThirdRow.value = thirdRow.map { it.toString() }

                Log.d("WindowSizeViewModel", "First Row: $firstRow")
            } catch (e: Exception) {
                // Handle error
                // You can log the error or show a Toast message
                // Note: You cannot directly show UI from here
            }
        }
    }
}
