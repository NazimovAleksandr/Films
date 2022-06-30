package com.nazimovaleksandr.films.single_activity.ui.movie_list

import com.nazimovaleksandr.films.single_activity.data.entities.ui.MovieUI

interface MovieItemOnClickListener {
    fun onClickDetails(movie: MovieUI)
    fun onClickIsFavorite(movie: MovieUI, position: Int)
}