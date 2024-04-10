package com.jasmeet.cinemate.data.pagingSource.movies

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.jasmeet.cinemate.data.apiResponse.remote.movies.nowPlaying.Result
import com.jasmeet.cinemate.data.repository.movies.NowPlayingMoviesRepository
import retrofit2.HttpException
import javax.inject.Inject

class NowPlayingMoviesPagingSource @Inject constructor(
    private val nowPlayingMoviesRepository: NowPlayingMoviesRepository
) : PagingSource<Int, Result>(){

    override fun getRefreshKey(state: PagingState<Int, Result>): Int? {
        return state.anchorPosition
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Result> {
        val page = params.key ?: 1

        val response = nowPlayingMoviesRepository.getNowPlayingMovies(
            page = page
        )

        return try {
            LoadResult.Page(
                data =  response.results,
                prevKey =  if (page == 1) null else page.minus(1),
                nextKey = if (response.results.isEmpty()) null else page.plus(1)
            )

        }catch (e: HttpException) {
            LoadResult.Error(
                e
            )
        }catch (e:Exception){
            LoadResult.Error(
                e
            )
        }
    }
}