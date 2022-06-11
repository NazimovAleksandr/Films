package com.nazimovaleksandr.films.single_activity.ui.favorites

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.nazimovaleksandr.films.single_activity.ui.films.movie_list.MovieItem
import com.nazimovaleksandr.films.single_activity.SingleActivity

class FavoriteViewModel : ViewModel() {

    private var _favoriteMovieList = MutableLiveData<List<MovieItem>>()
    val favoriteMovieList: LiveData<List<MovieItem>> = _favoriteMovieList

    fun loadMovieList(activity: SingleActivity) {
        _favoriteMovieList.value = activity.getFavoriteMovieList()
    }
}