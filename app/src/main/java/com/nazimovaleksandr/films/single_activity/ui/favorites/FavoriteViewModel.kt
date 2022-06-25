package com.nazimovaleksandr.films.single_activity.ui.favorites

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.nazimovaleksandr.films.single_activity.data.entities.ui.MovieUI
import com.nazimovaleksandr.films.single_activity.SingleActivity

class FavoriteViewModel : ViewModel() {

    private var _favoriteMovieList = MutableLiveData<List<MovieUI>>()
    val favoriteMovieList: LiveData<List<MovieUI>> = _favoriteMovieList

    fun loadMovieList(activity: SingleActivity) {
        _favoriteMovieList.value = activity.getFavoriteMovieList()
    }
}