package com.nazimovaleksandr.films.single_activity.ui.details

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.nazimovaleksandr.films.R
import com.nazimovaleksandr.films.databinding.FragmentDetailsBinding
import com.nazimovaleksandr.films.single_activity.SingleActivity
import com.nazimovaleksandr.films.single_activity.SingleActivity.Companion.KEY_TOOLBAR
import com.nazimovaleksandr.films.single_activity.data.DataManager
import jp.wasabeef.glide.transformations.BlurTransformation

class DetailsFragment : Fragment() {
    private var _binding: FragmentDetailsBinding? = null
    private val binding get() = _binding!!

    private val viewModel: DetailsViewModel by viewModels {
        DetailsViewModelFactory(DataManager)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initListeners()
        initViewModel()
    }

    override fun onStart() {
        super.onStart()
        requireActivity().supportFragmentManager.setFragmentResult(
            KEY_TOOLBAR,
            Bundle().apply {
                putBoolean(KEY_TOOLBAR, false)
            }
        )
    }

    override fun onStop() {
        super.onStop()

        viewModel.saveMovie()

        requireActivity().supportFragmentManager.setFragmentResult(
            KEY_TOOLBAR,
            Bundle().apply {
                putBoolean(KEY_TOOLBAR, true)
            }
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun initListeners() {
        binding.apply {
            buttonEmail.setOnClickListener {
                sendLink("mailto:")
            }
            buttonSms.setOnClickListener {
                sendLink("sms:")
            }
            buttonMessage.setOnClickListener {
                sendLink("")
            }
        }

        binding.checkbox.setOnCheckedChangeListener { _, isLike ->
            viewModel.onFavoriteChanged(isLike)
        }

        binding.comment.addTextChangedListener {
            viewModel.onCommentChanged(it.toString())
        }

        binding.toolbar.setNavigationOnClickListener {
            requireActivity().onBackPressed()
        }
    }

    private fun initViewModel() {
        viewModel.movie.observe(viewLifecycleOwner) {
            it?.let { movie ->
                binding.toolbar.title = movie.name ?: ""
                binding.fullText.text = movie.details
                binding.comment.setText(movie.comment)
                binding.checkbox.isChecked = movie.isFavorite

                Glide
                    .with(binding.fullImage.context)
                    .load(movie.image)
                    .apply(RequestOptions.bitmapTransform(BlurTransformation(25, 3)))
                    .placeholder(R.drawable.ic_load)
                    .error(R.drawable.ic_error_loading)
                    .into(binding.fullImage)

                Glide
                    .with(binding.fullImageMini.context)
                    .load(movie.image)
                    .placeholder(R.drawable.ic_load)
                    .error(R.drawable.ic_error_loading)
                    .into(binding.fullImageMini)
            }
        }

        arguments?.apply {
            viewModel.loadMovie(
                getInt(SingleActivity.KEY_MOVIE),
                getInt(SingleActivity.KEY_FAVORITE_MOVIE)
            )
        }
    }

    private fun sendLink(data: String) {
        val intent = Intent().apply {
            action = if (data != "") Intent.ACTION_SENDTO else Intent.ACTION_SEND

            putExtra(Intent.EXTRA_TEXT, "Присоединяйся :) http://otus.ru/")
            putExtra(Intent.EXTRA_SUBJECT, "Films App")

            if (data != "") this.data = Uri.parse(data) else type = "text/plain"
        }

        startActivity(intent)
    }
}