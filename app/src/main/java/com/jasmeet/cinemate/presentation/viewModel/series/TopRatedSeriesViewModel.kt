package com.jasmeet.cinemate.presentation.viewModel.series

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.filter
import com.jasmeet.cinemate.data.apiResponse.remote.tvSeries.topRated.Result
import com.jasmeet.cinemate.data.pagingSource.series.TopRatedSeriesPagingSource
import com.jasmeet.cinemate.data.repository.series.TopRatedSeriesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class TopRatedSeriesViewModel @Inject constructor(
    private val repository: TopRatedSeriesRepository
): ViewModel() {

    private val _topRatedSeries : MutableStateFlow<PagingData<Result>> =
        MutableStateFlow(PagingData.empty())
    val topRatedSeries : MutableStateFlow<PagingData<Result>> = _topRatedSeries

    init {
        viewModelScope.launch {
            Pager(
                config = PagingConfig(
                    pageSize = 70,
                    enablePlaceholders = true
                ),
                pagingSourceFactory = { TopRatedSeriesPagingSource(repository) }
            ).flow
                .cachedIn(viewModelScope)
                .map { pagingData ->
                    pagingData.filter { result ->
                        result.backdrop_path != null
                    }
                }
                .collectLatest { filteredTopRatedSeries ->
                    _topRatedSeries.value = filteredTopRatedSeries
                }
        }
    }

}