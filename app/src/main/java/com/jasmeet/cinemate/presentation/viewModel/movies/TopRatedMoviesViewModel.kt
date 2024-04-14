package com.jasmeet.cinemate.presentation.viewModel.movies

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.filter
import com.jasmeet.cinemate.data.pagingSource.movies.TopRatedMoviesPagingSource
import com.jasmeet.cinemate.data.repository.movies.TopRatedMoviesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject
import com.jasmeet.cinemate.data.apiResponse.remote.movies.topRated.Result
import kotlinx.coroutines.flow.map


@HiltViewModel
class TopRatedMoviesViewModel @Inject constructor(
    private val repository: TopRatedMoviesRepository
) :ViewModel(){

    private val _topRatedMovies: MutableStateFlow<PagingData<Result>> =
        MutableStateFlow(PagingData.empty())
    val topRatedMovies = _topRatedMovies.asStateFlow()


    init {
        viewModelScope.launch {
            Pager(
                config = PagingConfig(
                    pageSize = 10,
                    enablePlaceholders = true
                ),
                pagingSourceFactory = { TopRatedMoviesPagingSource(repository) }
            ).flow
                .map { pagingData ->
                    pagingData.filter { result ->
                        result.backdrop_path != null
                    }
                }
                .cachedIn(viewModelScope)
                .collectLatest {
                    _topRatedMovies.value = it
                }
        }

    }
}