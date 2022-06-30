package com.nazimovaleksandr.films.single_activity.ui.favorites

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.nazimovaleksandr.films.single_activity.data.DataManager
import com.nazimovaleksandr.films.single_activity.data.entities.ui.MovieUI
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FavoriteViewModel(
    private val dataManager: DataManager
) : ViewModel() {
    val favoriteMovieList: LiveData<List<MovieUI>>? = dataManager.getFavoriteMovieList()?.asLiveData()

    fun onClickIsFavorite(movie: MovieUI) {
        viewModelScope.launch(Dispatchers.IO) {
            if (movie.isFavorite) {
                dataManager.insertFavoriteMovie(movie)
            } else {
                dataManager.deleteFavoriteMovie(movie)
            }
        }
    }

    fun onClickDetails(movie: MovieUI) {
        viewModelScope.launch(Dispatchers.IO) {
            dataManager.updateFavoriteMovie(movie)
        }
    }
}