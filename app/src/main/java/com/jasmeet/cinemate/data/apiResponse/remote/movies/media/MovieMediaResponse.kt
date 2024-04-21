package com.jasmeet.cinemate.data.apiResponse.remote.movies.media

data class MovieMediaResponse(
    val backdrops: List<Backdrop>,
    val id: Int,
    val logos: List<Logo>,
    val posters: List<Poster>
)