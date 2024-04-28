package com.jasmeet.cinemate.presentation.viewModel.movies

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.filter
import com.jasmeet.cinemate.data.apiResponse.remote.movies.searchedMovies.Result
import com.jasmeet.cinemate.data.pagingSource.movies.SearchMoviePagingSource
import com.jasmeet.cinemate.data.repository.movies.SearchMovieRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchMovieViewModel @Inject constructor(private val repository: SearchMovieRepository) :
    ViewModel() {


    private val _searchedMovies: MutableStateFlow<PagingData<Result>> =
        MutableStateFlow(PagingData.empty())
    val searchedMovies = _searchedMovies.asStateFlow()

    private val _query = MutableLiveData<String>()
    val query: LiveData<String> get() = _query

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> get() = _error

    private var currentQuery: String? = null

    init {
        _query.value = ""
        loadMovies("")
    }

    fun searchMovies(query: String) {
        currentQuery = query
        _query.value = query
        loadMovies(query)
    }

    private fun loadMovies(query: String) {
        viewModelScope.launch {
            try {
                Pager(
                    config = PagingConfig(
                        pageSize = 70,
                        enablePlaceholders = true
                    ),
                    pagingSourceFactory = { SearchMoviePagingSource(repository, query) }
                ).flow
                    .cachedIn(viewModelScope)
                    .map { pagingData ->
                        pagingData.filter { result ->
                            result.backdrop_path != null
                        }
                    }
                    .collectLatest { filteredNowPlayingMovies ->
                        _searchedMovies.value = filteredNowPlayingMovies
                        Log.d(
                            "SearchMovieViewModel",
                            "Movies loaded successfully: $filteredNowPlayingMovies"
                        )
                    }
            } catch (e: Exception) {
                _error.value = "Failed to load movies: ${e.message}"
                Log.e("SearchMovieViewModel", "Error loading movies", e)
            }
        }
    }


    fun retry() {
        currentQuery?.let { loadMovies(it) }
    }

    fun clearQuery() {
        currentQuery = null
        _query.value = ""
        _searchedMovies.value = PagingData.empty()
    }

}
