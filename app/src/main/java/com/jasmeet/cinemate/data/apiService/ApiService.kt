package com.jasmeet.cinemate.data.apiService

import com.jasmeet.cinemate.BuildConfig
import com.jasmeet.cinemate.data.apiResponse.remote.movies.nowPlaying.NowPlayingResponse
import com.jasmeet.cinemate.data.apiResponse.remote.movies.popular.PopularMoviesResponse
import com.jasmeet.cinemate.data.apiResponse.remote.movies.topRated.TopRatedMoviesResponse
import com.jasmeet.cinemate.data.apiResponse.remote.movies.upComing.UpcomingMovieResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {


    companion object {
        const val BASE_URL = "https://api.themoviedb.org/3/movie/"
        const val API_KEY = BuildConfig.api_key
    }

    @GET("top_rated?api_key=$API_KEY&page=1&language=en-US&region=IN")
    suspend fun getPopularMovies(): PopularMoviesResponse

    @GET("now_playing?api_key=$API_KEY")
    suspend fun getNowPlayingMovies(
        @Query("page") page: Int
    ): NowPlayingResponse

    @GET("top_rated?api_key=$API_KEY")
    suspend fun getTopRatedMovies(
        @Query("page") page: Int
    ): TopRatedMoviesResponse

    @GET("upcoming?api_key=$API_KEY&language=en-US&region=IN")
    suspend fun getUpcomingMovies(
        @Query("page") page: Int
    ): UpcomingMovieResponse
}
