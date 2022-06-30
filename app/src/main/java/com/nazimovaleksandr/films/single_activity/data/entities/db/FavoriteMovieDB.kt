package com.nazimovaleksandr.films.single_activity.data.entities.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorite_movies")
data class FavoriteMovieDB(
    @PrimaryKey
    var id: Int,
    val name: String?,
    val details: String?,
    val preview: String?,
    val image: String?,
    val year: Int?,

    @ColumnInfo(name = "date_added")
    val dateAdded: Long = 0L,

    @ColumnInfo(name = "is_favorite")
    var isFavorite: Boolean = false,

    @ColumnInfo(name = "is_viewed")
    var isViewed: Boolean = false,

    var comment: String = ""
)
