package com.jasmeet.cinemate.data.apiResponse.remote.movies.castAndCrew

data class MovieCastResponse(
    val cast: List<Cast>,
    val crew: List<Crew>,
    val id: Int?
)