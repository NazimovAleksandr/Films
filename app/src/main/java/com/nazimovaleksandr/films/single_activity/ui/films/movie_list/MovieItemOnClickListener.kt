package com.nazimovaleksandr.films.single_activity.ui.films.movie_list

import com.nazimovaleksandr.films.single_activity.data.entities.ui.MovieUI

interface MovieItemOnClickListener {
    fun onClickDetails(item: MovieUI)
    fun onClickIsFavorite(item: MovieUI, position: Int)
}