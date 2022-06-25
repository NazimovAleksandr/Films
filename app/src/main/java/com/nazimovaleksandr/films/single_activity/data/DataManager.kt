package com.nazimovaleksandr.films.single_activity.data

import com.nazimovaleksandr.films.single_activity.data.network.MovieApi
import com.nazimovaleksandr.films.single_activity.data.network.model.MovieList
import com.nazimovaleksandr.films.single_activity.data.network.model.MovieModel
import retrofit2.Callback

object DataManager {
    private val movieApi: MovieApi = MovieApi()
    private var page = 0

    fun getMoviesByYear(
        startYear: Int,
        endYear: Int,
        callback: Callback<MovieList>
    ) {
        page++
        movieApi.getMoviesByYear("$startYear-$endYear", page.toString(), callback)
    }
}