package com.nazimovaleksandr.films.single_activity.data.network

import com.nazimovaleksandr.films.single_activity.MOVIE_CACHE_SIZE
import com.nazimovaleksandr.films.single_activity.data.network.model.MovieList
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieService {
    @GET(value = MOVIE_URL)
    fun getMovieList(
        @Query(value = "token")
        token: String,
        @Query(value = "page")
        page: String,
        @Query(value = "search")
        searchRating: String = "7-10",
        @Query(value = "field")
        rating: String = "rating.kp",
        @Query(value = "sortField")
        sortField: String = "year",
        @Query(value = "sortType")
        sortType: String = "-1",
        @Query(value = "limit")
        limit: String = MOVIE_CACHE_SIZE.toString()
    ): Call<MovieList>
}

//https://api.kinopoisk.dev/movie?field=year&search=2019-2020&sortField=year&sortType=-1&token=0G3PDR1-VP8MEY2-HGXPYZT-RH1P69V
