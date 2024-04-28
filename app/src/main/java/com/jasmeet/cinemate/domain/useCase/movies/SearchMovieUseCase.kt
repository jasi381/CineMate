package com.jasmeet.cinemate.domain.useCase.movies

import com.jasmeet.cinemate.data.repository.movies.SearchMovieRepository

class SearchMovieUseCase(private val repository: SearchMovieRepository) {
    suspend operator fun invoke(query: String, page: Int) = repository.getSearchMovie(query, page)
}