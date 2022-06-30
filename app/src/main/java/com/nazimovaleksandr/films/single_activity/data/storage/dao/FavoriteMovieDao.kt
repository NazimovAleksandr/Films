package com.nazimovaleksandr.films.single_activity.data.storage.dao

import androidx.room.*
import com.nazimovaleksandr.films.single_activity.data.entities.db.FavoriteMovieDB
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoriteMovieDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertFavoriteMovie(movie: FavoriteMovieDB)

    @Update
    fun updateFavoriteMovie(movieDB: FavoriteMovieDB)

    @Delete
    fun deleteFavoriteMovie(movieDB: FavoriteMovieDB)

    @Query("SELECT * FROM favorite_movies ORDER BY date_added DESC")
    fun getFavoriteMovieList(): Flow<List<FavoriteMovieDB>>

    @Query("SELECT * FROM favorite_movies WHERE id = :id")
    fun loadFavoriteMovieByID(id: Int): FavoriteMovieDB?
}