package com.jasmeet.cinemate.data.repository.movies

import com.jasmeet.cinemate.data.apiResponse.remote.movies.characterMovies.CharacterMoviesResponse

interface CharacterMoviesRepository {
    suspend fun  getCharacterMovies(characterId: String): CharacterMoviesResponse
}