package com.nazimovaleksandr.films.single_activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.navigation.NavigationView
import com.nazimovaleksandr.films.R
import com.nazimovaleksandr.films.databinding.ActivitySingleBinding
import com.nazimovaleksandr.films.single_activity.ui.films.movie_list.MovieItem

class SingleActivity : AppCompatActivity() {

    companion object {
        const val KEY_MOVIE = "KEY_MOVIE"
        const val KEY_FAVORITES_MOVIE = "KEY_FAVORITES_MOVIE"

        const val KEY_DETAILS = "KEY_DETAILS"
        const val KEY_IS_LIKE = "KEY_IS_LIKE"
        const val KEY_TOOLBAR = "KEY_TOOLBAR"

        @Suppress("unused")
        val TAG = SingleActivity::class.simpleName
    }

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivitySingleBinding

    private val favoriteMovieList = mutableListOf<MovieItem>()
    private val movieList = mutableListOf<MovieItem>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySingleBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.appBarSingle.toolbar)

        val drawerLayout: DrawerLayout = binding.drawerLayout
        val navView: NavigationView = binding.navView
        val navController = findNavController(R.id.nav_host_fragment_content_single)

        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_movie, R.id.nav_favorites
            ),
            drawerLayout
        )

        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        binding.appBarSingle.fabFavorites.setOnClickListener {
            binding.navView.menu.performIdentifierAction(R.id.nav_favorites, 0)
        }

        repeat(4) {
            movieList.add(
                MovieItem(
                    image = R.drawable.image_1,
                    name = getString(R.string.film_name_1),
                    details = getString(R.string.film_details_1)
                )
            )
            movieList.add(
                MovieItem(
                    image = R.drawable.image_2,
                    name = getString(R.string.film_name_2),
                    details = getString(R.string.film_details_2)
                )
            )
            movieList.add(
                MovieItem(
                    image = R.drawable.image_3,
                    name = getString(R.string.film_name_3),
                    details = getString(R.string.film_details_3)
                )
            )
            movieList.add(
                MovieItem(
                    image = R.drawable.image_4,
                    name = getString(R.string.film_name_4),
                    details = getString(R.string.film_details_4)
                )
            )
        }

        setResultListeners()
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_single)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    private fun setResultListeners() {
        supportFragmentManager.setFragmentResultListener(KEY_TOOLBAR, this) { _, bundle ->
            bundle.apply {
                val isVisibleToolbar: Boolean = getBoolean(KEY_TOOLBAR)

                binding.appBarSingle.toolbar.isVisible = isVisibleToolbar
                binding.appBarSingle.fabFavorites.isVisible = isVisibleToolbar
            }
        }

        supportFragmentManager.setFragmentResultListener(KEY_FAVORITES_MOVIE, this) { _, bundle ->
            bundle.apply {
                val isVisibleToolbar: Boolean = getBoolean(KEY_FAVORITES_MOVIE)

                binding.appBarSingle.fabFavorites.isVisible = isVisibleToolbar
            }
        }
    }

    fun getFavoriteMovieList(): List<MovieItem> {
        return favoriteMovieList
    }

    fun getMovieList(): List<MovieItem> {
        return movieList
    }
}