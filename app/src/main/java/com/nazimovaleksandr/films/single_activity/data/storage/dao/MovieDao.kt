package com.nazimovaleksandr.films.single_activity.data.storage.dao

import androidx.room.*
import com.nazimovaleksandr.films.single_activity.data.entities.db.MovieDB
import kotlinx.coroutines.flow.Flow

@Dao
interface MovieDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMovie(movie: MovieDB)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMovies(list: List<MovieDB>?)

    @Update
    fun updateMovie(movieDB: MovieDB)

    @Delete
    fun deleteMovie(movieDB: MovieDB)

    @Query("SELECT * FROM movies ORDER BY date_added DESC")
    fun getMovieList(): Flow<List<MovieDB>>

    @Query("SELECT * FROM movies WHERE id = :id")
    fun loadMovieByID(id: Int): MovieDB?
}