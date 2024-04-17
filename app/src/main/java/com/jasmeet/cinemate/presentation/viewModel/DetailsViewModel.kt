package com.jasmeet.cinemate.presentation.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jasmeet.cinemate.data.apiResponse.remote.movies.castAndCrew.MovieCastResponse
import com.jasmeet.cinemate.data.apiResponse.remote.movies.details.MovieDetails
import com.jasmeet.cinemate.data.apiResponse.remote.movies.videoDetails.MovieVideoDetailsResponse
import com.jasmeet.cinemate.data.apiResponse.remote.tvSeries.seriesDetails.SeriesDetailsResponse
import com.jasmeet.cinemate.data.repository.movies.MovieDetailsRepository
import com.jasmeet.cinemate.data.repository.movies.MoviesCastRepository
import com.jasmeet.cinemate.data.repository.movies.VideoDetailsRepository
import com.jasmeet.cinemate.data.repository.series.SeriesDetailsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class DetailsViewModel @Inject constructor(
    private val movieDetailsRepository: MovieDetailsRepository,
    private val seriesDetailsRepository: SeriesDetailsRepository,
    private val videoDetailsRepository: VideoDetailsRepository,
    private val movieCastRepository: MoviesCastRepository
) : ViewModel() {

    private val _movieDetails = MutableLiveData<MovieDetails>()
    val movieDetails: LiveData<MovieDetails> = _movieDetails

    private val _seriesDetails = MutableLiveData<SeriesDetailsResponse>()
    val seriesDetails: LiveData<SeriesDetailsResponse> = _seriesDetails

    private val _trailerId = MutableLiveData<String?>()
    val trailerId: MutableLiveData<String?> = _trailerId

    private val _movieCastDetails = MutableLiveData<MovieCastResponse>()
    val movieCastDetails :LiveData<MovieCastResponse> = _movieCastDetails


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

    fun fetchVideoDetails(id: String) {
        viewModelScope.launch {
            _isLoading.postValue(true)

            try {
                val videoDetails = videoDetailsRepository.getVideoDetails(id)
                val trailerId = videoDetails.results.find { it.type == "Trailer"  }?.key
                _trailerId.postValue(trailerId)

                Log.d("VideoDetails", trailerId.toString())
            } catch (e: Exception) {
                _error.postValue(e.message ?: "An error occurred")
                Log.d("VideoDetails", e.message ?: "An error occurred")
            }
            finally {
                _isLoading.postValue(false)
            }
        }
    }

    fun fetchMovieCast(id: String){
        viewModelScope.launch {
            _isLoading.postValue(true)

            try {
                val moviesCast = movieCastRepository.getMovieCast(id)
                _movieCastDetails.postValue(moviesCast)
                Log.d("MovieCast", moviesCast.toString())
            }catch (e:Exception){
                _error.postValue(e.message ?: "An error occurred")
                Log.d("Error",e.message.toString())
            }finally {
                _isLoading.postValue(false)
            }
        }
    }
}