package com.jasmeet.cinemate.domain.useCase.movies

import com.jasmeet.cinemate.data.repository.movies.VideoDetailsRepository


class VideoDetailsUseCase (private val repository: VideoDetailsRepository){
    suspend operator fun invoke(id:String) = repository.getVideoDetails(id)

}