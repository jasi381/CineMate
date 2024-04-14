package com.jasmeet.cinemate.data.repositoryImpl.series

import com.jasmeet.cinemate.data.apiResponse.remote.tvSeries.seriesDetails.SeriesDetailsResponse
import com.jasmeet.cinemate.data.apiService.ApiService
import com.jasmeet.cinemate.data.repository.series.SeriesDetailsRepository

class SeriesDetailsRepositoryImpl(private val apiService: ApiService):SeriesDetailsRepository {
    override suspend fun getSeriesDetails(seriesId: String): SeriesDetailsResponse {
        return apiService.getSeriesDetails(seriesId)

    }
}