package com.jasmeet.cinemate.data.repository.series

import com.jasmeet.cinemate.data.apiResponse.remote.tvSeries.seriesDetails.SeriesDetailsResponse

interface SeriesDetailsRepository {
    suspend fun getSeriesDetails(seriesId: String): SeriesDetailsResponse
}