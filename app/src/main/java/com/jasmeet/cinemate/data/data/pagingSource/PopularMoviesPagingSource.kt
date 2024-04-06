package com.jasmeet.cinemate.data.data.pagingSource

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.jasmeet.cinemate.data.data.apiResponse.remote.PopularMoviesResponse
import com.jasmeet.cinemate.data.data.apiResponse.remote.Result
import com.jasmeet.cinemate.data.repository.PopularMoviesRepository
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class PopularMoviesPagingSource @Inject constructor(
    private val popularMoviesRepository: PopularMoviesRepository
) : PagingSource<Int, Result>(){

    override fun getRefreshKey(state: PagingState<Int, Result>): Int? {
        return state.anchorPosition
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Result> {
        val page = params.key ?: 1

        val response = popularMoviesRepository.getPopularMovies(
            page = page
        )

        return try {
            LoadResult.Page(
                data =  response.results,
                prevKey =  if (page == 1) null else page.minus(1),
                nextKey = if (response.results.isEmpty()) null else page.plus(1)
            )

        }catch (e: IOException) {
            LoadResult.Error(
                e
            )
        } catch (e: HttpException) {
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