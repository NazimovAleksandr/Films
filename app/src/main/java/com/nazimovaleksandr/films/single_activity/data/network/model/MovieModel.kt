package com.nazimovaleksandr.films.single_activity.data.network.model

import com.google.gson.annotations.SerializedName

data class MovieModel(
    @SerializedName(value = "name")
    val name: String,
    @SerializedName(value = "description")
    val description: String,
    @SerializedName(value = "year")
    val year: Int,
    @SerializedName(value = "poster")
    val poster: MoviePoster
)