package com.nazimovaleksandr.films.single_activity.data.network.model

import com.google.gson.annotations.SerializedName

data class MovieList(
    @SerializedName("docs")
    val movieList: List<MovieModel>
)
