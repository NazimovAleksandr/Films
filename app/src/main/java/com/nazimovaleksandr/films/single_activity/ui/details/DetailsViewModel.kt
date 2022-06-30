package com.nazimovaleksandr.films.single_activity.ui.details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nazimovaleksandr.films.single_activity.data.DataManager
import com.nazimovaleksandr.films.single_activity.data.entities.ui.MovieUI
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DetailsViewModel(
    private val dataManager: DataManager
) : ViewModel() {

    private val _movie: MutableLiveData<MovieUI> = MutableLiveData()
    val movie: LiveData<MovieUI> get() = _movie

    fun loadMovie(movieID: Int, favoriteMovieID: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            _movie.postValue(
                if (movieID != 0)
                    dataManager.loadMovieByID(movieID)
                else
                    dataManager.loadFavoriteMovieByID(favoriteMovieID)
            )
        }
    }

    fun onCommentChanged(comment: String) {
        _movie.value?.comment = comment
    }

    fun onFavoriteChanged(like: Boolean) {
        _movie.value?.isFavorite = like
    }

    fun saveMovie() {
        viewModelScope.launch(Dispatchers.IO) {
            _movie.value?.let { dataManager.updateMovie(it) }
        }
    }
}