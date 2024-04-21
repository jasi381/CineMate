package com.jasmeet.cinemate.data.apiResponse.remote.movies.characterMovies

data class CharacterMoviesResponse(
    val cast: List<Cast>,
    val crew: List<Crew>,
    val id: Int
)