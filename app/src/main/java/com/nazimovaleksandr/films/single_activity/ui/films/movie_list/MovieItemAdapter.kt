package com.nazimovaleksandr.films.single_activity.ui.films.movie_list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.nazimovaleksandr.films.databinding.ItemMovieBinding

class MovieItemAdapter(
    private var movieItemList: List<MovieItem>,
    private val onClickListener: MovieItemOnClickListener
) : RecyclerView.Adapter<MovieItemViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieItemViewHolder {
        val inflate = LayoutInflater.from(parent.context)
        val binding = ItemMovieBinding.inflate(inflate, parent, false)

        return MovieItemViewHolder(binding, onClickListener)
    }

    override fun onBindViewHolder(holder: MovieItemViewHolder, position: Int) {
        holder.bind(movieItemList[position])
    }

    override fun getItemCount(): Int = movieItemList.size

    fun removeItem(item: MovieItem) {
        val list = movieItemList.toMutableList()
        list.remove(item)

        val diffUtil = MovieItemDiffUtil(movieItemList, list)
        val diffResult = DiffUtil.calculateDiff(diffUtil)

        movieItemList = list

        diffResult.dispatchUpdatesTo(this)
    }

    fun addItem(item: MovieItem, position: Int) {
        val list = movieItemList.toMutableList()
        list.add(position, item)

        val diffUtil = MovieItemDiffUtil(movieItemList, list)
        val diffResult = DiffUtil.calculateDiff(diffUtil)

        movieItemList = list

        diffResult.dispatchUpdatesTo(this)
    }
}