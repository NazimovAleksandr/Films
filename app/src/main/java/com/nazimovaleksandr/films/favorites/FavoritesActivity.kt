package com.nazimovaleksandr.films.favorites

import android.app.Activity
import android.content.Intent
import android.content.res.Configuration
import android.graphics.Rect
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.nazimovaleksandr.films.R
import com.nazimovaleksandr.films.databinding.ActivityFavoritesBinding
import com.nazimovaleksandr.films.details.DetailsActivity
import com.nazimovaleksandr.films.main.MainActivity
import com.nazimovaleksandr.films.main.MainActivity.Companion.KEY_FAVORITES_MOVIE
import com.nazimovaleksandr.films.main.movie_list.MovieItem
import com.nazimovaleksandr.films.main.movie_list.MovieItemAdapter
import com.nazimovaleksandr.films.main.movie_list.MovieItemOnClickListener
import java.io.Serializable

class FavoritesActivity : AppCompatActivity() {

    private var _binding: ActivityFavoritesBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityFavoritesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.title = getString(R.string.favorite)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val movieList: List<MovieItem> = intent?.getParcelableArrayExtra(KEY_FAVORITES_MOVIE)?.map {
            it as MovieItem
        } ?: emptyList()

        binding.recyclerFavoritesMovie.apply {
            val orientation = resources.configuration.orientation
            layoutManager =
                if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
                    GridLayoutManager(this@FavoritesActivity, 3)
                } else {
                    GridLayoutManager(this@FavoritesActivity, 2)
                }

            adapter = MovieItemAdapter(
                movieList,
                onClickListener
            )

            addItemDecoration(itemDecoration)
        }
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }

    override fun onBackPressed() {
        setResult(
            Activity.RESULT_OK,
            Intent().apply {
                putExtra(
                    KEY_FAVORITES_MOVIE,
                    (binding.recyclerFavoritesMovie.adapter as MovieItemAdapter).movieItemList.toTypedArray()
                )
            }
        )
        super.onBackPressed()
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    private val onClickListener = object : MovieItemOnClickListener {
        override fun onClickDetails(item: MovieItem) {
            item.isViewed = true

            openDetailsActivity(item)
        }

        override fun onClickIsFavorite(item: MovieItem, position: Int) {
            item.isFavorite = !item.isFavorite

            (binding.recyclerFavoritesMovie.adapter as MovieItemAdapter).removeItem(item)

            binding.recyclerFavoritesMovie.adapter?.notifyItemRemoved(position)
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
            putExtra(MainActivity.KEY_MOVIE, movie as Serializable)
        }

        startActivity(intent)
    }
}