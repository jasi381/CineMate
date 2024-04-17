package com.jasmeet.cinemate.data.repository.movies

import com.jasmeet.cinemate.data.apiResponse.remote.movies.videoDetails.MovieVideoDetailsResponse

interface VideoDetailsRepository {
    suspend fun  getVideoDetails(videoId: String): MovieVideoDetailsResponse
}