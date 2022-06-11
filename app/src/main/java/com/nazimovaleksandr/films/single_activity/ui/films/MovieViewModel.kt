package com.nazimovaleksandr.films.single_activity.ui.films

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.nazimovaleksandr.films.single_activity.ui.films.movie_list.MovieItem
import com.nazimovaleksandr.films.single_activity.SingleActivity

class MovieViewModel : ViewModel() {

    private var _movieList = MutableLiveData<List<MovieItem>>()
    val movieList: LiveData<List<MovieItem>> get() = _movieList

    fun loadMovieList(activity: SingleActivity) {
        _movieList.value = activity.getMovieList()
    }
}