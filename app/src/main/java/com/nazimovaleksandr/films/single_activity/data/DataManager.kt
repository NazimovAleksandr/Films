package com.nazimovaleksandr.films.single_activity.data

import android.content.Context
import android.util.Log
import androidx.room.Room
import com.nazimovaleksandr.films.single_activity.data.entities.db.MovieDB
import com.nazimovaleksandr.films.single_activity.data.entities.ui.MovieUI
import com.nazimovaleksandr.films.single_activity.data.mappers.toDB
import com.nazimovaleksandr.films.single_activity.data.mappers.toFavoriteDB
import com.nazimovaleksandr.films.single_activity.data.mappers.toUI
import com.nazimovaleksandr.films.single_activity.data.network.MovieApi
import com.nazimovaleksandr.films.single_activity.data.network.model.MovieList
import com.nazimovaleksandr.films.single_activity.data.storage.MoviesStorage
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

object DataManager {
    private val movieApi: MovieApi = MovieApi()

    private var DATA_BASE: MoviesStorage? = null

    fun init(context: Context) {
        DATA_BASE = DATA_BASE ?: synchronized(this) {
            val dataBase = Room
                .databaseBuilder(context, MoviesStorage::class.java, MoviesStorage.DATA_BASE_NAME)
                .build()

            DATA_BASE = dataBase
            dataBase
        }
    }

    fun loadMovies(page: Int, errorCallback: () -> Unit) {
        movieApi.getMovies(
            page.toString(),
            object : Callback<MovieList> {
                override fun onResponse(call: Call<MovieList>, response: Response<MovieList>) {
                    CoroutineScope(Job()).launch(Dispatchers.IO) {
                        var id = 0
                        response.body()?.movieList?.map {
                            Log.d("loadMovies", "onResponse: $it")
                            delay(1L)

                            id++

                            MovieDB(
                                id = id,
                                name = it.name,
                                details = it.description,
                                preview = it.poster.previewUrl,
                                image = it.poster.url,
                                year = it.year,
                                dateAdded = System.currentTimeMillis()
                            )
                        }?.let {
                            DATA_BASE?.movieDao()?.insertMovies(it)
                        }
                    }
                }

                override fun onFailure(call: Call<MovieList>, t: Throwable) {
                    Log.e("loadMovies", "onFailure: $t")
                    errorCallback.invoke()
                }
            }
        )
    }

    fun getMovieList(): Flow<List<MovieUI>>? =
        DATA_BASE?.movieDao()?.getMovieList()?.map {
            it.map { movieDB ->
                movieDB.toUI
            }
        }

    fun updateMovie(movieUI: MovieUI) {
        DATA_BASE?.movieDao()?.updateMovie(movieUI.toDB)
    }

    fun loadMovieByID(id: Int): MovieUI? =
        DATA_BASE?.movieDao()?.loadMovieByID(id)?.toUI

    fun getFavoriteMovieList(): Flow<List<MovieUI>>? =
        DATA_BASE?.favoriteMovieDao()?.getFavoriteMovieList()?.map {
            it.map { movieDB ->
                movieDB.toUI
            }
        }

    fun insertFavoriteMovie(movieUI: MovieUI) {
        DATA_BASE?.favoriteMovieDao()?.insertFavoriteMovie(movieUI.toFavoriteDB)
    }

    fun deleteFavoriteMovie(movieUI: MovieUI) {
        DATA_BASE?.favoriteMovieDao()?.deleteFavoriteMovie(movieUI.toFavoriteDB)
    }

    fun updateFavoriteMovie(movieUI: MovieUI) {
        DATA_BASE?.favoriteMovieDao()?.updateFavoriteMovie(movieUI.toFavoriteDB)
    }

    fun loadFavoriteMovieByID(id: Int): MovieUI? =
        DATA_BASE?.favoriteMovieDao()?.loadFavoriteMovieByID(id)?.toUI
}