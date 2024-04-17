package com.jasmeet.cinemate.domain.useCase.tvSeries

import com.jasmeet.cinemate.data.repository.series.SeriesDetailsRepository


class SeriesDetailsUseCase (private val repository: SeriesDetailsRepository){
    suspend operator fun invoke(id:String) = repository.getSeriesDetails(id)

}