package com.jasmeet.cinemate.data.repository.series

import com.jasmeet.cinemate.data.apiResponse.remote.tvSeries.airingToday.AiringTodayResponse

interface AiringTodayRepository {
    suspend fun getAiringToday(page:Int): AiringTodayResponse
}