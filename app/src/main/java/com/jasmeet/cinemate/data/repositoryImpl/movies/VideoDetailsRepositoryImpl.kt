package com.jasmeet.cinemate.data.repositoryImpl.movies

import com.jasmeet.cinemate.data.apiResponse.remote.movies.videoDetails.MovieVideoDetailsResponse
import com.jasmeet.cinemate.data.apiService.ApiService
import com.jasmeet.cinemate.data.repository.movies.VideoDetailsRepository

class VideoDetailsRepositoryImpl(private val apiService: ApiService): VideoDetailsRepository {
    override suspend fun getVideoDetails(videoId: String): MovieVideoDetailsResponse {
        return apiService.getMovieVideos(videoId)
    }
}