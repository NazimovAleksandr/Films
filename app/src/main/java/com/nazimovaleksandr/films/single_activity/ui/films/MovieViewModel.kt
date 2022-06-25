package com.nazimovaleksandr.films.single_activity.ui.films

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.nazimovaleksandr.films.single_activity.data.entities.ui.MovieUI
import com.nazimovaleksandr.films.single_activity.SingleActivity

class MovieViewModel : ViewModel() {

    private var _movieList = MutableLiveData<List<MovieUI>>()
    val movieList: LiveData<List<MovieUI>> get() = _movieList

    fun loadMovieList(activity: SingleActivity) {
        _movieList.value = activity.getMovieList()
    }
}