package com.nazimovaleksandr.films.single_activity.data.mappers

import com.nazimovaleksandr.films.single_activity.data.entities.db.FavoriteMovieDB
import com.nazimovaleksandr.films.single_activity.data.entities.ui.MovieUI

inline val FavoriteMovieDB.toUI
    get() = MovieUI(
        id = id,
        name = name,
        details = details,
        preview = preview,
        image = image,
        year = year,
        dateAdded = dateAdded,
        isFavorite = isFavorite,
        isViewed = isViewed,
        comment = comment
    )

inline val MovieUI.toFavoriteDB
    get() = FavoriteMovieDB(
        id = id,
        name = name,
        details = details,
        preview = preview,
        image = image,
        year = year,
        dateAdded = dateAdded,
        isFavorite = isFavorite,
        isViewed = isViewed,
        comment = comment
    )