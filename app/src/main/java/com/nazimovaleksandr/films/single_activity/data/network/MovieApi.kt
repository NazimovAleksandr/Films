package com.nazimovaleksandr.films.single_activity.data.network

import com.nazimovaleksandr.films.single_activity.data.network.model.MovieList
import retrofit2.Callback
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/*
Поиск по году
GET/movie

token: string (required) Example: ZQQ8GMN-TN54SGK-NB3MKEC-ZKB8V06
Токен для авторизации

search: string (required) Example: 2020
Искомое значение 2020

field: string (required) Example: year
Поиск по year
*/

/*
ПРИМЕР СЛОЖНОЙ ПОИСКОВОЙ КОНСТРУКЦИИ
Поиск сериалов с диапозонами и сортировкой

/movie?
field=rating.kp&search=7-10&
field=year&search=2017-2020&
field=typeNumber&search=2&
sortField=year&sortType=1&
sortField=votes.imdb&sortType=-1&
token=ZQQ8GMN-TN54SGK-NB3MKEC-ZKB8V06
*/

class MovieApi {
    private val service: MovieService

    init {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        service = retrofit.create(MovieService::class.java)
    }

    fun getMoviesByYear(period: String, page: String, callback: Callback<MovieList>) {
        service.getMovieList(
            token = TOKEN,
            search = period,
            field = "year",
            page = page
        ).enqueue(callback)
    }
}