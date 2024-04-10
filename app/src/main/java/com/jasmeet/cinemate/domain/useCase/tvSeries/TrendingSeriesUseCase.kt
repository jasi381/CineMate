package com.jasmeet.cinemate.domain.useCase.tvSeries

import com.jasmeet.cinemate.data.apiResponse.remote.tvSeries.trending.TrendingSeriesResponse
import com.jasmeet.cinemate.data.repository.series.TrendingSeriesRepository

class TrendingSeriesUseCase(private val repository: TrendingSeriesRepository) {
    suspend operator fun invoke():TrendingSeriesResponse{
        return repository.getTrendingSeries()
    }
}