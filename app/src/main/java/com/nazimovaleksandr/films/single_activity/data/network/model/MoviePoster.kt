package com.nazimovaleksandr.films.single_activity.data.network.model

import com.google.gson.annotations.SerializedName

data class MoviePoster(
    @SerializedName(value = "url")
    val url: String,
    @SerializedName(value = "previewUrl")
    val previewUrl: String,
)