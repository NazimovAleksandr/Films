package com.nazimovaleksandr.films.single_activity.data.storage

import androidx.room.Database
import androidx.room.RoomDatabase
import com.nazimovaleksandr.films.single_activity.data.entities.db.FavoriteMovieDB
import com.nazimovaleksandr.films.single_activity.data.entities.db.MovieDB
import com.nazimovaleksandr.films.single_activity.data.storage.dao.FavoriteMovieDao
import com.nazimovaleksandr.films.single_activity.data.storage.dao.MovieDao

@Database(
    entities = [MovieDB::class, FavoriteMovieDB::class],
    version = MoviesStorage.DATA_BASE_VERSION
)
abstract class MoviesStorage : RoomDatabase() {
    companion object {
        const val DATA_BASE_NAME = "MoviesStorageDB"
        const val DATA_BASE_VERSION = 1
    }

    abstract fun movieDao(): MovieDao
    abstract fun favoriteMovieDao(): FavoriteMovieDao
}