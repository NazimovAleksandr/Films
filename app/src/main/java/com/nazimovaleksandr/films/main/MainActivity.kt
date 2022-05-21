package com.nazimovaleksandr.films.main

import android.annotation.SuppressLint
import android.content.Intent
import android.content.res.Configuration
import android.graphics.Rect
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.nazimovaleksandr.films.R
import com.nazimovaleksandr.films.databinding.ActivityMainBinding
import com.nazimovaleksandr.films.details.DetailsActivity
import com.nazimovaleksandr.films.exit_dialog.ExitDialog
import com.nazimovaleksandr.films.favorites.FavoritesActivity
import com.nazimovaleksandr.films.main.movie_list.MovieItem
import com.nazimovaleksandr.films.main.movie_list.MovieItemAdapter
import com.nazimovaleksandr.films.main.movie_list.MovieItemOnClickListener
import java.io.Serializable

class MainActivity : AppCompatActivity() {

    companion object {
        const val KEY_MOVIE = "KEY_MOVIE"
        const val KEY_FAVORITES_MOVIE = "KEY_FAVORITES_MOVIE"

        const val KEY_IS_LIKE = "KEY_IS_LIKE"
        const val KEY_COMMENT = "KEY_COMMENT"

        val TAG = MainActivity::class.simpleName
    }

    private var _binding: ActivityMainBinding? = null
    private val binding: ActivityMainBinding get() = _binding!!

    private val resultForDetailsActivity =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            Log.d("__$TAG", "isLike: ${it.data?.getBooleanExtra(KEY_IS_LIKE, false)}")
            Log.d("__$TAG", "comment: ${it.data?.getStringExtra(KEY_COMMENT)}")
        }

    @SuppressLint("NotifyDataSetChanged")
    private val resultForFavoritesActivity =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            it.data?.apply {
                val list = getParcelableArrayExtra(KEY_FAVORITES_MOVIE)?.map { parcelable ->
                    parcelable as MovieItem
                } ?: emptyList()

                val favorites = favoriteMovieList.toMutableList().apply {
                    removeAll(list)
                }

                favorites.forEach { noFavorite ->
                    movieList.find { movie -> movie == noFavorite }?.isFavorite = false
                    favoriteMovieList.remove(noFavorite)
                }

                binding.recyclerMovieItems.adapter?.notifyDataSetChanged()
            }
        }

    private val favoriteMovieList = mutableListOf<MovieItem>()
    private val movieList = mutableListOf<MovieItem>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        repeat(4) {
            movieList += MovieItem(
                image = R.drawable.image_1,
                name = getString(R.string.film_name_1),
                details = getString(R.string.film_details_1)
            )
            movieList += MovieItem(
                image = R.drawable.image_2,
                name = getString(R.string.film_name_2),
                details = getString(R.string.film_details_2)
            )
            movieList += MovieItem(
                image = R.drawable.image_3,
                name = getString(R.string.film_name_3),
                details = getString(R.string.film_details_3)
            )
            movieList += MovieItem(
                image = R.drawable.image_4,
                name = getString(R.string.film_name_4),
                details = getString(R.string.film_details_4)
            )
        }

        binding.recyclerMovieItems.apply {
            val orientation = resources.configuration.orientation
            layoutManager =
                if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
                    GridLayoutManager(this@MainActivity, 3)
                } else {
                    GridLayoutManager(this@MainActivity, 2)
                }

            adapter = MovieItemAdapter(
                movieList,
                onClickListener
            )

            addItemDecoration(itemDecoration)
            itemAnimator
        }

        binding.buttonFavorites.setOnClickListener {
            val intent = Intent(this, FavoritesActivity::class.java).apply {
                putExtra(KEY_FAVORITES_MOVIE, favoriteMovieList.toTypedArray())
            }

            resultForFavoritesActivity.launch(intent)
        }
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }

    override fun onBackPressed() {
        ExitDialog().show(supportFragmentManager, "dialog")
    }

    private val onClickListener = object : MovieItemOnClickListener {
        override fun onClickDetails(item: MovieItem) {
            item.isViewed = true

            openDetailsActivity(item)
        }

        override fun onClickIsFavorite(item: MovieItem, position: Int) {
            item.isFavorite = !item.isFavorite

            favoriteMovieList.apply {
                if (contains(item)) {
                    remove(item)
                } else {
                    add(item)
                }
            }
        }
    }

    private val itemDecoration = object : RecyclerView.ItemDecoration() {
        override fun getItemOffsets(
            outRect: Rect,
            view: View,
            parent: RecyclerView,
            state: RecyclerView.State
        ) {
            outRect.top = 60
        }
    }

    private fun openDetailsActivity(movie: MovieItem) {
        val intent = Intent(this, DetailsActivity::class.java).apply {
            putExtra(KEY_MOVIE, movie as Serializable)
        }

        resultForDetailsActivity.launch(intent)
    }
}