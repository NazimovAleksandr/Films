package com.nazimovaleksandr.films.single_activity.ui.films.movie_list

import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.nazimovaleksandr.films.R
import com.nazimovaleksandr.films.databinding.ItemMovieBinding

class MovieItemViewHolder(
    private val binding: ItemMovieBinding,
    private val onClickListener: MovieItemOnClickListener
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(movie: MovieItem) {
        Glide
            .with(binding.movieImage.context)
            .load(movie.image)
            .placeholder(R.drawable.ic_films)
            .error(R.drawable.ic_error)
            .into(binding.movieImage)

        binding.movieName.apply {
            text = movie.name

            setIsViewedData(movie, context)
        }

        binding.buttonFavorite.apply {
            setIsFavoriteData(movie)

            setOnClickListener {
                onClickListener.onClickIsFavorite(
                    item = movie,
                    position = adapterPosition
                )

                setIsFavoriteData(movie)
            }
        }

        binding.movieImage.setOnClickListener {
            onClickListener.onClickDetails(
                item = movie
            )

            setIsViewedData(movie, it.context)
        }
    }

    private fun setIsFavoriteData(movie: MovieItem) {
        binding.buttonFavorite.setImageResource(
            if (movie.isFavorite) {
                R.drawable.ic_favorite_true
            } else {
                R.drawable.ic_favorite_false
            }
        )
    }

    private fun setIsViewedData(movie: MovieItem, context: Context) {
        if (movie.isViewed) {
            binding.movieName.setTextColor(context.getColor(R.color.purple_200))
        }
    }
}