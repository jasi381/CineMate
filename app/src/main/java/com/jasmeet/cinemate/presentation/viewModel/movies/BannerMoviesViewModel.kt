package com.jasmeet.cinemate.presentation.viewModel.movies

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jasmeet.cinemate.data.apiResponse.remote.movies.popular.PopularMoviesResponse
import com.jasmeet.cinemate.data.repository.PopularMoviesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class BannerMoviesViewModel @Inject constructor(
    private val repository: PopularMoviesRepository,
) : ViewModel() {

    private val _bannerMoviesResponse: MutableStateFlow<PopularMoviesResponse?> =
        MutableStateFlow(null)
    val popularMoviesResponse = _bannerMoviesResponse.asStateFlow()

    init {
        viewModelScope.launch {
            _bannerMoviesResponse.value = repository.getPopularMovies()

        }
    }
}
