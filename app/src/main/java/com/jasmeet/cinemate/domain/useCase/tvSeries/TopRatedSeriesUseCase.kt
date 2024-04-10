package com.jasmeet.cinemate.domain.useCase.tvSeries

import com.jasmeet.cinemate.data.repository.series.TopRatedSeriesRepository

class TopRatedSeriesUseCase(private val repository: TopRatedSeriesRepository) {
    suspend operator fun invoke(page: Int) = repository.getTopRatedSeries(page)
}