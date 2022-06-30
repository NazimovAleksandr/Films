package com.nazimovaleksandr.films.single_activity.ui.movie_list

import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.nazimovaleksandr.films.R
import com.nazimovaleksandr.films.databinding.ItemMovieBinding
import com.nazimovaleksandr.films.single_activity.data.entities.ui.MovieUI

class MovieItemViewHolder(
    private val binding: ItemMovieBinding,
    private val onClickListener: MovieItemOnClickListener
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(movie: MovieUI) {
        Glide
            .with(binding.movieImage.context)
            .load(movie.preview)
            .placeholder(R.drawable.ic_load)
            .error(R.drawable.ic_error_loading)
            .into(binding.movieImage)

        binding.movieYear.text = movie.year.toString()
        binding.viewed.isVisible = movie.isViewed
        binding.movieName.text = movie.name

        binding.buttonFavorite.apply {
            setIsFavoriteData(movie)

            setOnClickListener {
                onClickListener.onClickIsFavorite(
                    movie = movie,
                    position = adapterPosition
                )

                setIsFavoriteData(movie)
            }
        }

        binding.movieImage.setOnClickListener {
            onClickListener.onClickDetails(
                movie = movie
            )
        }
    }

    private fun setIsFavoriteData(movie: MovieUI) {
        binding.buttonFavorite.setImageResource(
            if (movie.isFavorite) {
                R.drawable.ic_favorite_true
            } else {
                R.drawable.ic_favorite_false
            }
        )
    }
}