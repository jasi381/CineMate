package com.jasmeet.cinemate.presentation.viewModel.series

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.filter
import com.jasmeet.cinemate.data.apiResponse.remote.tvSeries.airingToday.Result
import com.jasmeet.cinemate.data.pagingSource.movies.NowPlayingMoviesPagingSource
import com.jasmeet.cinemate.data.pagingSource.series.AiringTodayPagingSource

import com.jasmeet.cinemate.data.repository.series.AiringTodayRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class AiringTodayViewModel @Inject constructor(
    private val repository: AiringTodayRepository
):ViewModel() {

    private val _airingToday : MutableStateFlow<PagingData<Result>> =
        MutableStateFlow(PagingData.empty())
    val airingToday : MutableStateFlow<PagingData<Result>> = _airingToday

    init {
        viewModelScope.launch {
            Pager(
                config = PagingConfig(
                    pageSize = 70,
                    enablePlaceholders = true
                ),
                pagingSourceFactory = { AiringTodayPagingSource(repository) }
            ).flow
                .cachedIn(viewModelScope)
                .map { pagingData ->
                    pagingData.filter { result ->
                        result.backdrop_path != null
                    }
                }
                .collectLatest { filteredNowPlayingMovies ->
                    _airingToday.value = filteredNowPlayingMovies
                }
        }
    }

}