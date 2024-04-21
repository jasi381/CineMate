package com.jasmeet.cinemate.data.repositoryImpl.movies

import com.jasmeet.cinemate.data.apiResponse.remote.movies.characterMovies.CharacterMoviesResponse
import com.jasmeet.cinemate.data.apiService.ApiService
import com.jasmeet.cinemate.data.repository.movies.CharacterMoviesRepository

class CharacterMoviesRepositoryImpl(private val apiService: ApiService): CharacterMoviesRepository {
    override suspend fun getCharacterMovies(characterId: String): CharacterMoviesResponse {
        return apiService.getCharacterMovies(characterId)
    }

}