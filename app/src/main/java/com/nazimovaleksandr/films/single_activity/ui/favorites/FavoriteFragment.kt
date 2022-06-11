package com.nazimovaleksandr.films.single_activity.ui.favorites

import android.content.res.Configuration
import android.graphics.Rect
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.setPadding
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.nazimovaleksandr.films.R
import com.nazimovaleksandr.films.databinding.FragmentFavoriteBinding
import com.nazimovaleksandr.films.single_activity.ui.films.movie_list.MovieItem
import com.nazimovaleksandr.films.single_activity.ui.films.movie_list.MovieItemAdapter
import com.nazimovaleksandr.films.single_activity.ui.films.movie_list.MovieItemOnClickListener
import com.nazimovaleksandr.films.single_activity.SingleActivity
import java.io.Serializable

class FavoriteFragment : Fragment() {

    private var _binding: FragmentFavoriteBinding? = null
    private val binding get() = _binding!!

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
        val viewModel = ViewModelProvider(this)[FavoriteViewModel::class.java]

        viewModel.favoriteMovieList.observe(viewLifecycleOwner) { movieList ->
            adapter = MovieItemAdapter(
                movieList,
                getMovieItemClickListener()
            )

            binding.recyclerMovieItems.adapter = adapter
        }

        viewModel.loadMovieList(requireActivity() as SingleActivity)
    }

    private fun getMovieItemClickListener() = object : MovieItemOnClickListener {
        override fun onClickDetails(item: MovieItem) {
            item.isViewed = true

            findNavController().navigate(
                R.id.action_nav_favorites_to_detailsFragment,

                Bundle().apply {
                    putSerializable(SingleActivity.KEY_MOVIE, item as Serializable)
                }
            )
        }

        override fun onClickIsFavorite(item: MovieItem, position: Int) {
            item.isFavorite = !item.isFavorite
            adapter?.removeItem(item)
            setResultIsFavorite(item)

            showSnackbar(getString(R.string.text_is_favorite_false), item, position)
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
                adapter?.addItem(item, position)
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