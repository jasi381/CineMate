package com.jasmeet.cinemate.data.apiService

import com.jasmeet.cinemate.BuildConfig
import com.jasmeet.cinemate.data.apiResponse.remote.movies.castAndCrew.MovieCastResponse
import com.jasmeet.cinemate.data.apiResponse.remote.movies.characterMovies.CharacterMoviesResponse
import com.jasmeet.cinemate.data.apiResponse.remote.movies.details.MovieDetails
import com.jasmeet.cinemate.data.apiResponse.remote.movies.media.MovieMediaResponse
import com.jasmeet.cinemate.data.apiResponse.remote.movies.nowPlaying.NowPlayingResponse
import com.jasmeet.cinemate.data.apiResponse.remote.movies.topRated.TopRatedMoviesResponse
import com.jasmeet.cinemate.data.apiResponse.remote.movies.trendingMovies.TrendingMovieResponse
import com.jasmeet.cinemate.data.apiResponse.remote.movies.upComing.UpcomingMovieResponse
import com.jasmeet.cinemate.data.apiResponse.remote.movies.videoDetails.MovieVideoDetailsResponse
import com.jasmeet.cinemate.data.apiResponse.remote.tvSeries.airingToday.AiringTodayResponse
import com.jasmeet.cinemate.data.apiResponse.remote.tvSeries.seriesDetails.SeriesDetailsResponse
import com.jasmeet.cinemate.data.apiResponse.remote.tvSeries.topRated.TopRatedResponse
import com.jasmeet.cinemate.data.apiResponse.remote.tvSeries.trending.TrendingSeriesResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    companion object {
        const val BASE_URL = "https://api.themoviedb.org/3/"
        const val API_KEY = BuildConfig.api_key
    }

    /**
     * These are the API endpoints for the Movies
     */

    @GET("movie/now_playing?api_key=$API_KEY")
    suspend fun getNowPlayingMovies(@Query("page") page: Int): NowPlayingResponse

    @GET("movie/top_rated?api_key=$API_KEY")
    suspend fun getTopRatedMovies(@Query("page") page: Int): TopRatedMoviesResponse

    @GET("movie/upcoming?api_key=$API_KEY&language=en-US&region=IN")
    suspend fun getUpcomingMovies(@Query("page") page: Int): UpcomingMovieResponse

    @GET("trending/movie/day?page=1&language=en&api_key=$API_KEY")
    suspend fun getTrendingMovies(): TrendingMovieResponse

    @GET("movie/{movie_id}?api_key=$API_KEY&language=en-US")
    suspend fun getMovieDetails(@Path("movie_id") id: String): MovieDetails

    @GET("movie/{movie_id}/videos?language=en-US&api_key=$API_KEY")
    suspend fun getMovieVideos(@Path("movie_id") id: String): MovieVideoDetailsResponse

    @GET("movie/{movie_id}/credits?language=en-US&api_key=$API_KEY")
    suspend fun getMovieCast(@Path("movie_id") id: String): MovieCastResponse

    @GET("person/{person_id}/movie_credits?language=en-US&api_key=$API_KEY")
    suspend fun getCharacterMovies(@Path("person_id") id: String): CharacterMoviesResponse

    @GET("movie/{movie_id}/images?api_key=$API_KEY")
    suspend fun getMovieImages(@Path("movie_id") id: String): MovieMediaResponse


    /**
     * These are the API endpoints for the Series
     */


    @GET("trending/tv/day?page=1&language=en&api_key=$API_KEY")
    suspend fun getTrendingTvSeries(): TrendingSeriesResponse

    @GET("tv/airing_today?language=en&api_key=$API_KEY")
    suspend fun getAiringTodayTvSeries(@Query("page") page: Int): AiringTodayResponse

    @GET("tv/top_rated?language=en&api_key=$API_KEY")
    suspend fun getTopRatedTvSeries(@Query("page") page: Int): TopRatedResponse

    @GET("tv/{series_id}?api_key=$API_KEY")
    suspend fun getSeriesDetails(@Path("series_id") id: String): SeriesDetailsResponse


}


