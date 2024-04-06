package com.jasmeet.cinemate.presentation.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.jasmeet.cinemate.data.data.apiResponse.remote.Result
import com.jasmeet.cinemate.data.data.pagingSource.PopularMoviesPagingSource
import com.jasmeet.cinemate.data.repository.PopularMoviesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class PopularMoviesViewModel @Inject constructor(
    private val repository: PopularMoviesRepository
) :ViewModel() {

    private val _popularMoviesResponse: MutableStateFlow<PagingData<Result>> =
        MutableStateFlow(PagingData.empty())
    val popularMoviesResponse = _popularMoviesResponse.asStateFlow()

    init {

        viewModelScope.launch {
            Pager(
                config = PagingConfig(
                    pageSize = 3,
                    enablePlaceholders = true
                ),
                pagingSourceFactory = { PopularMoviesPagingSource(repository) }
            ).flow.cachedIn(viewModelScope).collectLatest { response ->
                _popularMoviesResponse.value = response
            }
        }
    }
}
