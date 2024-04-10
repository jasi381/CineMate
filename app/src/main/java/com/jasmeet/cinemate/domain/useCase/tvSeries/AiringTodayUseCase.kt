package com.jasmeet.cinemate.domain.useCase.tvSeries

import com.jasmeet.cinemate.data.repository.series.AiringTodayRepository

class AiringTodayUseCase(private val repository: AiringTodayRepository){
    suspend operator fun invoke(page:Int) = repository.getAiringToday(page)
}