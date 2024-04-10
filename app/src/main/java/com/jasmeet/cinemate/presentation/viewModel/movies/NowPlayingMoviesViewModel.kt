package com.jasmeet.cinemate.presentation.viewModel.movies

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.filter
import com.jasmeet.cinemate.data.apiResponse.remote.movies.nowPlaying.Result
import com.jasmeet.cinemate.data.pagingSource.movies.NowPlayingMoviesPagingSource
import com.jasmeet.cinemate.data.repository.movies.NowPlayingMoviesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NowPlayingMoviesViewModel @Inject constructor(
    private val repository: NowPlayingMoviesRepository,
) : ViewModel() {

    private val _nowPlayingMovies: MutableStateFlow<PagingData<Result>> =
        MutableStateFlow(PagingData.empty())
    val nowPlayingMovies = _nowPlayingMovies.asStateFlow()


    init {
        viewModelScope.launch {
            Pager(
                config = PagingConfig(
                    pageSize = 70,
                    enablePlaceholders = true
                ),
                pagingSourceFactory = { NowPlayingMoviesPagingSource(repository) }
            ).flow
                .cachedIn(viewModelScope)
                .map { pagingData ->
                    pagingData.filter { result ->
                        result.backdrop_path != null
                    }
                }
                .collectLatest { filteredNowPlayingMovies ->
                    _nowPlayingMovies.value = filteredNowPlayingMovies
                }
        }
    }
}