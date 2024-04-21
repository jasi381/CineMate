package com.jasmeet.cinemate.data.repository.movies

import com.jasmeet.cinemate.data.apiResponse.remote.movies.media.MovieMediaResponse

interface MovieMediaRepository {
    suspend fun getMovieMedia(movieId: String): MovieMediaResponse
}