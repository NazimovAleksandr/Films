package com.nazimovaleksandr.films.single_activity.ui.movie_list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.nazimovaleksandr.films.databinding.ItemMovieBinding
import com.nazimovaleksandr.films.single_activity.data.entities.ui.MovieUI

class MovieItemAdapter(
    private val onClickListener: MovieItemOnClickListener
) : RecyclerView.Adapter<MovieItemViewHolder>() {

    private var movieItemList: List<MovieUI> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieItemViewHolder {
        val inflate = LayoutInflater.from(parent.context)
        val binding = ItemMovieBinding.inflate(inflate, parent, false)

        return MovieItemViewHolder(binding, onClickListener)
    }

    override fun onBindViewHolder(holder: MovieItemViewHolder, position: Int) {
        holder.bind(movieItemList[position])
    }

    override fun getItemCount(): Int = movieItemList.size

    fun setMovieList(list: List<MovieUI>) {
        updateList(list)
    }

    fun addMovieList(list: List<MovieUI>) {
        val updateList = movieItemList.toMutableList().apply {
            addAll(list)
        }

        updateList(updateList)
    }

    fun removeItem(item: MovieUI) {
        val list = movieItemList.toMutableList()
        list.remove(item)

        updateList(list)
    }

    fun addItem(item: MovieUI, position: Int) {
        val list = movieItemList.toMutableList()
        list.add(position, item)

        updateList(list)
    }

    private fun updateList(list: List<MovieUI>) {
        val diffUtil = MovieItemDiffUtil(movieItemList, list)
        val diffResult = DiffUtil.calculateDiff(diffUtil)

        movieItemList = list

        diffResult.dispatchUpdatesTo(this)
    }
}