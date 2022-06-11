package com.nazimovaleksandr.films.single_activity.ui.films.movie_list

import androidx.recyclerview.widget.DiffUtil

class MovieItemDiffUtil(
    private val oldList: List<MovieItem>,
    private val newList: List<MovieItem>
) : DiffUtil.Callback() {
    override fun getOldListSize(): Int = oldList.size

    override fun getNewListSize(): Int = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition] == newList[newItemPosition]
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].isFavorite == newList[newItemPosition].isFavorite
    }
}