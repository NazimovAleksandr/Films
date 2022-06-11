package com.nazimovaleksandr.films.single_activity.ui.films

import android.content.Context
import android.content.res.Configuration
import android.graphics.Rect
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.core.view.setPadding
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.nazimovaleksandr.films.R
import com.nazimovaleksandr.films.databinding.FragmentMovieBinding
import com.nazimovaleksandr.films.single_activity.ui.films.movie_list.MovieItem
import com.nazimovaleksandr.films.single_activity.ui.films.movie_list.MovieItemAdapter
import com.nazimovaleksandr.films.single_activity.ui.films.movie_list.MovieItemOnClickListener
import com.nazimovaleksandr.films.single_activity.SingleActivity
import com.nazimovaleksandr.films.single_activity.ui.exit_dialog.ExitDialog
import java.io.Serializable

class MovieFragment : Fragment() {

    private var _binding: FragmentMovieBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: MovieViewModel

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
        setResultListeners()

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
        }
    }

    private fun initViewModel() {
        viewModel = ViewModelProvider(this)[MovieViewModel::class.java]

        viewModel.movieList.observe(viewLifecycleOwner) { movieList ->
            binding.recyclerMovieItems.adapter = MovieItemAdapter(
                movieList,
                getMovieItemClickListener()
            )
        }

        viewModel.loadMovieList(requireActivity() as SingleActivity)
    }

    private fun setResultListeners() {
        parentFragmentManager.setFragmentResultListener(
            SingleActivity.KEY_DETAILS,
            this
        ) { _, bundle ->
            bundle.apply {
                val movie = getSerializable(SingleActivity.KEY_MOVIE) as MovieItem
                setResultIsFavorite(movie)
            }
        }
    }

    private fun getMovieItemClickListener() = object : MovieItemOnClickListener {
        override fun onClickDetails(item: MovieItem) {
            item.isViewed = true

            findNavController().navigate(
                R.id.action_nav_movie_to_detailsFragment,

                Bundle().apply {
                    putSerializable(SingleActivity.KEY_MOVIE, item as Serializable)
                }
            )
        }

        override fun onClickIsFavorite(item: MovieItem, position: Int) {
            item.isFavorite = !item.isFavorite

            setResultIsFavorite(item)

            val text = if (item.isFavorite) {
                getString(R.string.text_is_favorite_true)
            } else {
                getString(R.string.text_is_favorite_false)
            }

            showSnackbar(text, item, position)
        }
    }

    private fun showSnackbar(message: String, item: MovieItem, position: Int) {
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
                item.isFavorite = !item.isFavorite
                setResultIsFavorite(item)
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

    private fun setResultIsFavorite(movie: MovieItem) {
        requireActivity().supportFragmentManager.setFragmentResult(
            SingleActivity.KEY_IS_LIKE,
            Bundle().apply {
                putSerializable(SingleActivity.KEY_MOVIE, movie)
            }
        )
    }
}