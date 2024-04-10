package com.jasmeet.cinemate.data.repositoryImpl.series

import com.jasmeet.cinemate.data.apiResponse.remote.tvSeries.airingToday.AiringTodayResponse
import com.jasmeet.cinemate.data.apiService.ApiService
import com.jasmeet.cinemate.data.repository.series.AiringTodayRepository

class AiringTodayRepositoryImpl(private val apiService: ApiService):AiringTodayRepository {
    override suspend fun getAiringToday(page: Int): AiringTodayResponse {
        return apiService.getAiringTodayTvSeries(page)
    }
}