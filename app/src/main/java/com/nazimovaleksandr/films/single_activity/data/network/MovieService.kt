package com.nazimovaleksandr.films.single_activity.data.network

import com.nazimovaleksandr.films.single_activity.data.network.model.MovieList
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieService {
    @GET(value = MOVIE_URL)
    fun getMovieList(
        @Query(value = "token")
        token: String,
        @Query(value = "search")
        search: String,
        @Query(value = "field")
        field: String,
        @Query(value = "page")
        page: String,
        @Query(value = "sortField")
        sortField: String = "year",
        @Query(value = "sortType")
        sortType: String = "-1"
    ): Call<MovieList>
}