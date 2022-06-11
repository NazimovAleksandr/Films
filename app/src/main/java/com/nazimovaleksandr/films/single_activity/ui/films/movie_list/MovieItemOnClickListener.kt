package com.nazimovaleksandr.films.single_activity.ui.films.movie_list

interface MovieItemOnClickListener {
    fun onClickDetails(item: MovieItem)
    fun onClickIsFavorite(item: MovieItem, position: Int)
}