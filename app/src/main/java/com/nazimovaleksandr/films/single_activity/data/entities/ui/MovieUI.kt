package com.nazimovaleksandr.films.single_activity.data.entities.ui

data class MovieUI(
    var id: Int,
    val name: String?,
    val details: String?,
    val preview: String?,
    val image: String?,
    val year: Int?,
    val dateAdded: Long,
    var isFavorite: Boolean = false,
    var isViewed: Boolean = false,
    var comment: String = ""
)