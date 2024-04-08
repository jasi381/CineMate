package com.jasmeet.cinemate.presentation.viewModel.movies

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.filter
import com.jasmeet.cinemate.data.apiResponse.remote.movies.upComing.Result
import com.jasmeet.cinemate.data.pagingSource.UpcomingMoviesPagingSource
import com.jasmeet.cinemate.data.repository.UpcomingMovieRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UpcomingMoviesViewModel  @Inject constructor(
    private val repository: UpcomingMovieRepository,
): ViewModel(){

    private val _upComingMovies: MutableStateFlow<PagingData<Result>> =
        MutableStateFlow(PagingData.empty())
    val upComingMovies = _upComingMovies.asStateFlow()


    init {
        viewModelScope.launch {
            Pager(
                config = PagingConfig(
                    pageSize = 70,
                    enablePlaceholders = true
                ),
                pagingSourceFactory = { UpcomingMoviesPagingSource(repository) }
            ).flow
                .cachedIn(viewModelScope)
                .map { pagingData ->
                    pagingData.filter { result ->
                        result.backdrop_path != null
                    }
                }
                .collectLatest { filteredNowPlayingMovies ->
                    _upComingMovies.value = filteredNowPlayingMovies
                }
        }
    }
}