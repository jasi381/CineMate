package com.jasmeet.cinemate.data.pagingSource.movies

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.jasmeet.cinemate.data.apiResponse.remote.movies.searchedMovies.Result
import com.jasmeet.cinemate.data.repository.movies.SearchMovieRepository
import retrofit2.HttpException
import javax.inject.Inject

class SearchMoviePagingSource @Inject constructor(
    private val searchMovieRepository: SearchMovieRepository,
    private val query: String
) : PagingSource<Int, Result>() {

    override fun getRefreshKey(state: PagingState<Int, Result>): Int? {
        return state.anchorPosition
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Result> {
        val page = params.key ?: 1

        val response = searchMovieRepository.getSearchMovie(
            page = page,
            query = query
        )

        return try {
            LoadResult.Page(
                data = response.results,
                prevKey = if (page == 1) null else page - 1,
                nextKey = if (response.results.isEmpty()) null else page + 1
            )
        } catch (e: HttpException) {
            LoadResult.Error(e)
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}
