package com.jasmeet.cinemate.presentation.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jasmeet.cinemate.data.apiResponse.remote.movies.details.MovieDetails
import com.jasmeet.cinemate.data.apiResponse.remote.tvSeries.seriesDetails.SeriesDetailsResponse
import com.jasmeet.cinemate.data.repository.movies.MovieDetailsRepository
import com.jasmeet.cinemate.data.repository.series.SeriesDetailsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class DetailsViewModel @Inject constructor(
    private val movieDetailsRepository: MovieDetailsRepository,
    private val seriesDetailsRepository: SeriesDetailsRepository
) : ViewModel() {

    private val _movieDetails = MutableLiveData<MovieDetails>()
    val movieDetails: LiveData<MovieDetails> = _movieDetails

    private val _seriesDetails = MutableLiveData<SeriesDetailsResponse>()
    val seriesDetails: LiveData<SeriesDetailsResponse> = _seriesDetails

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> = _error

    fun fetchMovieDetails(id: String, isMovie: Boolean) {
        viewModelScope.launch {
            _isLoading.postValue(true)
            try {
                if (isMovie) {
                    val movieDetails = movieDetailsRepository.getMovieDetails(id)
                    _movieDetails.postValue(movieDetails)
                } else {
                    val seriesDetails = seriesDetailsRepository.getSeriesDetails(id)
                    _seriesDetails.postValue(seriesDetails)
                }
            } catch (e: Exception) {
                _error.postValue(e.message ?: "An error occurred")
            } finally {
                _isLoading.postValue(false)
            }
        }
    }
}