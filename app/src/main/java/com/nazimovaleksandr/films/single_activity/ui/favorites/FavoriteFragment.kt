package com.nazimovaleksandr.films.single_activity.ui.favorites

import android.content.res.Configuration
import android.graphics.Rect
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.setPadding
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.nazimovaleksandr.films.R
import com.nazimovaleksandr.films.databinding.FragmentFavoriteBinding
import com.nazimovaleksandr.films.single_activity.SingleActivity
import com.nazimovaleksandr.films.single_activity.data.DataManager
import com.nazimovaleksandr.films.single_activity.data.entities.ui.MovieUI
import com.nazimovaleksandr.films.single_activity.ui.movie_list.MovieItemAdapter
import com.nazimovaleksandr.films.single_activity.ui.movie_list.MovieItemOnClickListener

class FavoriteFragment : Fragment() {

    private var _binding: FragmentFavoriteBinding? = null
    private val binding get() = _binding!!

    private val viewModel: FavoriteViewModel by viewModels {
        FavoriteViewModelFactory(DataManager)
    }

    private var adapter: MovieItemAdapter? = null

    private var snackbar: Snackbar? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFavoriteBinding.inflate(inflater, container, false)

        initView()
        initViewModel()

        return binding.root
    }

    override fun onStart() {
        super.onStart()
        requireActivity().supportFragmentManager.setFragmentResult(
            SingleActivity.KEY_FAVORITES_MOVIE,
            Bundle().apply {
                putBoolean(SingleActivity.KEY_FAVORITES_MOVIE, false)
            }
        )
    }

    override fun onStop() {
        super.onStop()
        snackbar?.dismiss()
        requireActivity().supportFragmentManager.setFragmentResult(
            SingleActivity.KEY_FAVORITES_MOVIE,
            Bundle().apply {
                putBoolean(SingleActivity.KEY_FAVORITES_MOVIE, true)
            }
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun initView() {
        binding.recyclerMovieItems.apply {
            val orientation = resources.configuration.orientation

            layoutManager =
                if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
                    GridLayoutManager(requireContext(), 3)
                } else {
                    GridLayoutManager(requireContext(), 2)
                }

            addItemDecoration(itemDecoration)

            setPadding(2)
        }
    }

    private fun initViewModel() {
        viewModel.favoriteMovieList?.observe(viewLifecycleOwner) { movieList ->
            adapter = MovieItemAdapter(
                getMovieItemClickListener()
            )

            adapter?.setMovieList(movieList)

            binding.recyclerMovieItems.adapter = adapter
        }
    }

    private fun getMovieItemClickListener() = object : MovieItemOnClickListener {
        override fun onClickDetails(movie: MovieUI) {
            movie.isViewed = true
            viewModel.onClickDetails(movie)

            findNavController().navigate(
                R.id.action_nav_favorites_to_detailsFragment,

                Bundle().apply {
                    putInt(SingleActivity.KEY_FAVORITE_MOVIE, movie.id)
                }
            )
        }

        override fun onClickIsFavorite(movie: MovieUI, position: Int) {
            movie.isFavorite = !movie.isFavorite
            adapter?.removeItem(movie)
            viewModel.onClickIsFavorite(movie)

            showSnackbar(getString(R.string.text_is_favorite_false), movie, position)
        }
    }

    private fun showSnackbar(message: String, movie: MovieUI, position: Int) {
        snackbar = Snackbar
            .make(
                binding.recyclerMovieItems,
                message,
                Snackbar.LENGTH_LONG
            )
            .setAnimationMode(Snackbar.ANIMATION_MODE_SLIDE)
            .setAction(
                android.R.string.cancel
            ) {
                movie.isFavorite = !movie.isFavorite
                viewModel.onClickIsFavorite(movie)
                adapter?.addItem(movie, position)
            }

        snackbar?.show()
    }

    private val itemDecoration = object : RecyclerView.ItemDecoration() {
        override fun getItemOffsets(
            outRect: Rect,
            view: View,
            parent: RecyclerView,
            state: RecyclerView.State
        ) {
            outRect.top = 4
            outRect.left = 2
            outRect.right = 2
            outRect.bottom = 4
        }
    }
}