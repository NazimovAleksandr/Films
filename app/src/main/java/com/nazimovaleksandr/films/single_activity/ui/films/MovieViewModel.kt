package com.nazimovaleksandr.films.single_activity.ui.films

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.nazimovaleksandr.films.single_activity.data.DataManager
import com.nazimovaleksandr.films.single_activity.data.entities.ui.MovieUI
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import kotlin.random.Random

class MovieViewModel(private val dataManager: DataManager) : ViewModel() {

    private val _isShowError = Channel<Boolean>()
    val isShowError: Flow<Boolean> get() = _isShowError.receiveAsFlow()

    val movieList: LiveData<List<MovieUI>>? = dataManager.getMovieList()?.asLiveData()
    var currentPage = 0
        private set

    private var page = 0

    fun loadMovieList() {
        currentPage = 0
        page = Random.nextInt(0, 100)
        dataManager.loadMovies(page, ::loadMovieErrorCallback)
    }

    fun loadNextPage() {
        currentPage += 1
        page++
        dataManager.loadMovies(page, ::loadMovieErrorCallback)
    }

    fun refresh() {
        dataManager.loadMovies(page, ::loadMovieErrorCallback)
    }

    fun onClickIsFavorite(movie: MovieUI) {
        viewModelScope.launch(Dispatchers.IO) {
            if (movie.isFavorite) {
                dataManager.insertFavoriteMovie(movie)
            } else {
                dataManager.deleteFavoriteMovie(movie)
            }

            dataManager.updateMovie(movie)
        }
    }

    fun onClickDetails(movie: MovieUI) {
        viewModelScope.launch(Dispatchers.IO) {
            dataManager.updateMovie(movie)
        }
    }

    private fun loadMovieErrorCallback() {
        viewModelScope.launch {
            _isShowError.send(true)
        }
    }
}