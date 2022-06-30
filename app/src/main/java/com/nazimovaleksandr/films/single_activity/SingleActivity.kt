package com.nazimovaleksandr.films.single_activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.navigation.NavigationView
import com.nazimovaleksandr.films.R
import com.nazimovaleksandr.films.databinding.ActivitySingleBinding
import com.nazimovaleksandr.films.single_activity.data.DataManager
import com.nazimovaleksandr.films.single_activity.data.entities.ui.MovieUI
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SingleActivity : AppCompatActivity() {

    companion object {
        const val KEY_MOVIE = "KEY_MOVIE"
        const val KEY_FAVORITE_MOVIE = "KEY_FAVORITE_MOVIE"
        const val KEY_FAVORITES_MOVIE = "KEY_FAVORITES_MOVIE"

        const val KEY_DETAILS = "KEY_DETAILS"
        const val KEY_IS_LIKE = "KEY_IS_LIKE"
        const val KEY_TOOLBAR = "KEY_TOOLBAR"

        @Suppress("unused")
        val TAG = SingleActivity::class.simpleName
    }

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivitySingleBinding

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

        supportFragmentManager.setFragmentResultListener(KEY_IS_LIKE, this) { _, bundle ->
            bundle.apply {
                (getSerializable(KEY_MOVIE) as MovieUI?)?.let {
                    lifecycleScope.launch(Dispatchers.IO) {
                        DataManager.updateMovie(it)
                    }
                }
            }
        }
    }
}