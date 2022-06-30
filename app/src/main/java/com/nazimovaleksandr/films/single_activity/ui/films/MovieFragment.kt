package com.nazimovaleksandr.films.single_activity.ui.films

import android.content.Context
import android.content.res.Configuration
import android.graphics.Rect
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.core.view.isVisible
import androidx.core.view.setPadding
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.nazimovaleksandr.films.R
import com.nazimovaleksandr.films.databinding.FragmentMovieBinding
import com.nazimovaleksandr.films.single_activity.SingleActivity
import com.nazimovaleksandr.films.single_activity.data.DataManager
import com.nazimovaleksandr.films.single_activity.data.entities.ui.MovieUI
import com.nazimovaleksandr.films.single_activity.ui.exit_dialog.ExitDialog
import com.nazimovaleksandr.films.single_activity.ui.movie_list.MovieItemAdapter
import com.nazimovaleksandr.films.single_activity.ui.movie_list.MovieItemOnClickListener
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach

class MovieFragment : Fragment() {

    private var _binding: FragmentMovieBinding? = null
    private val binding get() = _binding!!

    private val viewModel: MovieViewModel by viewModels {
        MovieViewModelFactory(DataManager)
    }

    private var movieAdapter: MovieItemAdapter? = null

    private var snackbar: Snackbar? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)

        requireActivity().onBackPressedDispatcher.addCallback(
            this,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    ExitDialog().show(parentFragmentManager, "dialog")
                }
            }
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMovieBinding.inflate(inflater, container, false)

        initView()
        initViewModel()

        return binding.root
    }

    override fun onPause() {
        super.onPause()
        snackbar?.dismiss()
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

            addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)

                    val lastMovie =
                        recyclerView.findViewHolderForAdapterPosition((adapter?.itemCount ?: 0) - 1)

                    if (lastMovie != null) {
                        binding.loaderNextPage.isVisible = true
                        viewModel.loadNextPage()
                    }
                }
            })
        }

        binding.swipeRefresh.apply {
            setOnRefreshListener {
                binding.swipeRefresh.isRefreshing = false
                binding.loader.isVisible = true
                viewModel.loadMovieList()
            }
        }

        binding.loader.setOnClickListener {}
    }

    private fun initViewModel() {
        viewModel.movieList?.observe(viewLifecycleOwner) { movieList ->
            binding.loader.isVisible = movieList.isEmpty()

            if (movieList.isEmpty()) {
                viewModel.loadMovieList()
            }

            movieAdapter = movieAdapter ?: MovieItemAdapter(
                getMovieItemClickListener()
            )

            when (viewModel.currentPage) {
                0 -> movieAdapter?.setMovieList(movieList)

                else -> {
                    binding.loaderNextPage.isVisible = false
                    movieAdapter?.addMovieList(movieList)
                }
            }

            if (binding.recyclerMovieItems.adapter == null) {
                binding.recyclerMovieItems.adapter = movieAdapter
            }
        }

        lifecycleScope.launchWhenStarted {
            viewModel.isShowError.onEach {
                if (it) {
                    val snackbar = Snackbar
                        .make(
                            binding.recyclerMovieItems,
                            getString(R.string.movie_loading_error),
                            Snackbar.LENGTH_INDEFINITE
                        )
                        .setAnimationMode(Snackbar.ANIMATION_MODE_SLIDE)
                        .setAction(
                            R.string.refresh
                        ) {
                            binding.loader.isVisible = true
                            viewModel.refresh()
                        }

                    snackbar.show()

                    binding.loader.isVisible = false
                    binding.loaderNextPage.isVisible = false
                }
            }.collect()
        }
    }

    private fun getMovieItemClickListener() = object : MovieItemOnClickListener {
        override fun onClickDetails(movie: MovieUI) {
            movie.isViewed = true
            viewModel.onClickDetails(movie)

            findNavController().navigate(
                R.id.action_nav_movie_to_detailsFragment,

                Bundle().apply {
                    putInt(SingleActivity.KEY_MOVIE, movie.id)
                }
            )
        }

        override fun onClickIsFavorite(movie: MovieUI, position: Int) {
            movie.isFavorite = !movie.isFavorite

            viewModel.onClickIsFavorite(movie)

            val text = if (movie.isFavorite) {
                getString(R.string.text_is_favorite_true)
            } else {
                getString(R.string.text_is_favorite_false)
            }

            showSnackbar(text, movie, position)
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
                binding.recyclerMovieItems.adapter?.notifyItemChanged(position)
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