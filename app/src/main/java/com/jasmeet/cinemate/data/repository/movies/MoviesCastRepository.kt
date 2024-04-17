package com.jasmeet.cinemate.data.repository.movies

import com.jasmeet.cinemate.data.apiResponse.remote.movies.castAndCrew.MovieCastResponse

interface MoviesCastRepository {
    suspend fun getMovieCast(movieId:String): MovieCastResponse
}