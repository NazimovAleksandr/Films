package com.nazimovaleksandr.films.main.movie_list

interface MovieItemOnClickListener {
    fun onClickDetails(item: MovieItem)
    fun onClickIsFavorite(item: MovieItem, position: Int)
}