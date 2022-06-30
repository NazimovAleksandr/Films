package com.nazimovaleksandr.films.single_activity.data.mappers

import com.nazimovaleksandr.films.single_activity.data.entities.db.MovieDB
import com.nazimovaleksandr.films.single_activity.data.entities.ui.MovieUI
import com.nazimovaleksandr.films.single_activity.data.network.model.MovieModel

inline val MovieDB.toUI
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

inline val MovieUI.toDB
    get() = MovieDB(
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