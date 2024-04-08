package com.jasmeet.cinemate.presentation.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import com.jasmeet.cinemate.data.data.apiResponse.remote.movies.nowPlaying.Result
import com.jasmeet.cinemate.data.data.apiResponse.remote.movies.popular.PopularMoviesResponse
import com.jasmeet.cinemate.data.pagingSource.NowPlayingMoviesPagingSource
import com.jasmeet.cinemate.data.repository.NowPlayingMoviesRepository
import com.jasmeet.cinemate.data.repository.PopularMoviesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class MoviesViewModel @Inject constructor(
    private val popularMoviesRepository: PopularMoviesRepository,
    private val nowPlayingMoviesRepository: NowPlayingMoviesRepository
) : ViewModel() {

    private val _popularMoviesResponse: MutableStateFlow<PopularMoviesResponse?> =
        MutableStateFlow(null)
    val popularMoviesResponse = _popularMoviesResponse.asStateFlow()


    private val _nowPlayingMovies: MutableStateFlow<PagingData<Result>> =
        MutableStateFlow(PagingData.empty())
    val nowPlayingMovies = _nowPlayingMovies.asStateFlow()

    init {
        viewModelScope.launch {
            _popularMoviesResponse.value = popularMoviesRepository.getPopularMovies()


            Pager(
                config = PagingConfig(
                    pageSize = 20,
                    enablePlaceholders = true
                ),
                pagingSourceFactory = {NowPlayingMoviesPagingSource(nowPlayingMoviesRepository)}
            ).flow.cachedIn(viewModelScope).collectLatest { nowPlayingMovies ->
                _nowPlayingMovies.value = nowPlayingMovies
            }
        }


    }
}
