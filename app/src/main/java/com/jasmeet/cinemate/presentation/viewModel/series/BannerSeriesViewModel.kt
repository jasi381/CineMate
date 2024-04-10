package com.jasmeet.cinemate.presentation.viewModel.series

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jasmeet.cinemate.data.apiResponse.remote.tvSeries.trending.TrendingSeriesResponse
import com.jasmeet.cinemate.data.repository.series.TrendingSeriesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BannerSeriesViewModel @Inject constructor(
    private val repository: TrendingSeriesRepository
) :ViewModel(){

    private val _bannerSeriesResponse: MutableStateFlow<TrendingSeriesResponse?> =
        MutableStateFlow(null)
    val bannerSeriesResponse = _bannerSeriesResponse.asStateFlow()

    init {
        viewModelScope.launch {
            _bannerSeriesResponse.value = repository.getTrendingSeries()

        }
    }
}