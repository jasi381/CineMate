package com.jasmeet.cinemate.presentation.viewModel.movies

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jasmeet.cinemate.data.apiResponse.remote.movies.characterMovies.CharacterMoviesResponse
import com.jasmeet.cinemate.data.repository.movies.CharacterMoviesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CharacterMoviesViewModel @Inject constructor(
    private val repository: CharacterMoviesRepository
) :ViewModel() {

    private val _characterMoviesResponse = MutableLiveData<CharacterMoviesResponse?> ()
    val characterMoviesResponse:LiveData<CharacterMoviesResponse?> = _characterMoviesResponse

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> = _error


    fun getCharacterMovies(characterId: String) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                _characterMoviesResponse.value = repository.getCharacterMovies(characterId)
                Log.d("CharacterMoviesResponse", _characterMoviesResponse.value.toString())
            }catch (e:Exception){
                _error.value = e.message
                Log.d("CharacterMoviesError", e.message.toString())
            }finally {
                _isLoading.value = false
            }

        }

    }
}